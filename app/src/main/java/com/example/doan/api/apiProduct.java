package com.example.doan.api;

import com.example.doan.model.Product;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface apiProduct {

    @Multipart
    @POST("/api/product")
//    Call<Product> createProduct(@Field("token") String token,
//                                @Field("idbrand") String id_brand,
//                                @Field("idcategory") String id_category,
//                                @Field("product") String product,
//                                @Field("stock") int stock,
//                                @Field("price") int price,
//                                @Part("img") File img);
    Call<Product> createProduct(@Part("token") RequestBody token,
                                @Part("idbrand") RequestBody idbrand,
                                @Part("idcategory") RequestBody idcategory,
                                @Part("product") RequestBody product,
                                @Part("stock") RequestBody stock,
                                @Part("price") RequestBody price,
                                @Part MultipartBody.Part img);
//    Call<Product> createProduct(@Body Product product);

    //put Product
    @FormUrlEncoded
    @PUT("/api/product")
    Call<Product> updateProduct(@Field("token") String token,
                                @Field("idproduct") String idproduct,
                                @Field("product") String product,
                                @Field("stock") int stock,
                                @Field("price") int price);

    //get listProduct
    @GET("/api/{token}/product")
    Call<List<Product>> getlsProduct(@Path("token") String token);

    //get Product
    @GET("/api/{token}/product/{id}")
    Call<Product> getProduct(@Path("token") String token, @Path("id") String id);

    //delete Product
//    @DELETE("/api/product")
    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "/api/product", hasBody = true)
    Call<Product> delProduct(@Field("token") String token, @Field("idproduct") String id);
}
