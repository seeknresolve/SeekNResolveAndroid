package org.seeknresolve.android.ui;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.seeknresolve.android.SeekNResolveAndroid;
import org.seeknresolve.android.model.Project;
import org.seeknresolve.android.model.UserProvider;
import org.seeknresolve.android.rest.SeekNResolve;
import org.seeknresolve.android.rest.SnrResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ProjectListActivity extends ListActivity {

    @Inject
    RestAdapter.Builder restAdapterBuilder;

    @Inject
    UserProvider loggedUserProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((SeekNResolveAndroid) getApplication()).inject(this);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Project selectedProject = ((ProjectListAdapter) getListView().getAdapter()).getItem(position);

                Intent intent = new Intent(getApplicationContext(), ProjectBugListActivity.class);
                intent.putExtra(ProjectBugListActivity.PROJECT_ID, selectedProject.getId());
                startActivity(intent);
                Toast.makeText(getApplicationContext(), selectedProject.getId().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        getSeekNResolveApi().getProjectList(new Callback<SnrResponse<List<Project>>>() {
            @Override
            public void success(SnrResponse<List<Project>> listSnrResponse, Response response) {
                List<Project> projects = listSnrResponse.getObject();
                getListView().setAdapter(new ProjectListAdapter(projects));
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    private SeekNResolve getSeekNResolveApi() {
        return restAdapterBuilder.build().create(SeekNResolve.class);
    }

    class ProjectListAdapter extends BaseAdapter {
        List<Project> projects;

        ProjectListAdapter() {
            this.projects = new ArrayList<Project>();
        }

        ProjectListAdapter(List<Project> projects) {
            this.projects = projects;
        }

        @Override
        public int getCount() {
            return projects.size();
        }

        @Override
        public Project getItem(int i) {
            return projects.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(android.R.layout.simple_list_item_2, null);
            }
            TextView name = (TextView) view.findViewById(android.R.id.text1);
            TextView description = (TextView) view.findViewById(android.R.id.text2);

            Project project = getItem(i);
            name.setText(project.getName());
            description.setText(project.getDescription());

            return view;
        }
    }
}