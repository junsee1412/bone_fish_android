package com.example.doan.tabforMain;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.doan.R;
import com.example.doan.adapter.BrandAdapter;
import com.example.doan.adapter.CategoryAdapter;
import com.example.doan.api.apiBrand;
import com.example.doan.api.apiCategory;
import com.example.doan.common.Service;
import com.example.doan.api.apiProduct;
import com.example.doan.model.Brand;
import com.example.doan.model.Category;
import com.example.doan.model.Product;
import com.example.doan.model.productInBrand;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tabProduct extends Fragment {

    Service service;
    apiProduct servicePro = service.retrofit.create(apiProduct.class);
    apiBrand serviceBra = service.retrofit.create(apiBrand.class);
    apiCategory serviceCate = service.retrofit.create(apiCategory.class);

    private List<Product> productList;
    private List<Brand> brandList;
    private List<Category> categoryList;
    private List<productInBrand> inBrandList;
    private RecyclerView brandRecyclerView;
    private BrandAdapter brandAdapter;
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter adapterCategory;
    private View view;
    private Context context;
    private String token;
    private SearchView searchView;
    private Bundle bundle;

    @Override
    public void onResume() {
        super.onResume();
        if (bundle != null) {
            token = bundle.getString("token");
            checkList();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_product, container, false);
        context = getActivity();
        bundle = this.getArguments();
        intUI();
        if (bundle != null) {
            token = bundle.getString("token");
            getProduct(token);
            getBrand(token);
            getListCategory(token);
        }
        return view;
    }

    private void intUI() {

        brandRecyclerView = view.findViewById(R.id.bra_recycler);
        categoryRecyclerView = view.findViewById(R.id.cate_recy);
        searchView = view.findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterPro(s,1);
                return false;
            }
        });
    }

    private void getProduct(String token) {
        servicePro.getlsProduct(token).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()) {
                    List<Product> productList = response.body();
                    setListPr(productList);
                    checkList();
                }
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("error:", "Error");
            }
        });
    }

    private void getBrand(String token) {
        serviceBra.getlsBrand(token).enqueue(new Callback<List<Brand>>() {
            @Override
            public void onResponse(Call<List<Brand>> call, Response<List<Brand>> response) {
                if (response.isSuccessful()) {
                    List<Brand> brandList = response.body();
                    setListBr(brandList);
                    checkList();
                }
            }
            @Override
            public void onFailure(Call<List<Brand>> call, Throwable t) {
                Log.d("error:", "Error");
            }
        });
    }

    private void getListCategory(String token) {
        serviceCate.getlsCategory(token).enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    categoryList = response.body();
                    setCategoryRecyclerView(categoryList);
                }
            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d("error:", "Error");
            }
        });
    }

    private void checkList() {
        if (productList!=null && brandList!=null){
            Log.d("runrun","okela");
            inBrandList = new ArrayList<>();

            for (Brand brand :brandList){
                String brandID = brand.get_id();
                String brandName = brand.getBrand();
                List<Product> products = new ArrayList<>();
                for (Product product: productList) {
                    String brandID_Pr = product.getId_brand();
                    if (brandID_Pr.equals(brandID)){
                        products.add(product);
                    }
                }
                if (products.size()!=0) {
                    inBrandList.add(new productInBrand(brandID, brandName, products));
                }
            }
            setProductRecyclerView(inBrandList);
        }
    }

    private void setListPr(List<Product> productList) { this.productList = productList; }

    private void setListBr(List<Brand> brandList) { this.brandList = brandList; }

    private void filterPro(String text, int i) {
        inBrandList = new ArrayList<>();
        if (productList!=null && brandList!=null) {
            for (Brand brand :brandList){
                String brandID = brand.get_id();
                String brandName = brand.getBrand();
                List<Product> products = new ArrayList<>();

                for (Product product: productList) {
                    String brandID_Pr = product.getId_brand();
                    String categoryID_Pr = product.getId_category();
                    String productName = product.getProduct();

                    if (brandID_Pr.equals(brandID) && productName.toLowerCase().contains(text.toLowerCase()) && i==1){
                        products.add(product);
                    } else if (brandID_Pr.equals(brandID) && categoryID_Pr.equals(text) && i==2) {
                        products.add(product);
                    }
                }
                if (products.size()!=0) {
                    inBrandList.add(new productInBrand(brandID, brandName, products));
                }
            }
            setProductRecyclerView(inBrandList);
        }
    }

    private void setProductRecyclerView(List<productInBrand> list) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        brandRecyclerView.setLayoutManager(layoutManager);

        brandAdapter = new BrandAdapter();
        brandAdapter.setInBrandList(context,list);
        brandRecyclerView.setAdapter(brandAdapter);
    }

    private void setCategoryRecyclerView(List<Category> list) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        adapterCategory = new CategoryAdapter();
        adapterCategory.setCategoryList(list);
        categoryRecyclerView.setAdapter(adapterCategory);
    }
}