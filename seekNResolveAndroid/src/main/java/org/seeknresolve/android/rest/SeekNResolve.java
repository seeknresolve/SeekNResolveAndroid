package org.seeknresolve.android.rest;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface SeekNResolve {
    @FormUrlEncoded
    @POST("/login")
    void login(@Field("login") String login, @Field("password") String password, Callback<Response> callback);
}
