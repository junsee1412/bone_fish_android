package com.example.doan.api;

import com.example.doan.model.User;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface apiUser {
    @FormUrlEncoded
    @POST("/api/user/login")
    Call<User> Login(@Field("email") String email, @Field("pass") String pass);

    @FormUrlEncoded
    @POST("/api/user/register")
    Call<User> Register(@Field("email") String email, @Field("pass") String pass);

    @FormUrlEncoded
    @PUT("/api/user")
    Call<User> UpdatePassword(@Field("token") String token, @Field("pass") String pass);

    @FormUrlEncoded
    @DELETE("/api/user")
    Call<User> DelUser(@Field("token") String token, @Field("pass") String pass);
}