package com.example.doan.intentforUser;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.doan.R;
import com.example.doan.api.apiUser;
import com.example.doan.common.Service;
import com.example.doan.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordActivity extends AppCompatActivity {

    private Service service;
    private apiUser serviceUser = service.retrofit.create(apiUser.class);

    private ProgressDialog progressDialog;

    private Button cancel, change;
    private EditText oldpass, newpass;
    private ImageView back;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        cancel = findViewById(R.id.button6);
        change = findViewById(R.id.button7);

        oldpass = findViewById(R.id.editTextTextPassword);
        newpass = findViewById(R.id.editTextTextPassword2);

        back = findViewById(R.id.imageView8);

        cancel.setOnClickListener(v -> finish());
        back.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        change.setOnClickListener(v -> changePass());
    }

    private void changePass() {
        String odlstr, newstr;
        odlstr = oldpass.getText().toString();
        newstr = newpass.getText().toString();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        if (!odlstr.isEmpty() && !newstr.isEmpty()) {
            serviceUser.UpdatePassword(token, odlstr, newstr).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        User user = response.body();
                        String message = user.getMessage();
                        if (message.equals("update success")) {
                            Toast.makeText(PasswordActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
                            finish();
                        } else Toast.makeText(PasswordActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(PasswordActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            });
        } else {

        }
    }
}