package com.example.retrofittest1.retrofit;

import com.example.retrofittest1.model.FirstDate;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("/top.json?limit=2")
    Call<FirstDate> getPublish();



}
