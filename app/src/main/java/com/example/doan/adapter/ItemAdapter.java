package com.example.doan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;
import com.example.doan.model.itemBill;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {

    Context context;
    List<itemBill> bills;

    public void setListItem(Context context, List<itemBill> bills) {
        this.context = context;
        this.bills = bills;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemHolder holder, int position) {
        itemBill item = bills.get(position);
        holder.product.setText(item.getProduct());
        holder.quantity.setText("("+String.valueOf(item.getQuantity())+")");
        holder.price.setText(String.valueOf(item.getPrice()));
        Picasso.get().load("http://192.168.1.23:3000"+item.getImage()).into(holder.imageBill);
//        holder.
    }

    @Override
    public int getItemCount() {
        if (bills!=null) return bills.size();
        return 0;
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {

        ImageView imageBill, option;
        TextView product, price, quantity;

        public ItemHolder(View itemView) {
            super(itemView);
            option = itemView.findViewById(R.id.option_item_bill);
            imageBill = itemView.findViewById(R.id.imageBill);
            product = itemView.findViewById(R.id.nameItemBill);
            price = itemView.findViewById(R.id.priceItemBill);
            quantity = itemView.findViewById(R.id.qtyBill);
        }
    }
}