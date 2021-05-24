package com.example.doan.adapter;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.doan.tabforMain.tabBill;
import com.example.doan.tabforMain.tabCategory;
import com.example.doan.tabforMain.tabProduct;
import com.example.doan.tabforMain.tabUser;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private String token;
    private Bundle bundle;

    public void setArrayData(String token)
    {
        this.token = token;
    }

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        bundle = new Bundle();
        bundle.putString("token", token);
        Log.d("runrun", "adap: "+token);
        switch (position) {
            case 0:
                tabProduct tabProduct = new tabProduct();
                tabProduct.setArguments(bundle);

                return tabProduct;
            case 1:
                tabCategory tabCategory = new tabCategory();
                tabCategory.setArguments(bundle);

                return tabCategory;
            case 2:
                tabBill tabBill = new tabBill();
                tabBill.setArguments(bundle);

                return tabBill;
            case 3:
                tabUser tabUser = new tabUser();
                tabUser.setArguments(bundle);

                return tabUser;
            default:
                return new  tabProduct();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
