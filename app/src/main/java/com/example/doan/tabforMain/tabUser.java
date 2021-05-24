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

    Service service;
    apiUser serviceUser = service.retrofit.create(apiUser.class);

    private String token;
    private Bundle bundle;
    private View view;
    private Context context;
    private Button logout;
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
        logout = view.findViewById(R.id.Logout);

        logout.setOnClickListener(v -> Logout());
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
        context.startActivity(intent);
    }
}