package com.salagroup.salaman.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.salagroup.salaman.fragments.LoginFragment;


public class LoginActivity extends FragmentActivity {

    private FragmentManager mFragmentManager;
    private LoginFragment mLoginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentManager = getSupportFragmentManager();

        mLoginFragment = new LoginFragment();

        mFragmentManager.beginTransaction()
                .add(android.R.id.content, mLoginFragment, "LoginFragment")
                .commit();
    }
}
