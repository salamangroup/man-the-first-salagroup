package com.salagroup.salaman.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.salagroup.salaman.R;
import com.salagroup.salaman.network.NetworkUtil;

import org.json.JSONObject;

import java.util.Arrays;


public class LoginActivity extends FragmentActivity implements View.OnClickListener {

    private CallbackManager mCallBackMAnager;
    private AccessTokenTracker mAccessTokenTracker;
    private ProfileTracker mProfileTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isConnected = NetworkUtil.isNetworkConnected(this);
        setContentView(R.layout.fragment_login);

        if (isConnected){
            FacebookSdk.sdkInitialize(getApplicationContext());
            mCallBackMAnager = CallbackManager.Factory.create();

            onPersonalTracker();

            ImageButton btnLoginWithFacebook = (ImageButton) findViewById(R.id.btnLoginWithFacebook);
            btnLoginWithFacebook.setOnClickListener(this);
        }


    }

    private void onPersonalTracker() {
        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.i("salagroup", currentAccessToken.getToken());
            }
        };

        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

            }
        };

        mAccessTokenTracker.startTracking();
        mProfileTracker.startTracking();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoginWithFacebook:
                onFacebookLogin();
        }
    }

    private void onFacebookLogin() {

        //Set Permission
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "user_photos", "public_profile"));

        LoginManager.getInstance().registerCallback(mCallBackMAnager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i("salagroup", "Token: " + loginResult.getAccessToken().getToken());
                Log.i("salagroup", "UserId: " + loginResult.getAccessToken().getUserId());

                GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        if (response.getError() != null) {
                            Log.i("salagroup", "Error");
                        } else {
                            Log.i("salagroup", "Success");
                            Log.i("salagroup", "Result: " + String.valueOf(object));
                            String email = object.optString("email");

                            Log.i("salagroup", "Email: " + email);

                        }
                    }
                }).executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.i("salagroup", "Error");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallBackMAnager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Check network Status
        int networkType = NetworkUtil.getConnectivityType(this);
        String status = NetworkUtil.getConnectivityStatus(networkType);
        if (status != null ){
            Toast.makeText(this,status,Toast.LENGTH_LONG).show();
        }
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);

        try {
            Profile profile = Profile.getCurrentProfile();
            onShowInfo(profile);
        } catch (NullPointerException e) {
            Log.i("salagroup", "profile = null");
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    private void onShowInfo(Profile profile) {
        EditText edtUsername = (EditText) findViewById(R.id.edtUsername);
        EditText edtPasswd = (EditText) findViewById(R.id.edtPassword);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mAccessTokenTracker.stopTracking();
        mProfileTracker.stopTracking();
    }
}
