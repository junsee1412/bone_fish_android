package com.example.doan.intentforUser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.R;
import com.example.doan.api.apiBrand;
import com.example.doan.api.apiCategory;
import com.example.doan.common.Service;
import com.example.doan.common.sqlite;
import com.example.doan.model.Brand;
import com.example.doan.model.Category;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBranCateActivity extends AppCompatActivity {

    private Service service;
    private apiBrand serviceBrand = service.retrofit.create(apiBrand.class);
    private apiCategory serviceCategory = service.retrofit.create(apiCategory.class);

    private sqlite db = new sqlite(this, "bone_fish.sqlite", null, 1);
    private Cursor cursor;

    private ImageView back;
    private TextView title, addtitle;
    private EditText addBC;
    private Button cancle, change;
    private String token, titleStr, tempBC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bran_cate);

        back = findViewById(R.id.imageView9);
        title = findViewById(R.id.textView13);
        addtitle = findViewById(R.id.textView14);
        addBC = findViewById(R.id.editTextTextPersonName2);
        cancle = findViewById(R.id.button8);
        change = findViewById(R.id.button9);

        back.setOnClickListener(v -> finish());
        cancle.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        titleStr = intent.getStringExtra("title");

        title.setText(titleStr);
        addtitle.setText("Add "+titleStr);
        change.setOnClickListener(v -> addBC(titleStr));
    }

    private void addBC(String title) {
        tempBC = addBC.getText().toString();
        if (tempBC.isEmpty()){
            Toast.makeText(this, "Input is Empty", Toast.LENGTH_SHORT);
        } else {
            if (title.equals("Brand")) {
                serviceBrand.createBrand(token, tempBC).enqueue(new Callback<Brand>() {
                    @Override
                    public void onResponse(Call<Brand> call, Response<Brand> response) {
                        if (response.isSuccessful()) {
                            Brand brand = response.body();
                            System.out.println(brand.get_id());
                            db.QueryData("INSERT INTO bran (id, id_user, bran) VALUES(" +
                                    "'"+brand.get_id()+"'," +
                                    "'"+brand.getId_user()+"'," +
                                    "'"+brand.getBrand()+"')");
                            Toast.makeText(AddBranCateActivity.this, "Add Success", Toast.LENGTH_SHORT);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Brand> call, Throwable t) {
                        Toast.makeText(AddBranCateActivity.this, "Server Error", Toast.LENGTH_SHORT);
                    }
                });
            } else {
                serviceCategory.createCategory(token, tempBC).enqueue(new Callback<Category>() {
                    @Override
                    public void onResponse(Call<Category> call, Response<Category> response) {
                        if (response.isSuccessful()) {
                            Category category = response.body();
                            System.out.println(category.get_id());
                            db.QueryData("INSERT INTO cate (id, id_user, cate) VALUES(" +
                                    "'"+category.get_id()+"'," +
                                    "'"+category.getId_user()+"'," +
                                    "'"+category.getCategory()+"')");
                            Toast.makeText(AddBranCateActivity.this, "Add Success", Toast.LENGTH_SHORT);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Category> call, Throwable t) {
                        Toast.makeText(AddBranCateActivity.this, "Server Error", Toast.LENGTH_SHORT);
                    }
                });
            }
        }
    }
}