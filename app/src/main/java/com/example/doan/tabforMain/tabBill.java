package com.example.doan.tabforMain;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.LoginActivity;
import com.example.doan.R;
import com.example.doan.adapter.ItemAdapter;
import com.example.doan.common.sqlite;
import com.example.doan.model.itemBill;

import java.util.ArrayList;
import java.util.List;

public class tabBill extends Fragment {

    sqlite db;
    Cursor cursor;

    private View view;
    private Bundle bundle;
    private String token;
    private Context context;
    private ImageView checkout;
    private EditText editTextTextPersonName;
    private RecyclerView billRecyclerView;
    private ItemAdapter itemAdapter;
    private List<itemBill> itemBillList;

    @Override
    public void onResume() {
        super.onResume();
        if (bundle!=null) {
            token = bundle.getString("token");
            itemBillList = new ArrayList<>();
            cursor = db.GetData("SELECT * FROM bill");
            while (cursor.moveToNext()) {
                String idProduct = cursor.getString(1);
                int qtyProduct = cursor.getInt(2);
                int priceProduct = cursor.getInt(3);
                String nameProduct = cursor.getString(4);
                String imgProduct = cursor.getString(5);

                itemBillList.add(new itemBill(idProduct, qtyProduct, priceProduct, nameProduct, imgProduct));
            }
            setBillRecyclerView(itemBillList);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_bill, container, false);
        context = getActivity();
        db  = new sqlite(context, "bone_fish.sqlite", null, 1);
        bundle = this.getArguments();
        intUI();
        if (bundle!=null) {
            token = bundle.getString("token");
            itemBillList = new ArrayList<>();
            cursor = db.GetData("SELECT * FROM bill");
            while (cursor.moveToNext()) {
                String idProduct = cursor.getString(1);
                int qtyProduct = cursor.getInt(2);
                int priceProduct = cursor.getInt(3);
                String nameProduct = cursor.getString(4);
                String imgProduct = cursor.getString(5);

                itemBillList.add(new itemBill(idProduct, qtyProduct, priceProduct, nameProduct, imgProduct));
            }
            setBillRecyclerView(itemBillList);
        }
        return view;
    }

    private void intUI() {
        checkout = view.findViewById(R.id.checkout);
        editTextTextPersonName = view.findViewById(R.id.editTextTextPersonName);
        billRecyclerView = view.findViewById(R.id.list_item_bill);

        checkout.setOnClickListener(v -> {
            String name = editTextTextPersonName.getText().toString();
            if (!name.equals("")) {
                Log.d("run",name);
//              function()
            } else {
                Log.d("run", "Name Customer not be null");
                Toast.makeText(context, "Name Customer not be null", Toast.LENGTH_SHORT);
            }
        });
    }

    private void setBillRecyclerView(List<itemBill> list) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        billRecyclerView.setLayoutManager(layoutManager);
        itemAdapter = new ItemAdapter();
        itemAdapter.setListItem(context, list);
        billRecyclerView.setAdapter(itemAdapter);
    }
}