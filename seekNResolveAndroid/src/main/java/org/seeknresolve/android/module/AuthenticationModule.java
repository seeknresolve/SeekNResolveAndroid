package org.seeknresolve.android.module;

import org.seeknresolve.android.model.UserProvider;
import org.seeknresolve.android.ui.LogInActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = LogInActivity.class,
        complete = false
)
public class AuthenticationModule {

    @Provides
    @Singleton
    UserProvider provideUserProvider() {
        return new UserProvider();
    }
}
