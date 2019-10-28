package com.example.retrofitpost;

import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;


public interface ApiInterface {

    // @Headers({"Content-Type: application/json;charset=Utf-8"})
    @Multipart
    @POST("upload")
    Call<Object> uploadImagePost(
                @Header("Authorization") String Authorization,
                @Part MultipartBody.Part file
//                @Body JsonObject fields
            );

    @Headers({"Content-Type: application/json;charset=Utf-8"})
    @POST("visitors")
    Call<Object> createPost( @Header("Authorization") String Authorization, @Body JsonObject fields);

}