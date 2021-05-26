package com.example.doan.intentforUser;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doan.R;
import com.example.doan.api.apiProduct;
import com.example.doan.common.Service;
import com.example.doan.common.sqlite;

public class AddProductActivity extends AppCompatActivity {

    private Service service;
    private apiProduct servicePro = service.retrofit.create(apiProduct.class);

    private sqlite db = new sqlite(this, "bone_fish.sqlite", null, 1);
    private Cursor cursor;

    private ImageView back;
    private TextView brand, category, image;
    private EditText product, qty, price;
    private Button btnBrand, btnCategory, btnImage, btnCancle, btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        brand = findViewById(R.id.textView9);
        category = findViewById(R.id.textView10);
        image = findViewById(R.id.textView11);

        product = findViewById(R.id.addNameProduct);
        qty = findViewById(R.id.editTextNumberDecimal);
        price = findViewById(R.id.editTextNumberDecimal2);

        btnBrand = findViewById(R.id.button);
        btnCategory = findViewById(R.id.button2);
        btnImage = findViewById(R.id.button3);
        btnCancle = findViewById(R.id.button4);
        btnAdd = findViewById(R.id.button5);
        back = findViewById(R.id.imageView7);
        back.setOnClickListener(v -> finish());
    }
}