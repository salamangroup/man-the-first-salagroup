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
import com.salagroup.salaman.adapter.ContactAdapter;
import com.salagroup.salaman.pojo.ContactInfo;

import java.util.ArrayList;

/**
 * Created by MyPC on 02/10/2015.
 */
public class Fragment_Customer extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer, container, false);
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_customer);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        ContactAdapter adapter = new ContactAdapter(createData());
        mRecyclerView.setAdapter(adapter);
        return rootView;
    }

    private ArrayList<ContactInfo> createData() {
        ArrayList<ContactInfo> listContact = new ArrayList<>();
        listContact.add(new ContactInfo("Minh", "Tphcm", "0903759493"));
        listContact.add(new ContactInfo("Minh", "Tphcm", "0903759493"));
        listContact.add(new ContactInfo("Minh", "Tphcm", "0903759493"));
        listContact.add(new ContactInfo("Minh", "Tphcm", "0903759493"));
        listContact.add(new ContactInfo("Minh", "Tphcm", "0903759493"));
        return listContact;
    }
}
