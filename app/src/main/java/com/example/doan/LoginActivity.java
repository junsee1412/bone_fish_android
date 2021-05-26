package com.example.doan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan.api.apiUser;
import com.example.doan.common.Service;
import com.example.doan.common.sqlite;
import com.example.doan.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Service service;
    apiUser serviceUser = service.retrofit.create(apiUser.class);
    sqlite db = new sqlite(LoginActivity.this, "bone_fish.sqlite", null, 1);

    Button registerBtn, loginBtn;
    EditText emailLogin, passLogin;
    ProgressDialog progressDialog;

    String email, pass;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerBtn = findViewById(R.id.login_btn_register);
        loginBtn = findViewById(R.id.login_btn_login);
        emailLogin = findViewById(R.id.loginEmail);
        passLogin = findViewById(R.id.loginPassword);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        registerBtn.setOnClickListener(v -> CreateOrLogin(1));

        loginBtn.setOnClickListener(v -> CreateOrLogin(0));
    }

    private void CreateOrLogin(int i) {
        email = emailLogin.getText().toString().trim();
        pass = passLogin.getText().toString().trim();
        if (!email.isEmpty() && !pass.isEmpty()) {
            if (i==0) {
                progressDialog.show();
                loginUser(email, pass);
            } else {
                progressDialog.show();
                createUser(email, pass);
            }
        } else showToast("Password or Email is empty");
    }

    private void loginUser(String email, String pass) {
        serviceUser.Login(email, pass).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user.getMessage()!=null){
                        progressDialog.hide();
                        showToast("Account does not exist");
                    } else {
                        toMainActivity(user.getToken());
                        progressDialog.hide();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showToast("Server error");
                progressDialog.hide();
            }
        });
    }

    private void createUser(String email, String pass) {
        serviceUser.Register(email, pass).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user.getMessage()!=null){
                        progressDialog.hide();
                        showToast("Account already exists");
                    } else {
                        toMainActivity(user.getToken());
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showToast("Server error");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void toMainActivity(String token) {
        Intent intent = new Intent(this, MainActivity.class);
        db.QueryData("INSERT INTO token VALUES(null,'"+token+"')");
        startActivity(intent);
    }
}
