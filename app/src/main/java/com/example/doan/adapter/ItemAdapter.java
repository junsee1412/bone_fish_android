package com.example.doan.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;
import com.example.doan.common.sqlite;
import com.example.doan.model.itemBill;
import com.squareup.picasso.Picasso;

import java.util.List;

import static java.lang.Integer.parseInt;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {

    Context context;
    List<itemBill> bills;
    Cursor cursor;

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
        holder.quantity.setText(String.valueOf(item.getQuantity()));
        holder.price.setText(String.valueOf(item.getPrice()));
        Picasso.get().load("http://192.168.1.23:3000"+item.getImage()).into(holder.imageBill);
        holder.option.setOnClickListener(v -> {
            sqlite db = new sqlite(context, "bone_fish.sqlite", null, 1);
            PopupMenu popupMenu = new PopupMenu(context, v);
            popupMenu.inflate(R.menu.menu_popup);
            popupMenu.setOnMenuItemClickListener(item1 -> {
                switch (item1.getItemId()) {
                    case R.id.item_Edit:
                        Log.d("run", "Edit "+item.getId_product());
                        String strQty = holder.quantity.getText().toString();
                        if (strQty.isEmpty()) strQty="0";
                        int newQty = parseInt(strQty);
                        if (newQty>0) {
                            EditItem(item.getId_product(), newQty);
                        } else {
                            RemoveItem(position, item.getId_product());
                        }
                        return true;
                    case R.id.item_Remove:
                        Log.d("run", "Remove "+item.getId_product());

                        RemoveItem(position, item.getId_product());
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
        if (bills!=null) return bills.size();
        return 0;
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {

        ImageView imageBill, option;
        TextView product, price;
        EditText quantity;

        public ItemHolder(View itemView) {
            super(itemView);
            option = itemView.findViewById(R.id.option_item_bill);
            imageBill = itemView.findViewById(R.id.imageBill);
            product = itemView.findViewById(R.id.nameItemBill);
            price = itemView.findViewById(R.id.priceItemBill);
            quantity = itemView.findViewById(R.id.qtyBill);
        }
    }

    private void EditItem(String id_product, int newQty) {
        sqlite db = new sqlite(context, "bone_fish.sqlite", null, 1);
        cursor = db.GetData("SELECT * FROM bill WHERE id_product='"+id_product+"'");
        if(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int Oldqty = cursor.getInt(2);
            int Oldprice = cursor.getInt(3);
            int div = Oldprice/Oldqty;
            db.QueryData("UPDATE bill SET quantity="+newQty+", price="+newQty*div+" WHERE id="+id);
        }
        notifyDataSetChanged();
    }
    public void RemoveItem(int p, String id){
        sqlite db = new sqlite(context, "bone_fish.sqlite", null, 1);
        db.QueryData("DELETE FROM bill WHERE id_product='"+id+"'");
        bills.remove(p);
        notifyDataSetChanged();
    }
}
