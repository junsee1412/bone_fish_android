package com.example.doan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan.api.apiUser;
import com.example.doan.common.Service;
import com.example.doan.common.sqlite;
import com.example.doan.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    Service service;
    apiUser serviceUser = service.retrofit.create(apiUser.class);
    sqlite db = new sqlite(RegisterActivity.this, "bone_fish.sqlite", null, 1);

    ImageButton registerBtn, loginBtn;
    EditText emailRegister, passRegister;
    ProgressDialog progressDialog;

    String email, pass;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerBtn = findViewById(R.id.register_btn_register);
        loginBtn = findViewById(R.id.register_btn_login);
        emailRegister = findViewById(R.id.registerEmail);
        passRegister = findViewById(R.id.registerPassword);

        loginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        registerBtn.setOnClickListener(v -> {
            email = emailRegister.getText().toString().trim();
            pass = passRegister.getText().toString().trim();

            createUser(email,pass);
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
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
                Log.d("runrun", "errrorr");
                showToast("Server error");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void toMainActivity(String token) {
        Intent intent = new Intent(this, MainActivity.class);
//        db.QueryData("DELETE FROM token");
        db.QueryData("INSERT INTO token VALUES(null,'"+token+"')");
        startActivity(intent);
    }
}
