package org.seeknresolve.android.module;


import com.squareup.okhttp.OkHttpClient;

import org.seeknresolve.android.rest.SessionIdInterceptor;
import org.seeknresolve.android.ui.LogInActivity;
import org.seeknresolve.android.ui.ProjectListActivity;

import java.net.CookieManager;
import java.net.CookiePolicy;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

@Module(
        injects = {
                LogInActivity.class,
                ProjectListActivity.class,
                SessionIdInterceptor.class
        },
        complete = false
)
public class HttpModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        client.setCookieHandler(cookieManager);
        return client;
    }

    @Provides
    @Singleton
    OkClient provideOkClient(OkHttpClient client) {
        return new OkClient(client);
    }

    @Provides
    @Named("sessionIdName")
    String provideSessionIdName() {
        return "JSESSIONID";
    }

    @Provides
    @Singleton
    RestAdapter.Builder proviceBuilder(SessionIdInterceptor sessionIdInterceptor, OkClient okClient) {
        return new RestAdapter.Builder()
                .setRequestInterceptor(sessionIdInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(okClient);
    }
}
