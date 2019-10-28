package com.example.retrofitpost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.JsonObject;
import java.io.File;
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
        fields.addProperty("FirstName", "test4");
        fields.addProperty("LastName", "test4");
        fields.addProperty("email", "test4@gmail.com" );
        fields.addProperty("phone", "9876543214");
        fields.addProperty("Address", "Bangalore");
        fields.addProperty("blacklisted", false);
        fields.addProperty("organization", "exza");


        Call<Object> call = ApiInterfaceObject.createPost( token, fields);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                // textViewResult.setText();
                if (!response.isSuccessful()) {
                    textViewResult.setText(response.toString());
                    return;
                }else{

                    textViewResult.setText("In onResponse" + response.body());
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                t.printStackTrace();
                textViewResult.setText("Failure >>>>>>>>>>>>>>>" + t.toString());
            }
        });


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

        File file  = new File("/mnt/sdcard/Download/visitor1.jpg");
        Log.d("Upload", file.getAbsolutePath());

        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("files", file.getName(),
                requestBody

        );
        RequestBody ref = RequestBody.create(MediaType.parse("text/plain"), "visitor");
        RequestBody refId = RequestBody.create(MediaType.parse("text/plain"), "160");
        RequestBody field = RequestBody.create(MediaType.parse("text/plain"), "Photo");




        Call<Object> call_img = uploadInterface.uploadImagePost(
                token,
                fileToUpload,
                ref,
                refId,
                field
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