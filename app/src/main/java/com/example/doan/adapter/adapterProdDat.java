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

public class adapterProdDat extends RecyclerView.Adapter<adapterProdDat.ProdDatHolder> {

    private List<Product> productList;
    private Context context;

    public adapterProdDat(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProdDatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_data_product, parent, false);
        return new  ProdDatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterProdDat.ProdDatHolder holder, int position) {
        Product product = productList.get(position);
        holder.product.setText(product.getProduct());
        holder.price.setText(String.valueOf(product.getPrice()));
        holder.stock.setText(String.valueOf(product.getStock()));
        Picasso.get().load("http://192.168.1.23:3000"+product.getImg()).into(holder.img);

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
        if (productList!=null) return productList.size();
        return 0;
    }

    public static class ProdDatHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView product, price, stock;

        public ProdDatHolder (View view) {
            super(view);
            img = view.findViewById(R.id.imageProdData);
            product = view.findViewById(R.id.nameProdData);
            price = view.findViewById(R.id.pricProdData);
            stock = view.findViewById(R.id.stocProdData);
        }
    }
}
