package com.example.doan.tabforMain;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;
import com.example.doan.adapter.ItemAdapter;
import com.example.doan.api.apiBill;
import com.example.doan.common.Service;
import com.example.doan.common.sqlite;
import com.example.doan.model.Bill;
import com.example.doan.model.itemBill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class tabBill extends Fragment {

    private Service service;
    private apiBill serviceBill = service.retrofit.create(apiBill.class);

    private sqlite db;
    private Cursor cursor;

    private View view;
    private Bundle bundle;
    private String token;
    private Context context;
    private ImageView checkout;
    private EditText editTextTextPersonName;
    private RecyclerView billRecyclerView;
    private ItemAdapter itemAdapter;
    private List<itemBill> itemBillList, itembills;
    private ProgressDialog progressDialog;

    @Override
    public void onResume() {
        super.onResume();
        if (bundle!=null) {
            token = bundle.getString("token");
            itembills = new ArrayList<>();
            itemBillList = new ArrayList<>();
            cursor = db.GetData("SELECT * FROM bill");
            while (cursor.moveToNext()) {
                String idProduct = cursor.getString(1);
                int qtyProduct = cursor.getInt(2);
                int priceProduct = cursor.getInt(3);
                String nameProduct = cursor.getString(4);
                String imgProduct = cursor.getString(5);
                itembills.add(new itemBill(idProduct, qtyProduct, priceProduct));
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
            if (name.isEmpty() || itembills==null) {
                Log.d("run", "Name Customer not be null");
                Toast.makeText(context, "Customer or Bill is Empty", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("run",name);
                pushBill(name, itembills);
            }
        });
    }

    private void pushBill(String t, List<itemBill> list) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        Map<String,String> map = new HashMap<>();
        for (int i=0; i<list.size(); i++) {
            map.put("items["+i+"][id_product]", list.get(i).getId_product());
            map.put("items["+i+"][quantity]", String.valueOf(list.get(i).getQuantity()));
            map.put("items["+i+"][price]", String.valueOf(list.get(i).getPrice()));
        }

        serviceBill.createBill(token, t, map).enqueue(new Callback<Bill>() {
            @Override
            public void onResponse(Call<Bill> call, Response<Bill> response) {
                if (response.isSuccessful()) {
                    Bill bill = response.body();
                    String mess = bill.getMessage();
                    if (mess.equals("cannot create")) {
                        Toast.makeText(context, "Cannot Create Bill", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Create Bill Success", Toast.LENGTH_LONG).show();
                        updateUI();
                    }
                }
            }

            @Override
            public void onFailure(Call<Bill> call, Throwable t) {
                Log.d("error:", "Error");
            }
        });
    }

    private void updateUI() {
        db.QueryData("DELETE FROM bill");
        progressDialog.hide();
        editTextTextPersonName.setText("");
        setBillRecyclerView(null);
    }

    private void setBillRecyclerView(List<itemBill> list) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        billRecyclerView.setLayoutManager(layoutManager);
        itemAdapter = new ItemAdapter();
        itemAdapter.setListItem(context, list);
        billRecyclerView.setAdapter(itemAdapter);
    }
}