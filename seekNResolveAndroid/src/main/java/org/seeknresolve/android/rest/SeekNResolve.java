package org.seeknresolve.android.rest;

import org.seeknresolve.android.model.Project;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

public interface SeekNResolve {
    @FormUrlEncoded
    @POST("/login")
    void login(@Field("login") String login, @Field("password") String password, Callback<Response> callback);

    @GET("/project/all")
    void getProjectList(Callback<SnrResponse<List<Project>>> cb);
}
