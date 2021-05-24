package com.example.doan.common;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public interface Service {
    //https://bone-fish.herokuapp.com
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.1.23:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
