package org.seeknresolve.android;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import dagger.ObjectGraph;


public class SeekNResolveAndroid extends Application {

    private ObjectGraph graph;

    @Override
    public void onCreate() {
        super.onCreate();
        graph = ObjectGraph.create(getModules().toArray());
    }

    public void inject(Object object) {
        graph.inject(object);
    }

    private List<Object> getModules() {
        List<Object> modules = new ArrayList<Object>();
        modules.add(new HttpModule());
        return modules;
    }
}

