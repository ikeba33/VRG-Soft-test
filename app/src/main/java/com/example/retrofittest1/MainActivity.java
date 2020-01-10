package com.example.retrofittest1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String LIST_DATA_KEY = "answers";

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    List<Publish> list = new ArrayList<>();
    DataAdapter adapter = new DataAdapter(this);
    TextView outTv;
    Button btn_q;



    DBHelper dbHelper;
    private Api api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView view = findViewById(R.id.outTv);
        ImageManager.fetchImage("https://www.redditstatic.com/xray-snoo-head.png",view);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.reddit.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

       api = retrofit.create(Api.class);


        btn_q = findViewById(R.id.btn_q);
        RecyclerView recyclerView = findViewById(R.id.list);

        recyclerView.setAdapter(adapter);

        if(savedInstanceState!=null){
            String data = savedInstanceState.getString(LIST_DATA_KEY);
            Type listType = new TypeToken<List<Publish>>(){}.getType();
            list = gson.fromJson(data, listType);
            adapter.setPublishes(list);
        }


        btn_q.setOnClickListener(this);

        dbHelper = new DBHelper(this);
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        String data= gson.toJson(list);


        outState.putString(LIST_DATA_KEY, data);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        api.getPublish().enqueue(new Callback<FirstDate>() {
            @Override
            public void onResponse(Call<FirstDate> call, Response<FirstDate> response) {
                FirstDate f = response.body();
                list = new ArrayList<>();

                for (PublichData date:f.data.children ) {
                    list.add(date.data);
                }

                adapter.setPublishes(list);
                dbHelper.read();

            }

            @Override
            public void onFailure(Call<FirstDate> call, Throwable t) {
                String s = t.getMessage();
            }
        });
        }

    }

