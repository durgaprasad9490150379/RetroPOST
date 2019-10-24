package com.example.retrofitpost;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface ApiInterface {

    @Headers({"Content-Type: application/json;charset=Utf-8"})
    @POST("upload")
    Call<Object> createPostImage( @Header("Authorization") String Authorization, @Body JsonObject img_user);

    @Headers({"Content-Type: application/json;charset=Utf-8"})
    @POST("visitors")
    Call<Object> createPost( @Header("Authorization") String Authorization, @Body JsonObject fields);



}