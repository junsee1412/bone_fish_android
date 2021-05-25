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
import com.example.doan.api.apiCategory;
import com.example.doan.common.Service;
import com.example.doan.common.sqlite;
import com.example.doan.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adapterCateDat extends RecyclerView.Adapter<adapterCateDat.CateDatHolder> {

    private List<Category> categoryList;
    private Context context;
    private String token;
    private Service service;
    private apiCategory apiCategory = service.retrofit.create(apiCategory.class);

    public adapterCateDat(List<Category> categoryList, Context context, String token) {
        this.categoryList = categoryList;
        this.context = context;
        this.token = token;
    }

    @NonNull
    @Override
    public CateDatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_data_brand_category, parent, false);
        return new CateDatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterCateDat.CateDatHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.name.setText(category.getCategory());
        holder.option.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, v);
            popupMenu.inflate(R.menu.menu_popup);
            popupMenu.setOnMenuItemClickListener(item1 -> {
                switch (item1.getItemId()) {
                    case R.id.item_Edit:
                        String getName = holder.name.getText().toString();
                        holder.name.clearFocus();
                        if (getName.isEmpty()) Toast.makeText(context, "Name is Empty", Toast.LENGTH_SHORT).show();
                        else EditItem(category.get_id(), getName);
                        return true;
                    case R.id.item_Remove:
                        RemoveItem(position, category.get_id());
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
        if (categoryList!=null) return categoryList.size();
        return 0;
    }

    public static class CateDatHolder extends RecyclerView.ViewHolder {

        EditText name;
        ImageView option;

        public CateDatHolder(View view) {
            super(view);
            name = view.findViewById(R.id.editTextDataBrand_Category);
            option = view.findViewById(R.id.optionBrand_Category);
        }
    }

    private void EditItem(String id, String category) {
        sqlite db = new sqlite(context, "bone_fish.sqlite", null, 1);
        db.QueryData("UPDATE cate SET cate='"+category+"' WHERE id='"+id+"'");
        apiCategory.updateCategory(token, id, category).enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                Toast.makeText(context, "Update Category", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void RemoveItem(int p, String id) {
        sqlite db = new sqlite(context, "bone_fish.sqlite", null, 1);
        db.QueryData("DELETE FROM cate WHERE id='"+id+"'");
        categoryList.remove(p);
        notifyDataSetChanged();
        apiCategory.delCategory(token, id).enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                Toast.makeText(context, "Delete Category", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
