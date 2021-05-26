package com.example.doan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.intentforUser.BillActivity;
import com.example.doan.R;
import com.example.doan.model.Bill;
import com.example.doan.model.itemBill;

import java.util.List;

import static java.lang.Integer.parseInt;

public class adapterBillDat extends RecyclerView.Adapter<adapterBillDat.BillDatHoder> {

    private List<Bill> billList;
    private Context context;

    public adapterBillDat(List<Bill> billList, Context context) {
        this.billList = billList;
        this.context = context;
    }

    @NonNull
    @Override
    public BillDatHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_data_bill, parent, false);
        return new BillDatHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterBillDat.BillDatHoder holder, int position) {
        Bill bill = billList.get(position);
        holder.name.setText(bill.getBill());
        String timestr = bill.getTime();
        holder.time.setText(timestr.substring(0,10)+"     "+timestr.substring(11,16));
        int price = 0;
        List<itemBill> itemBillList = bill.getItems();
        for (itemBill itemBill :itemBillList) {
            price+= parseInt(itemBill.getPrice());
        }
        holder.price.setText("$: "+String.valueOf(price));
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BillActivity.class);
            intent.putExtra("id", bill.get_id());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (billList!=null) return billList.size();
        return 0;
    }

    public static class BillDatHoder extends RecyclerView.ViewHolder {

        TextView name, time, price;

        public BillDatHoder(View view) {
            super(view);
            name = view.findViewById(R.id.nameBillData);
            time = view.findViewById(R.id.timeBillData);
            price = view.findViewById(R.id.priceBillData);
        }
    }
}
