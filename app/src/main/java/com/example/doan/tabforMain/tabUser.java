package com.example.doan.tabforMain;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.doan.R;

public class tabUser extends Fragment {

    private String token;
    private Bundle bundle;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_user, container, false);
        intUI();
        bundle = this.getArguments();
        if (getArguments() != null) {
            token = bundle.getString("token");
        }
        Log.d("runrun","okela: "+token);
        return view;
    }

    private void intUI() {
    }
}