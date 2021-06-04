package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.api.apiProduct;
import com.example.doan.common.Service;
import com.example.doan.common.sqlite;
import com.example.doan.model.Product;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Integer.parseInt;

public class ProductActivity extends AppCompatActivity {
    String url="https://bone-fish.herokuapp.com";
//        String url="http://192.168.1.23:3000";

    String token;
    sqlite db = new sqlite(this, "bone_fish.sqlite", null, 1);
    Cursor cursor;
    Service service;
    apiProduct servicePro = service.retrofit.create(apiProduct.class);

    TextView protxt, pricetxt, qtytxt;
    String productid, prostr, pricestr, qtystr, imgstr;
    ImageView img, back, edit, delete, addcart;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent = getIntent();

        productid = intent.getStringExtra("idproduct");
        prostr = intent.getStringExtra("product");
        pricestr = intent.getStringExtra("price");
        qtystr = intent.getStringExtra("qty");
        imgstr = intent.getStringExtra("img");

        protxt = findViewById(R.id.productName);
        pricetxt = findViewById(R.id.productPrice);
        qtytxt = findViewById(R.id.qty);
        img = findViewById(R.id.img);

        protxt.setText(prostr);
        pricetxt.setText(pricestr);
        qtytxt.setText(qtystr);
        Picasso.get().load(url+imgstr).into(img);

        back = findViewById(R.id.back2);
        edit = findViewById(R.id.btnEditPro);
        addcart = findViewById(R.id.addToCart);
        delete = findViewById(R.id.delProduct);

        back.setOnClickListener(v -> finish());
        addcart.setOnClickListener(v -> addToCard());
        delete.setOnClickListener(v -> delProduct());
        edit.setOnClickListener(v -> editProduct());
    }

    private void addToCard() {
        cursor = db.GetData("SELECT * FROM bill WHERE id_product='"+productid+"'");
        if (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int newQty = cursor.getInt(2)+1;
            int newPri = parseInt(pricestr)*newQty;
            Log.d("runrun","Co sp");
            db.QueryData("UPDATE bill SET quantity="+newQty+", price="+newPri+" WHERE id="+id);
        } else {
            Log.d("runrun","ko co sp");
            db.QueryData("INSERT INTO bill VALUES(null, " +
                    "'"+productid+"', " +
                    ""+1+", " +
                    ""+1*parseInt(pricestr)+", " +
                    "'"+prostr+"', " +
                    "'"+imgstr+"')");
        }
        finish();
    }

    private void editProduct() {
        cursor = db.GetData("SELECT * FROM token");
        while (cursor.moveToNext()) {
            token = cursor.getString(1);
        }

        String proStr = protxt.getText().toString();
        String stkStr = qtytxt.getText().toString();
        String priStr = pricetxt.getText().toString();

        if (!proStr.isEmpty() && !stkStr.isEmpty() && !priStr.isEmpty()) {

            int stk = parseInt(stkStr);
            int pri = parseInt(priStr);

            db.QueryData("UPDATE prod SET product='"+proStr+"', stock="+stk+", price="+pri+" WHERE id='"+productid+"'");
            cursor = db.GetData("SELECT * FROM bill WHERE id_product='"+productid+"'");
            if (cursor.moveToNext()) {
                db.QueryData("UPDATE bill SET product='"+proStr+"' WHERE id_product='"+productid+"'");
            }

            servicePro.updateProduct(token, productid, proStr, stk, pri).enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    if (response.isSuccessful()) {
                        Product product = response.body();
                        Toast.makeText(ProductActivity.this, product.getMessage(), Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(ProductActivity.this, MainActivity.class);
//                        ProductActivity.this.startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    Toast.makeText(ProductActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            });
            finish();

        } else Toast.makeText(this, "Input is Empty", Toast.LENGTH_SHORT).show();
    }

    private void delProduct() {
        db.QueryData("DELETE FROM prod WHERE id='"+productid+"'");
        cursor = db.GetData("SELECT * FROM token");
        while (cursor.moveToNext()) {
            token = cursor.getString(1);
        }

        cursor = db.GetData("SELECT * FROM bill WHERE id_product='"+productid+"'");
        if (cursor.moveToNext()) {
            db.QueryData("DELETE FROM bill WHERE id_product='"+productid+"'");
        }

        servicePro.delProduct(token, productid).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Product product = response.body();
                    Toast.makeText(ProductActivity.this, product.getMessage(), Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(ProductActivity.this, MainActivity.class);
//                    ProductActivity.this.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(ProductActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
        finish();
    }
//    private void dialogProgress() {
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Please wait...");
//        progressDialog.show();
//    }
}