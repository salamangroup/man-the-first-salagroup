package com.salagroup.salaman.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.salagroup.salaman.R;
import com.salagroup.salaman.adapter.SettingAdapter;
import com.salagroup.salaman.pojo.KeySettingType;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by MyPC on 10/10/2015.
 */
public class Fragment_SettingSystem extends Fragment {
    private SettingAdapter mAdapter;
    private List<KeySettingType> lstKey;
    private ListView lstSetting;

    public void addItemNavigation(String tittle, int imageIcon, boolean arrow) {
        lstKey.add(new KeySettingType(SettingAdapter.TYPE_NAVIGATION_SETTING, tittle, imageIcon, arrow));
    }

    public void addTitle(final String tittle) {
        lstKey.add(new KeySettingType(SettingAdapter.TYPE_TILTE, tittle));
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        lstSetting = (ListView) rootView.findViewById(R.id.lstSetting);
        lstKey = new ArrayList<KeySettingType>();
        addTitle("Tài khoản");
        addItemNavigation(getString(R.string.navigation_change_password), R.drawable.ic_lock, false);
        addItemNavigation(getString(R.string.navigation_logout), R.drawable.ic_logout, false);
        addTitle("Hệ thống");
        addItemNavigation(getString(R.string.navigation_passcode), R.drawable.ic_protect_app, true);
        addItemNavigation(getString(R.string.navigation_language), R.drawable.ic_language, true);
        addItemNavigation(getString(R.string.navigation_notification), R.drawable.ic_notification, true);
        addItemNavigation(getString(R.string.navigation_rate), R.drawable.ic_rating, true);
        addItemNavigation(getString(R.string.navigation_feedback), R.drawable.ic_feedback, true);
        addItemNavigation(getString(R.string.navigation_about), R.drawable.ic_about, true);
        addItemNavigation(getString(R.string.navigation_help), R.drawable.ic_help, true);
        mAdapter = new SettingAdapter(getActivity(), lstKey);
        lstSetting.setDivider(null);
        lstSetting.setAdapter(mAdapter);
        return rootView;
    }
}
