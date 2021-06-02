package com.example.doan.intentforUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.R;
import com.example.doan.adapter.adapterBillDetail;
import com.example.doan.api.apiBill;
import com.example.doan.common.Service;
import com.example.doan.common.sqlite;
import com.example.doan.model.Bill;
import com.example.doan.model.itemBill;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Integer.parseInt;

public class BillActivity extends AppCompatActivity {

    private Service service;
    private apiBill serviceBill = service.retrofit.create(apiBill.class);

    private Cursor cursor;
    private sqlite db = new sqlite(this, "bone_fish.sqlite", null, 1);

    private TextView name, day, times, money;
    private ImageView back;
    private String id, token;
    private RecyclerView billDetailRecyclerView;
    private adapterBillDetail adapterBillDetail;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        name = findViewById(R.id.NameBillDetail);
        day = findViewById(R.id.DayBillDetail);
        times = findViewById(R.id.TimeBillDetail);
        money = findViewById(R.id.PriceBillDetail);
        back = findViewById(R.id.imageView5);
        billDetailRecyclerView = findViewById(R.id.item_bill_recy);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        back.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        cursor = db.GetData("SELECT * FROM token");
        if (cursor.moveToNext()){
            token = cursor.getString(1);
            getBill();
        }
    }

    private void getBill(){
        serviceBill.getBill(token, id).enqueue(new Callback<Bill>() {
            @Override
            public void onResponse(Call<Bill> call, Response<Bill> response) {
                if (response.isSuccessful()) {
                    Bill bill = response.body();
                    progressDialog.hide();
                    setData(bill.getBill(), bill.getTime(), bill.getItems());
                }
            }

            @Override
            public void onFailure(Call<Bill> call, Throwable t) {
                Toast.makeText(BillActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData(String bill, String time, List<itemBill> items) {
        name.setText(bill);
        day.setText(time.substring(0,10));
        times.setText(time.substring(11,16));
        int intmoney = 0;
        for (itemBill itemBill :items) {
            intmoney+= parseInt(itemBill.getPrice());
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        billDetailRecyclerView.setLayoutManager(layoutManager);
        adapterBillDetail = new adapterBillDetail(items, this);
        billDetailRecyclerView.setAdapter(adapterBillDetail);
        money.setText(String.valueOf(intmoney));
    }
}