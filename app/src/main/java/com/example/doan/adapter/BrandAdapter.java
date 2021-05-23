package com.example.doan.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;
import com.example.doan.model.productInBrand;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandHolder> {

    Context context;
    List<productInBrand> inBrandList;

    public void setInBrandList(Context context, List<productInBrand> inBrandList) {
        this.context = context;
        this.inBrandList = inBrandList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BrandHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_brand, parent, false);
        Log.d("runrun", "1"+context.toString());
        Log.d("runrun", "2"+parent.getContext().toString());
        return new  BrandHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandAdapter.BrandHolder holder, int position) {
        productInBrand inBrand = inBrandList.get(position);
        Log.d("runrun", context.toString());
        LinearLayoutManager linear = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.proRecy.setLayoutManager(linear);

        ProductAdapter productAdapter = new ProductAdapter();
        productAdapter.setProductList(inBrand.getProductList());

        holder.proRecy.setAdapter(productAdapter);
        holder.proRecy.setFocusable(false);
        holder.brand_name.setText(inBrand.getBrand());
        holder.qty.setText("("+inBrand.getProductList().size()+")");
    }

    @Override
    public int getItemCount() {
        return inBrandList.size();
    }

    public static class BrandHolder extends RecyclerView.ViewHolder {

        TextView brand_name, qty;
        RecyclerView proRecy;

        public BrandHolder(View itemView) {
            super(itemView);
            brand_name = itemView.findViewById(R.id.item_Brand);
            qty = itemView.findViewById(R.id.quantity_product);
            proRecy = (RecyclerView) itemView.findViewById(R.id.pro_recycler);
        }
    }
}
