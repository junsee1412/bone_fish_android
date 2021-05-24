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

public class LoginActivity extends AppCompatActivity {

    Service service;
    apiUser serviceUser = service.retrofit.create(apiUser.class);
    sqlite db = new sqlite(LoginActivity.this, "bone_fish.sqlite", null, 1);

    ImageButton registerBtn, loginBtn;
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

        registerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        loginBtn.setOnClickListener(v -> {
            email = emailLogin.getText().toString().trim();
            pass = passLogin.getText().toString().trim();

            CallUser(email,pass);
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        });
    }

    private void CallUser(String email, String pass) {
        serviceUser.Login(email, pass).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user.getMessage()!=null){
                        progressDialog.hide();
                        Log.d("runrun", user.getMessage());
                        showToast("Account does not exist");
                    } else {
                        Log.d("runrun", user.getToken());
                        toMainActivity(user.getToken());
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showToast("Server error");
                Log.d("runrun", "errrorr");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void toMainActivity(String token) {
        Intent intent = new Intent(this, MainActivity.class);
//        db.QueryData("DELETE FROM token");
//        db.QueryData("DELETE FROM bill");
        db.QueryData("INSERT INTO token VALUES(null,'"+token+"')");
        startActivity(intent);
    }

}
