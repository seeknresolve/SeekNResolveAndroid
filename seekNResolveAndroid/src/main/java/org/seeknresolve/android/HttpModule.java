package org.seeknresolve.android;


import com.squareup.okhttp.OkHttpClient;

import org.seeknresolve.android.rest.SessionIdInterceptor;
import org.seeknresolve.android.ui.MainActivity;

import java.net.CookieManager;
import java.net.CookiePolicy;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.client.OkClient;

@Module(
        injects = {
                MainActivity.class,
                SessionIdInterceptor.class
        }
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
}
