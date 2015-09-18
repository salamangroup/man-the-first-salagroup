package com.salagroup.salaman.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.salagroup.salaman.R;
import com.salagroup.salaman.fragments.LoginFragment;


public class LoginActivity extends FragmentActivity {

    private FragmentManager mFragmentManager;
    private LoginFragment mLoginFragment;
    private LoginButton loginButton;
    CallbackManager mCallBackMAnager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallBackMAnager = CallbackManager.Factory.create();
        setContentView(R.layout.fragment_login);

        mFragmentManager = getSupportFragmentManager();

        mLoginFragment = new LoginFragment();

        mFragmentManager.beginTransaction()
                .add(android.R.id.content, mLoginFragment, "LoginFragment")
                .commit();
    }
}
