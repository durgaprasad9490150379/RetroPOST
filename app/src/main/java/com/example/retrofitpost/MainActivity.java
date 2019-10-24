package com.example.retrofitpost;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;

    private ApiInterface ApiInterfaceObject;
    public String token = "Bearer "+"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiaXNBZG1pbiI6dHJ1ZSwiaWF0IjoxNTcxMjA4MjkyLCJleHAiOjE1NzM4MDAyOTJ9.fxBbFQ29gqQ-vPRDws0zHKIw3l0tEdB0rEfBvaJSVfs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.122:1337/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiInterfaceObject = retrofit.create(ApiInterface.class);

        createPost();
    }




    private void createPost() {
//        PostData Posting = new PostData("testneww", "KK", "testnew@gmail.com", 2211, "asdf", "exza", true);

        JsonObject fields = new JsonObject();
        fields.addProperty("FirstName", "Saik");
        fields.addProperty("LastName", "Kumar");
        fields.addProperty("email", "saik@gmail.com" );
        fields.addProperty("phone", 98235);
        fields.addProperty("Address", "Bangalore");
        fields.addProperty("blacklisted", false);
        fields.addProperty("organization", "exza");

        Call<Object> call = ApiInterfaceObject.createPost( token, fields);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText("Success "+response.toString());
                    return;
                }else{

                    textViewResult.setText("In onResponse" + response.body());
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                textViewResult.setText("Failure " + t.toString());
            }
        });
    }
}