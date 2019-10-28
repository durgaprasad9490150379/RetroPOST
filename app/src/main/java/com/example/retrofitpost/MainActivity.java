package com.example.retrofitpost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult, textViewResult2;

    private ApiInterface ApiInterfaceObject;

    private static final int REQUEST = 112;


    public String token = "Bearer "+"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiaXNBZG1pbiI6dHJ1ZSwiaWF0IjoxNTcxMjA4MjkyLCJleHAiOjE1NzM4MDAyOTJ9.fxBbFQ29gqQ-vPRDws0zHKIw3l0tEdB0rEfBvaJSVfs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);
        textViewResult2 = findViewById(R.id.text2);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.122:1337/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterfaceObject = retrofit.create(ApiInterface.class);

        createPost();
    }




    private void createPost() {



        JsonObject fields = new JsonObject();
        fields.addProperty("FirstName", "Durga");
        fields.addProperty("LastName", "Kumar");
        fields.addProperty("email", "frty@gmail.com" );
        fields.addProperty("phone", "9988779999");
        fields.addProperty("Address", "Bangalore");
        fields.addProperty("blacklisted", false);
        fields.addProperty("organization", "exza");


        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) MainActivity.this, PERMISSIONS, REQUEST);
            }
        }


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofitUploader = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://192.168.100.122:1337/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface uploadInterface = retrofitUploader.create(ApiInterface.class);

        // JsonObject img_user = new JsonObject();

        File file  = new File("/mnt/sdcard/Download/visitor.jpeg");
        Log.d("Upload", file.getAbsolutePath());


//     img_user.addProperty("foo", "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");
     RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("files", file.getName(),
                requestBody
        );
//         RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        // JsonObject fileMeta = new JsonObject();
        // fileMeta.addProperty("filename", "TestUpload" + file.getName() );
        // RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), "image-type");




        Call<Object> call_img = uploadInterface.uploadImagePost(
                token,
                fileToUpload
        );

        call_img.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if (!response.isSuccessful()) {
                    textViewResult2.setText(">>" + response.raw().toString());
                    return;
                }else{

                    textViewResult2.setText(">>>>>>>>>>>>>>>>>>>" + response.toString());
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                t.printStackTrace();
                textViewResult2.setText("onFailure " + t.toString());
            }
        });



//        Call<Object> call = ApiInterfaceObject.createPost( token, fields);
//
//        call.enqueue(new Callback<Object>() {
//            @Override
//            public void onResponse(Call<Object> call, Response<Object> response) {
//                // textViewResult.setText();
//                if (!response.isSuccessful()) {
//                    textViewResult.setText(response.toString());
//                    return;
//                }else{
//
//                    textViewResult.setText("In onResponse" + response.body());
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Object> call, Throwable t) {
//                t.printStackTrace();
//                textViewResult.setText("Failure >>>>>>>>>>>>>>>" + t.toString());
//            }
//        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(MainActivity.this, "The app was not allowed to read your store.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}