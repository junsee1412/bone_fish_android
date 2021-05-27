package com.example.doan.intentforUser;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.R;
import com.example.doan.api.apiProduct;
import com.example.doan.common.RealPathUtil;
import com.example.doan.common.Service;
import com.example.doan.common.sqlite;
import com.example.doan.model.Product;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Integer.parseInt;

public class AddProductActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 100;

    private Service service;
    private apiProduct servicePro = service.retrofit.create(apiProduct.class);

    private sqlite db = new sqlite(this, "bone_fish.sqlite", null, 1);
    private Cursor cursor;

    private ImageView back, thubnail;
    private TextView brand, category;
    private EditText product, qty, price;
    private Button btnBrand, btnCategory, btnImage, btnCancle, btnAdd;
    private HashMap<String, String> bra, cat;
    private String[] keybra, keycat;
    private Uri mUri;
    private String token;
    ProgressDialog progressDialog;

    ActivityResultLauncher<Intent> launchGallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d("Gallery", "on Gallery");
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            thubnail.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        brand = findViewById(R.id.textView9);
        category = findViewById(R.id.textView10);
        thubnail = findViewById(R.id.thubnail);

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
        btnCancle.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        token = intent.getStringExtra("token");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        queryData();

        btnBrand.setOnClickListener(v -> choseBrand());
        btnCategory.setOnClickListener(v -> choseCategory());
        btnImage.setOnClickListener(v -> onCickPermission());
        btnAdd.setOnClickListener(v -> clickBtnAdd());
    }

    private void queryData() {
        ArrayList<String> forbra = new ArrayList<>();
        forbra.add("None");
        bra = new HashMap<>();
        cursor = db.GetData("SELECT * FROM bran");
        while (cursor.moveToNext()) {
            forbra.add(cursor.getString(2));
            bra.put(cursor.getString(2), cursor.getString(0));
        }
        keybra = forbra.toArray(new String[0]);

        ArrayList<String> forcat = new ArrayList<>();
        forcat.add("None");
        cat = new HashMap<>();
        cursor = db.GetData("SELECT * FROM cate");
        while (cursor.moveToNext()) {
            forcat.add(cursor.getString(2));
            cat.put(cursor.getString(2), cursor.getString(0));
        }
        keycat = forcat.toArray(new String[0]);
    }

    private void choseBrand() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddProductActivity.this);
        builder.setTitle("Chose Brand");
        builder.setSingleChoiceItems(keybra, 0, (dialogInterface, i) -> {
            if (!keybra[i].equals("None")) {
                brand.setText(keybra[i]);
            } else {
                brand.setText("");
            }
        });
        builder.setPositiveButton("Chose", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        builder.show();
    }

    private void choseCategory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddProductActivity.this);
        builder.setTitle("Chose Brand");
        builder.setSingleChoiceItems(keycat, 0, (dialogInterface, i) -> {
            if (!keycat[i].equals("None")) {
                category.setText(keycat[i]);
            } else {
                category.setText("");
            }
        });
        builder.setPositiveButton("Chose", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        builder.show();
    }

    private void onCickPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE)
        if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launchGallery.launch(Intent.createChooser(intent, "Select Picture"));
    }

    private void clickBtnAdd() {
        String getProduct, getBrand, getCategory, getQty, getPrice;
        getProduct = product.getText().toString();
        getBrand = bra.get(brand.getText().toString());
        getCategory = cat.get(category.getText().toString());
        getQty = qty.getText().toString();
        getPrice = price.getText().toString();

        if (!getProduct.isEmpty() && !getBrand.isEmpty() && !getCategory.isEmpty() && !getQty.isEmpty() && !getPrice.isEmpty() && mUri!=null) {
            addProduct(getProduct, getBrand, getCategory, getQty, getPrice, mUri);
        } else Toast.makeText(this, "Something is Null", Toast.LENGTH_SHORT);
    }

    private void addProduct(String getProduct, String getBrand, String getCategory, String getQty, String getPrice, Uri mUri) {

        progressDialog.show();
        RequestBody rqToken = RequestBody.create(MediaType.parse("multipart/form-data"), token);
        RequestBody rqProduct = RequestBody.create(MediaType.parse("multipart/form-data"), getProduct);
        RequestBody rqBrand = RequestBody.create(MediaType.parse("multipart/form-data"), getBrand);
        RequestBody rqCategory = RequestBody.create(MediaType.parse("multipart/form-data"), getCategory);
        RequestBody rqQty = RequestBody.create(MediaType.parse("multipart/form-data"), getQty);
        RequestBody rqPrice = RequestBody.create(MediaType.parse("multipart/form-data"), getPrice);

        String tmpPathUri = RealPathUtil.getRealPath(this, mUri);
        File file = new File(tmpPathUri);
        RequestBody rqImg = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part multiImg = MultipartBody.Part.createFormData("image", file.getName(), rqImg);
        Log.d("Add", tmpPathUri);


        servicePro.createProduct(rqToken, rqBrand, rqCategory, rqProduct, rqQty, rqPrice, multiImg).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Product product = response.body();
                    if (product.getMessage()==null) {
                        db.QueryData("INSERT INTO prod (id, id_brand, id_category, id_user, product, stock, price, image) VALUES" +
                                "('"+product.get_id()+"', '"+product.getId_brand()+"', '"+product.getId_category()+"'," +
                                " '"+product.getId_user()+"', '"+product.getProduct()+"', "+product.getStock()+"," +
                                " "+product.getPrice()+", '"+product.getImg()+"')");

                        Toast.makeText(AddProductActivity.this, "Create Success", Toast.LENGTH_SHORT).show();

                        finish();
                    } else {
                        Toast.makeText(AddProductActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                        Log.d("Add", product.getMessage());
                    }
                    progressDialog.hide();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(AddProductActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        });
    }
}