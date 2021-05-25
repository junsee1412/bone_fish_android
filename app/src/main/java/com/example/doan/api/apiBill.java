package com.example.doan.api;

import com.example.doan.model.Bill;
import com.example.doan.model.itemBill;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface apiBill {
    @FormUrlEncoded
    @POST("/api/bill")
    Call<Bill> createBill(@Field("token") String token,
                             @Field("bill") String bill,
                             @FieldMap Map<String,String> params);

    @GET("/api/{token}/bill")
    Call<List<Bill>> getlistBill(@Path("token") String token);

    @GET("/api/{token}/bill/{id}")
    Call<Bill> getBill(@Path("token") String token, @Path("id") String id);
}