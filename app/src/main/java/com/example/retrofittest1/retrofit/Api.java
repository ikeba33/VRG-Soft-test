package com.example.retrofittest1.retrofit;

import com.example.retrofittest1.model.FirstDate;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("/top.json")
    Call<FirstDate> getPublish(@Query("count") int count, @Query("limit") int limit);



}
