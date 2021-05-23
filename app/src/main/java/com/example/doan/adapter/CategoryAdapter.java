package com.example.doan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;
import com.example.doan.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    List<Category> categoryList;

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent,false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryHolder holder, int position) {
        Category cate = categoryList.get(position);
        holder.category.setText(cate.getCategory());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryHolder extends RecyclerView.ViewHolder {

        ImageView more;
        TextView category;

        public CategoryHolder(View itemView) {
            super(itemView);
            more = itemView.findViewById(R.id.item_Category_more);
            category = itemView.findViewById(R.id.item_Category);
        }
    }
}
