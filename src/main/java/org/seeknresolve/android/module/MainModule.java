package org.seeknresolve.android.module;

import dagger.Module;

@Module(
        includes = {
                AuthenticationModule.class,
                HttpModule.class
        },
        complete = false
)
public class MainModule {
}
