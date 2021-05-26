package com.example.doan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;
import com.example.doan.model.itemBill;

import java.util.List;

import static java.lang.Integer.parseInt;

public class adapterBillDetail extends RecyclerView.Adapter<adapterBillDetail.BillDetailHolder> {

    List<itemBill> itemBillList;
    Context context;

    public adapterBillDetail(List<itemBill> itemBillList, Context context) {
        this.itemBillList = itemBillList;
        this.context = context;
    }

    @NonNull
    @Override
    public BillDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bill_detail, parent, false);
        return new BillDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterBillDetail.BillDetailHolder holder, int position) {
        itemBill itemBill = itemBillList.get(position);
        holder.product.setText(itemBill.getProduct());
        holder.qty.setText("x"+parseInt(itemBill.getQuantity()));
        holder.money.setText("$:"+parseInt(itemBill.getPrice()));
    }

    @Override
    public int getItemCount() {
        if (itemBillList!=null) return itemBillList.size();
        return 0;
    }

    public static class BillDetailHolder extends RecyclerView.ViewHolder {

        TextView product, qty, money;

        public BillDetailHolder(View view) {
            super(view);
            product = view.findViewById(R.id.nameItemBillDetail);
            qty = view.findViewById(R.id.qtyItemBillDetail);
            money = view.findViewById(R.id.moneyItemBillDetail);
        }
    }
}
