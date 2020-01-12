package com.example.retrofittest1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
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
    private static final int MY_PERMISSIONS_REQUEST_CODE = 22;

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
        ImageManager.fetchImage("https://www.redditstatic.com/xray-snoo-head.png", view);

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

        if (savedInstanceState != null) {
            String data = savedInstanceState.getString(LIST_DATA_KEY);
            Type listType = new TypeToken<List<Publish>>() {
            }.getType();
            list = gson.fromJson(data, listType);
            adapter.setPublishes(list);
        }


        btn_q.setOnClickListener(this);

        dbHelper = new DBHelper(this);
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

                for (PublichData date : f.data.children) {
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

    public void saveInGalery(){//todo
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
               // saveImage();
                // Здесь отображаете диалог с объяснением -- зачем
                // Вашему приложению требуется данное разрешение.
                // По кнопке "Ок" диалога запрашиваете разрешение.

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CODE);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //saveImage();

                } else {

                    // Пользователь запретил доступ
                }
                return;
            }
        }
    }

    private void saveImage(Bitmap bmp) {
        // Пользователь разрешил доступ
        // Сохраняете картинку на диск
        try {
            File dest = new File(Environment.getExternalStorageDirectory() + "/"+"TikTakToe");
            dest.mkdirs();
            dest = new File(Environment.getExternalStorageDirectory() + "/"+"newFoto"+".jpg");
            FileOutputStream out = new FileOutputStream(dest);

            bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(this, "Сохранил "+dest.getPath().toString(),
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка при сохранении. Повторите попытку.",
                    Toast.LENGTH_SHORT).show();
            Log.d("MyIlnarLog2", e.toString());
        }
    }

}

