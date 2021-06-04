package com.example.doan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.ProductActivity;
import com.example.doan.R;
import com.example.doan.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    String url="https://bone-fish.herokuapp.com";

//    String url="http://192.168.1.23:3000";

    List<Product> productList;
    Context context;

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductHolder holder, int position) {
        Product product = productList.get(position);
        holder.nameProduct.setText(product.getProduct());
        holder.price.setText(String.valueOf(product.getPrice()));
        holder.quantity.setText(String.valueOf(product.getStock()));
        Picasso.get().load(url+product.getImg()).into(holder.img);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductActivity.class);
            intent.putExtra("idproduct", product.get_id());
            intent.putExtra("product", product.getProduct());
            intent.putExtra("price", String.valueOf(product.getPrice()));
            intent.putExtra("qty", String.valueOf(product.getStock()));
            intent.putExtra("img", product.getImg());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (productList==null) return 0;
        return productList.size();
    }

    public static class ProductHolder extends RecyclerView.ViewHolder {

        TextView nameProduct;
        TextView price;
        TextView quantity;
        ImageView img;

        public ProductHolder(View itemView) {

            super(itemView);

            nameProduct = itemView.findViewById(R.id.product);
            price = itemView.findViewById(R.id.price_num);
            quantity = itemView.findViewById(R.id.quantity_num);
            img = itemView.findViewById(R.id.imgProduct);
        }
    }
}
