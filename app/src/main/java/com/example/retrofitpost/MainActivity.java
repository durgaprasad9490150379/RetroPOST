package com.example.retrofitpost;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

//import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        //getPosts();
        //getComments();
        createPost();
    }




    private void createPost() {
        Post post = new Post(23, "New Title", "New Text");

        Map<String, String> geo = new HashMap<>();
        geo.put("lat", "333");
        geo.put("lng", "444");

        Map<String, String> address = new HashMap<>();
        address.put("street", "main");
        address.put("suite", "aaaa");
        address.put("city", "Bgr");
        address.put("zipcode", "334455");
        address.put("geo", geo.toString());
        //map.put(.0F, new HashMap(){{put(.0F,0);}});


        Map<String, String> company = new HashMap<>();
        company.put("name", "DurgaPrasad");
        company.put("catchPhrase", "jsksksks");
        company.put("bs", "sssssfs");


        Map<String, String> fields = new HashMap<>();
        fields.put("id", "1");
        fields.put("name", "durgaa");
        fields.put("username", "dddddddd" );
        fields.put("email", "durga@gmail.com");
        fields.put("address", address.toString());
        fields.put("phone", "48894598");
        fields.put("website", "fskjnkjkjjkkj");
        fields.put("company", company.toString());




//        Map<String, String> fields = new HashMap<>();
//        fields.put("userId", "25");
//        fields.put("title", "New Title");

        Call<Post> call = jsonPlaceHolderApi.createPost(fields);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";

                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}