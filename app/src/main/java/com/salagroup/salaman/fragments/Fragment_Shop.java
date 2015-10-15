package com.salagroup.salaman.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.salagroup.salaman.R;
import com.salagroup.salaman.adapter.ShopAdapter;
import com.salagroup.salaman.pojo.ShopInfo;

import java.util.ArrayList;

/**
 * Created by MyPC on 02/10/2015.
 */
public class Fragment_Shop extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shop, container, false);

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_shop);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        ShopAdapter adapter = new ShopAdapter(createData());
        mRecyclerView.setAdapter(adapter);


        return rootView;
    }

    private ArrayList<ShopInfo> createData() {
        ArrayList<ShopInfo> listShop = new ArrayList<ShopInfo>();
        listShop.add(new ShopInfo("MinhMinh", "tp hcm", " 0903759394"));
        listShop.add(new ShopInfo("MinhMinh", "tp hcm", " 0903759394"));
        listShop.add(new ShopInfo("MinhMinh", "tp hcm", " 0903759394"));
        listShop.add(new ShopInfo("MinhMinh", "tp hcm", " 0903759394"));


        return listShop;
    }
}
