package com.example.doan.api;

import com.example.doan.model.Brand;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface apiBrand {

    @FormUrlEncoded
    @POST("/api/brand")
    Call<Brand> createBrand(@Field("token") String token, @Field("brand") String brand);

    //put Brand
    @FormUrlEncoded
    @PUT("/api/brand")
    Call<Brand> updateBrand(@Field("token") String token, @Field("brand") String brand);

    //get listBrand
    @GET("/api/{token}/brand")
    Call<List<Brand>> getlsBrand(@Path("token") String token);

    //get Brand
    @GET("/api/{token}/brand/{id}")
    Call<Brand> getBrand(@Path("token") String token, @Path("id") String id);

    //delete Brand
    @FormUrlEncoded
    @DELETE("/api/brand")
    Call<Brand> delBrand(@Field("token") String token, @Field("id") String id);
}