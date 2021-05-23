package com.example.doan.api;

import com.example.doan.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface apiCategory {

    // CATEGORY
    //post Category
    @FormUrlEncoded
    @POST("/api/category")
    Call<Category> createCategory(@Field("token") String token, @Field("category") String brand);

    //put Category
    @FormUrlEncoded
    @PUT("/api/category")
    Call<Category> updateCategory(@Field("token") String token, @Field("category") String brand);

    //get list Category
    @GET("/api/{token}/category")
    Call<List<Category>> getlsCategory(@Path("token") String token);

    //get Category
    @GET("/api/{token}/category/{id}")
    Call<Category> getCategory(@Path("token") String token, @Path("id") String id);

    //delete Category
    @FormUrlEncoded
    @DELETE("/api/category")
    Call<Category> delCategory(@Field("token") String token, @Field("id") String id);
    //END CATEGORY
}