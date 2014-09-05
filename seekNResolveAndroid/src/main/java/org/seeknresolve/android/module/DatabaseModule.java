package org.seeknresolve.android.module;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.seeknresolve.android.SeekNResolveAndroid;
import org.seeknresolve.android.model.DatabaseHelper;
import org.seeknresolve.android.model.Url;
import org.seeknresolve.android.ui.LogInActivity;

import java.sql.SQLException;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = LogInActivity.class,
        complete = false
)
public class DatabaseModule {

    private DatabaseHelper databaseHelper;

    public DatabaseModule(SeekNResolveAndroid application) {
        databaseHelper = getHelper(application);
    }

    private DatabaseHelper getHelper(Context context) {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Provides
    public Dao<Url, Long> provideUrlDao() {
        try {
            return databaseHelper.getUrlDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
