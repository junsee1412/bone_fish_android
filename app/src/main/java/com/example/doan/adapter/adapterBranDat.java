package com.example.doan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;
import com.example.doan.api.apiBrand;
import com.example.doan.common.Service;
import com.example.doan.common.sqlite;
import com.example.doan.model.Brand;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adapterBranDat extends RecyclerView.Adapter<adapterBranDat.BrandDatHolder> {

    private List<Brand> brandList;
    private Context context;
    private String token;
//    Cursor cursor;
    private Service service;
    private apiBrand serviceBra = service.retrofit.create(apiBrand.class);

    public adapterBranDat(List<Brand> brandList, Context context, String token) {
        this.brandList = brandList;
        this.context = context;
        this.token = token;
    }

    @NonNull
    @Override
    public BrandDatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_data_brand_category, parent, false);
        return new BrandDatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterBranDat.BrandDatHolder holder, int position) {
        Brand brand = brandList.get(position);
        holder.name.setText(brand.getBrand());
        holder.option.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, v);
            popupMenu.inflate(R.menu.menu_popup);
            popupMenu.setOnMenuItemClickListener(item1 -> {
                switch (item1.getItemId()) {
                    case R.id.item_Edit:
                        String getName = holder.name.getText().toString();
                        holder.name.clearFocus();
                        if (getName.isEmpty()) Toast.makeText(context, "Name is Empty", Toast.LENGTH_SHORT).show();
                        else EditItem(brand.get_id(), getName);
                        return true;
                    case R.id.item_Remove:
                        RemoveItem(position, brand.get_id());
                        return true;
                    default:
                        return false;
                }
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        if (brandList!=null) return brandList.size();
        return 0;
    }

    public static class BrandDatHolder extends RecyclerView.ViewHolder {

        EditText name;
        ImageView option;

        public BrandDatHolder(View view) {
            super(view);
            name = view.findViewById(R.id.editTextDataBrand_Category);
            option = view.findViewById(R.id.optionBrand_Category);
        }
    }

    private void EditItem(String id, String brand) {
        sqlite db = new sqlite(context, "bone_fish.sqlite", null, 1);
        db.QueryData("UPDATE bran SET bran='"+brand+"' WHERE id='"+id+"'");
        serviceBra.updateBrand(token, id, brand).enqueue(new Callback<Brand>() {
            @Override
            public void onResponse(Call<Brand> call, Response<Brand> response) {
                Toast.makeText(context, "Update Brand", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Brand> call, Throwable t) {
                Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void RemoveItem(int p, String id){
        sqlite db = new sqlite(context, "bone_fish.sqlite", null, 1);
        db.QueryData("DELETE FROM bran WHERE id='"+id+"'");
        System.out.println(id);
        brandList.remove(p);
        notifyDataSetChanged();
        serviceBra.delBrand(token, id).enqueue(new Callback<Brand>() {
            @Override
            public void onResponse(Call<Brand> call, Response<Brand> response) {
                Toast.makeText(context, "Delete Brand", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Brand> call, Throwable t) {
                Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
