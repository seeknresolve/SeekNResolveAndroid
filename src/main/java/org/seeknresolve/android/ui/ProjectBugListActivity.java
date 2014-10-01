package org.seeknresolve.android.ui;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.seeknresolve.android.R;
import org.seeknresolve.android.SeekNResolveAndroid;
import org.seeknresolve.android.model.Bug;
import org.seeknresolve.android.model.Project;
import org.seeknresolve.android.rest.SeekNResolve;
import org.seeknresolve.android.rest.SnrResponse;

import java.util.List;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ProjectBugListActivity extends ListActivity {
    public final static String PROJECT_ID = "projectId";

    @Inject
    RestAdapter.Builder restAdapterBuilder;

    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((SeekNResolveAndroid) getApplication()).inject(this);
        Long projectId = getIntent().getExtras().getLong(PROJECT_ID);

        getSeekNResolveApi().getProjectDetails(projectId, new Callback<SnrResponse<Project>>() {
            @Override
            public void success(SnrResponse<Project> snrResponse, Response response) {
                project = snrResponse.getObject();
                getListView().setAdapter(new BugListAdapter(project.getBugs()));
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private SeekNResolve getSeekNResolveApi() {
        return restAdapterBuilder.build().create(SeekNResolve.class);
    }

    private class BugListAdapter extends BaseAdapter {
        private List<Bug> bugs;

        private BugListAdapter(List<Bug> bugs) {
            this.bugs = bugs;
        }

        @Override
        public int getCount() {
            return bugs.size();
        }

        @Override
        public Bug getItem(int i) {
            return bugs.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            BugListItemViewHolder holder;
            if (view == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bug_list_item, null);
                holder = createHolder(view);
                view.setTag(holder);
            } else {
                holder = (BugListItemViewHolder) view.getTag();
            }
            Bug bug = getItem(i);
            holder.tag.setText(bug.getTag());
            holder.name.setText(bug.getName());
            holder.description.setText(bug.getDescription());
            holder.status.setText(bug.getState().toString());
            holder.priority.setText(bug.getPriority().toString());
            return view;
        }

        private BugListItemViewHolder createHolder(View view) {
            BugListItemViewHolder holder = new BugListItemViewHolder();
            holder.tag = (TextView) view.findViewById(R.id.tag);
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.description = (TextView) view.findViewById(R.id.description);
            holder.status = (TextView) view.findViewById(R.id.status);
            holder.priority = (TextView) view.findViewById(R.id.priority);
            return holder;
        }

        class BugListItemViewHolder {
            TextView tag;
            TextView name;
            TextView description;
            TextView status;
            TextView priority;
        }
    }
}
