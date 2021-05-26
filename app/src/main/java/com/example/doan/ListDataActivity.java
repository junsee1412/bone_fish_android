package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doan.adapter.adapterBillDat;
import com.example.doan.adapter.adapterBranDat;
import com.example.doan.adapter.adapterCateDat;
import com.example.doan.adapter.adapterProdDat;
import com.example.doan.api.apiBill;
import com.example.doan.api.apiProduct;
import com.example.doan.api.apiUser;
import com.example.doan.common.Service;
import com.example.doan.common.sqlite;
import com.example.doan.model.Bill;
import com.example.doan.model.Brand;
import com.example.doan.model.Category;
import com.example.doan.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListDataActivity extends AppCompatActivity {

    private Service service;
    private apiBill serviceBill = service.retrofit.create(apiBill.class);
    private apiProduct servicePro = service.retrofit.create(apiProduct.class);

    private sqlite db = new sqlite(ListDataActivity.this, "bone_fish.sqlite", null, 1);
    private Cursor cursor;

    private RecyclerView dataRecyclerView;
    private adapterBillDat adapterBillDat;
    private adapterBranDat adapterBranDat;
    private adapterCateDat adapterCateDat;
    private adapterProdDat adapterProdDat;

    private ImageView back;
    private TextView title;
    private String getTitle, token;
    private List<Bill> billList;
    private List<Brand> brandList;
    private List<Category> categoryList;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);
        dataRecyclerView = findViewById(R.id.data_recy);
        back = findViewById(R.id.imageView5);
        title = findViewById(R.id.Title);

        back.setOnClickListener(v -> finish());

        cursor = db.GetData("SELECT * FROM token");
        if (cursor.moveToNext()) token = cursor.getString(1);

        Intent intent = getIntent();
        getTitle = intent.getStringExtra("title");
        if (getTitle.equals("Brand")) {
            title.setText(getTitle);
            showRecyBrand();

        } else if (getTitle.equals("Category")) {
            title.setText(getTitle);
            showRecyCategory();

        } else if (getTitle.equals("Product")) {
            title.setText(getTitle);
            showRecyProduct();

        } else {
            title.setText(getTitle);
            showRecyBill();
        }
    }

    private void showRecyBrand() {
        cursor = db.GetData("SELECT * FROM bran");
        brandList = new ArrayList<>();
        while (cursor.moveToNext()) {
            brandList.add(new Brand(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        dataRecyclerView.setLayoutManager(layoutManager);
        adapterBranDat = new adapterBranDat(brandList, this, token);
        dataRecyclerView.setAdapter(adapterBranDat);
    }

    private void showRecyCategory() {
        cursor = db.GetData("SELECT * FROM cate");
        categoryList = new ArrayList<>();
        while (cursor.moveToNext()) {
            categoryList.add(new Category(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        dataRecyclerView.setLayoutManager(layoutManager);
        adapterCateDat = new adapterCateDat(categoryList, this, token);
        dataRecyclerView.setAdapter(adapterCateDat);
    }

    private void showRecyProduct() {
        cursor = db.GetData("SELECT * FROM prod");
        productList = new ArrayList<>();
        while (cursor.moveToNext()) {
            productList.add(new Product(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), cursor.getString(4),
                    cursor.getInt(5), cursor.getInt(6), cursor.getString(7)));
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        dataRecyclerView.setLayoutManager(layoutManager);
        adapterProdDat = new adapterProdDat(productList,this);
        dataRecyclerView.setAdapter(adapterProdDat);
    }

    private void showRecyBill() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        dataRecyclerView.setLayoutManager(layoutManager);
        serviceBill.getlistBill(token).enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                if (response.isSuccessful()) {
                    billList = response.body();

                }
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable t) {

            }
        });
    }
}