package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ProductActivity extends AppCompatActivity {

    TextView protxt, pricetxt, qtytxt;
    String prostr, pricestr, qtystr, imgstr;
    ImageView img, back, edit, delete, addcart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent = getIntent();

        prostr = intent.getStringExtra("product");
        pricestr = intent.getStringExtra("price");
        qtystr = intent.getStringExtra("qty");
        imgstr = intent.getStringExtra("img");

        protxt = findViewById(R.id.productName);
        pricetxt = findViewById(R.id.productPrice);
        qtytxt = findViewById(R.id.qty);
        img = findViewById(R.id.img);

        back = findViewById(R.id.back2);
        edit = findViewById(R.id.btnEditPro);
        addcart = findViewById(R.id.addToCart);
        delete = findViewById(R.id.delProduct);

        protxt.setText(prostr);
        pricetxt.setText(pricestr);
        qtytxt.setText(qtystr);
        Picasso.get().load("http://192.168.1.23:3000"+imgstr).into(img);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}