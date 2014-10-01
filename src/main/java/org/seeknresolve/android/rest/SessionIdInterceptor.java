package org.seeknresolve.android.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import retrofit.RequestInterceptor;

@Singleton
public class SessionIdInterceptor implements RequestInterceptor {
    @Inject @Named("sessionIdName")
    String sessionIdName;

    private String sessionIdValue;

    @Inject
    public SessionIdInterceptor() {
    }

    public String getSessionIdValue() {
        return sessionIdValue;
    }

    public void setSessionIdValue(String sessionIdValue) {
        this.sessionIdValue = sessionIdValue;
    }

    @Override
    public void intercept(RequestInterceptor.RequestFacade request) {
        if (sessionIdValue != null) {
            request.addHeader("Cookie", sessionIdName + "=" + sessionIdValue);
        }
    }
}