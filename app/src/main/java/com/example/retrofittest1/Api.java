package com.example.retrofittest1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("/r/TellMeAFact/top/.json?count=5")
    Call<FirstDate> getPublish();



}
