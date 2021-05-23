package com.example.doan.tabforMain;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;
import com.example.doan.adapter.CategoryAdapter;
import com.example.doan.adapter.ProductAdapter;
import com.example.doan.api.apiCategory;
import com.example.doan.inter.Service;
import com.example.doan.model.Category;
import com.example.doan.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tabCategory extends Fragment {

    Service service;
    apiCategory serviceCate = service.retrofit.create(apiCategory.class);

    private List<Category> categoryList;
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter adapterCategory;
    private Context context;
    private View view;
    private Bundle bundle;
    private String token;

    @Override
    public void onResume() {
        super.onResume();
        if (bundle!=null) {
            token = bundle.getString("token");
            Log.d("runrun", "Frag: "+token);
            getListCategory(token);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_tab_category, container, false);
        context = getActivity();
        bundle = this.getArguments();
        intUI();
        if (bundle!=null) {
            token = bundle.getString("token");
            Log.d("runrun", "Frag: "+token);
            getListCategory(token);
        }
        return view;
    }

    private void intUI() {
        categoryRecyclerView = view.findViewById(R.id.cat_recycler);
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

    private void setCategoryRecyclerView(List<Category> list) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        adapterCategory = new CategoryAdapter();
        adapterCategory.setCategoryList(list);
        categoryRecyclerView.setAdapter(adapterCategory);
    }
}