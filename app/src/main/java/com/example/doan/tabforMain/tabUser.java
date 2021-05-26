package com.example.doan.tabforMain;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.doan.ListDataActivity;
import com.example.doan.ProductActivity;
import com.example.doan.R;
import com.example.doan.SplashActivity1;
import com.example.doan.api.apiUser;
import com.example.doan.common.Service;
import com.example.doan.common.sqlite;
import com.example.doan.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tabUser extends Fragment {

    private Service service;
    private apiUser serviceUser = service.retrofit.create(apiUser.class);

    private String token;
    private Bundle bundle;
    private View view;
    private Context context;
    private Button AlProduct, AdProduct,
            AlBrand, AdBrand,
            AlCategory, AdCategory,
            AlBill, Password, logout;
    private TextView username, useremail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_user, container, false);
        context = getActivity();
        intUI();
        bundle = this.getArguments();
        if (getArguments() != null) {
            token = bundle.getString("token");
            getUserByToken(token);
        }
        return view;
    }

    private void getUserByToken(String token) {
        serviceUser.GetUser(token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    String email = user.getEmail();
                    showProfile(email);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("error:", "Error");
            }
        });
    }

    private void intUI() {
        username = view.findViewById(R.id.userName);
        useremail = view.findViewById(R.id.emailUser);

        AlProduct = view.findViewById(R.id.All_Product);
        AdProduct = view.findViewById(R.id.Add_Product);

        AlBrand = view.findViewById(R.id.All_Brand);
        AdBrand = view.findViewById(R.id.Add_Brand);

        AlCategory = view.findViewById(R.id.All_Category);
        AdCategory = view.findViewById(R.id.Add_Category);

        AlBill = view.findViewById(R.id.All_Bill);
        Password = view.findViewById(R.id.Password);

        logout = view.findViewById(R.id.Logout);

        AlProduct.setOnClickListener(v -> toListDataActivity("Product"));
        AdProduct.setOnClickListener(v -> {});

        AlBrand.setOnClickListener(v -> toListDataActivity("Brand"));
        AdBrand.setOnClickListener(v -> {});

        AlCategory.setOnClickListener(v -> toListDataActivity("Category"));
        AdCategory.setOnClickListener(v -> {});

        AlBill.setOnClickListener(v -> toListDataActivity("Bill"));
        Password.setOnClickListener(v -> {});

        logout.setOnClickListener(v -> Logout());
    }

    private void toListDataActivity(String title) {
        Intent intent = new Intent(context, ListDataActivity.class);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    private void showProfile(String email) {
        int index = email.indexOf("@");
        String nameShow = email.substring(0,index);
        username.setText(nameShow);
        useremail.setText(email);
    }

    private void Logout() {
        Intent intent = new Intent(context, SplashActivity1.class);
        sqlite db = new sqlite(context, "bone_fish.sqlite", null, 1);
        db.QueryData("DELETE FROM token");
        db.QueryData("DELETE FROM bill");
        db.QueryData("DELETE FROM cate");
        db.QueryData("DELETE FROM bran");
        db.QueryData("DELETE FROM prod");
        context.startActivity(intent);
    }
}