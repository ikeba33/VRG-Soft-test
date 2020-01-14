package com.example.retrofittest1;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofittest1.adapters.DataAdapter;
import com.example.retrofittest1.data_base.DBHelper;
import com.example.retrofittest1.managers.ImageManager;
import com.example.retrofittest1.model.FirstDate;
import com.example.retrofittest1.model.Publish;
import com.example.retrofittest1.model.PublishData;
import com.example.retrofittest1.retrofit.Api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String LIST_DATA_KEY = "answers";
    public static final int MY_PERMISSIONS_REQUEST_CODE = 22;

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private List<Publish> list = new ArrayList<>();
    private DataAdapter adapter = new DataAdapter(this);
    private Button btn_q;
    private DBHelper dbHelper;


    private Api api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView view = findViewById(R.id.outTv);
        ImageManager.fetchImage("https://www.redditstatic.com/xray-snoo-head.png", view);//todo убрать от сюда)

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        dbHelper = new DBHelper(this);

        //todo bd
        if(dbHelper.read()!= null){
            adapter.setPublishes(dbHelper.read());

        }


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.reddit.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(Api.class);


        btn_q = findViewById(R.id.btn_q);
        RecyclerView recyclerView = findViewById(R.id.list);

        recyclerView.setAdapter(adapter);

        if (savedInstanceState != null) {
            String data = savedInstanceState.getString(LIST_DATA_KEY);
            Type listType = new TypeToken<List<Publish>>() {
            }.getType();
            list = gson.fromJson(data, listType);
            adapter.setPublishes(list);
        }


        btn_q.setOnClickListener(this);


    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        String data = gson.toJson(list);


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

                for (PublishData date : f.data.children) {
                    list.add(date.data);
                }

                adapter.setPublishes(list);

                //todo clear old data
                dbHelper.insert(list);


            }

            @Override
            public void onFailure(Call<FirstDate> call, Throwable t) {
                String s = t.getMessage();
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "gooooooo saved ",
                            Toast.LENGTH_SHORT).show();
                    //saveImage();

                } else {
                    Toast.makeText(this, "sooooooo baddddd ",
                            Toast.LENGTH_SHORT).show();
                    // Пользователь запретил доступ
                }
                return;
            }
        }
    }



}

