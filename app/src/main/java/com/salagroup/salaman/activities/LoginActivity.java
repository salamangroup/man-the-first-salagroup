package com.salagroup.salaman.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.activeandroid.query.Select;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.gson.Gson;
import com.salagroup.salaman.R;
import com.salagroup.salaman.asynctask.LoginAsyncTask;
import com.salagroup.salaman.helper.Constant;
import com.salagroup.salaman.helper.EncryptionUtil;
import com.salagroup.salaman.helper.NetworkUtil;
import com.salagroup.salaman.helper.ValidateInputValue;
import com.salagroup.salaman.helper.ValidationHelper;
import com.salagroup.salaman.pojo.ResultLogin;
import com.salagroup.salaman.pojo.ResultLoginFacebook;
import com.salagroup.salaman.pojo.ResultLoginGoogle;
import com.salagroup.salaman.pojo.User;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnKeyListener {

    private static final String LOGIN_API = Constant.PROTOCOL + Constant.DOMAIN + Constant.API_ROOT + Constant.API_LOGIN;
    private static final List<String> PERMISSIONS = new ArrayList<>(Arrays.asList("email", "user_birthday ", "public_profile"));

    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;
    /* RequestCode for resolutions to get GET_ACCOUNTS permission on M */
    private static final int RC_PERM_GET_ACCOUNTS = 2;
    public static int USER_ROOT = 1;
    public static int status;
    private static String uriImageAvatar;
    @Bind(R.id.btnLogin)
    AppCompatButton btnLogin;
    @Bind(R.id.btnLoginWithFacebook)
    AppCompatButton btnLoginWithFacebook;
    @Bind(R.id.btnLoginWithGooglePlus)
    AppCompatButton btnLoginWithGooglePlus;
    @Bind(R.id.btnForgotPassword)
    AppCompatButton btnForgotPassword;
    @Bind(R.id.btnRegister)
    AppCompatButton btnRegister;
    @Bind(R.id.edtUsername)
    EditText edtUserName;
    @Bind(R.id.edtPassword)
    EditText edtPassword;
    private CallbackManager mCallBackManager;
    private AccessTokenTracker mAccessTokenTracker;
    private ProfileTracker mProfileTracker;
    private User user;
    private Profile mProfile;
    private String password = "";
    /* Is there a ConnectionResult resolution in progress? */
    private boolean mIsResolving = false;
    /* Should we automatically resolve ConnectionResults when possible? */
    private boolean mShouldResolve = false;
    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this);
        mCallBackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

//      Build GoogleApiClient with access to basic profile
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(new Scope(Scopes.PROFILE))
                .addScope(new Scope(Scopes.EMAIL))
                .build();

        btnLogin.setOnClickListener(this);
        btnLoginWithFacebook.setOnClickListener(this);
        btnLoginWithGooglePlus.setOnClickListener(this);
        btnForgotPassword.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        edtPassword.setOnKeyListener(this);

        Log.i(Constant.TAG, NetworkUtil.getIpAddr(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        onPersonalTracker();
//        mGoogleApiClient.connect();
        //Chưa có sync nên làm việc với Users mới nhất
        USER_ROOT = new Select().from(User.class).count();
        user = User.getUserById(USER_ROOT);
        if (!NetworkUtil.isNetworkConnected(this)) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
        }
        if (user != null) {
            edtUserName.setText(user.getUsername());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);

    }

    @Override
    public void onClick(View v) {

        /**
         Login with exist account or connect to server to sign in
         */
        if (v.getId() == R.id.btnLogin) {
            int codeCheck = ValidateInputValue.onValidateAccount(this, edtUserName, edtPassword);

            if (codeCheck == 0) {

                //Input value have no errors
                String username = edtUserName.getText().toString();
                String passwd = edtPassword.getText().toString();
                try {
                    password = EncryptionUtil.SHA1(passwd);
                    Log.i(Constant.TAG, password);
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                //Exist account in local Database
                try {
                    if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                        moveToMainScreen();

                    } else throw new NullPointerException();
                } catch (NullPointerException e) {
                    if (NetworkUtil.isNetworkConnected(this)) {
                        Toast.makeText(this, R.string.connect_to_server, Toast.LENGTH_SHORT).show();

                        LoginAsyncTask loginAsyncTask = new LoginAsyncTask();
                        loginAsyncTask.execute(LOGIN_API, Constant.AUTH_SALA, username, password);
                        try {
                            String jsonResult = loginAsyncTask.get();
                            Log.i(Constant.TAG, "Normal: " + jsonResult);
                            ResultLogin resultLogin = (new Gson()).fromJson(jsonResult, ResultLogin.class);

                            if (resultLogin.getSuccess() == 0) {
                                //TODO đợi API sync thông tin từ Server
                                //Không thể lấy thông tin từ facebook để lưu vì khách hàng có thể đã đổi lúc đăng ký.
                                moveToMainScreen();
                            } else {
                                String message = resultLogin.getMessage();
                                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (InterruptedException | ExecutionException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        Toast.makeText(this, R.string.message_re_connected, Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                //Input value have errors
                Toast.makeText(this, R.string.please_check_login_info, Toast.LENGTH_SHORT).show();
            }
        }
        /** Function:
         * Forgot password
         * Sign up with Sala
         * Login with Facebook, if existed FacebookID, Successfully login or move to register with facebook
         * Login with Google Plus, if existed GooglePlusID, Successfully login or move to register with Google Plus
         */
        switch (v.getId()) {
            case R.id.btnForgotPassword:
                Toast.makeText(this, R.string.not_exist_func, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnRegister:

                if (NetworkUtil.isNetworkConnected(this)) {
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    intent.putExtra("loginType", Constant.AUTH_SALA);
                    startActivity(intent);
                } else {
                    //Can't connect to Internet
                    Toast.makeText(this, R.string.message_re_connected, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnLoginWithFacebook:

                if (NetworkUtil.isNetworkConnected(this)) {
                    loginWithFacebook(mCallBackManager, user, mProfile);
                } else {
                    //Can't connect to Internet
                    Toast.makeText(this, R.string.message_re_connected, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnLoginWithGooglePlus:
                if (NetworkUtil.isNetworkConnected(this)) {
                    signInWithGPlus();
                } else {
                    //Can't connect to Internet
                    Toast.makeText(this, R.string.message_re_connected, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void signInWithGPlus() {
        // User clicked the sign-in button, so begin the sign-in process and automatically
        // attempt to resolve any errors that occur.
        mShouldResolve = true;
        mGoogleApiClient.connect();
    }

    /**
     * @param isSignedIn true if Login, else false
     */
    private void updateUI(boolean isSignedIn) {

        if (isSignedIn) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            if (currentPerson != null) {
                //Show signed-in user's name
                String displayName = currentPerson.getDisplayName();
                String gg_id = currentPerson.getId();
                int gender = currentPerson.getGender();
                String email = "";
                Log.i(Constant.TAG, "Display name: " + displayName + "\n" +
                        "GoogleID: " + gg_id + "\n" +
                        "Gender: " + gender + "\n");

                // Show users' email address (which requires GET_ACCOUNTS permission)
                if (checkAccountsPermission()) {
                    email = Plus.AccountApi.getAccountName(mGoogleApiClient);
                    Log.i(Constant.TAG, "Email: " + email);
                }
                ResultLoginGoogle resultLoginGoogle = new ResultLoginGoogle(displayName, gg_id, gender, email);
                sendInfoToRegister(resultLoginGoogle, null);
            } else {
                // If getCurrentPerson returns null there is generally some error with the
                // configuration of the application (invalid Client ID, Plus API not enabled, etc).
                Log.w(Constant.TAG, getString(R.string.error_null_person));
                Log.e(Constant.TAG, getString(R.string.signed_in_err));
            }
        }
    }

    /**
     * Check if we have the GET_ACCOUNTS permission and request it if we do not.
     *
     * @return true if we have the permission, false if we do not.
     */
    private boolean checkAccountsPermission() {

        final String perm = Manifest.permission.GET_ACCOUNTS;
        int permissionCheck = ContextCompat.checkSelfPermission(this, perm);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
            // Need to show permission rationale, display a snackbar and then request
            // the permission again when the snackbar is dismissed.
            Snackbar.make(findViewById(R.id.login_layout), R.string.contacts_permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{perm}, RC_PERM_GET_ACCOUNTS);
                        }
                    }).show();
            return false;
        } else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{perm},
                    RC_PERM_GET_ACCOUNTS);
            return false;
        }

    }


    public void moveToMainScreen() {
        try {

            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

            //Cập nhật trạng thái login
            LoginActivity.status = 1;
            user = User.load(User.class, USER_ROOT);
            user.status = (LoginActivity.status);
            user.save();

            //Hủy Activity
            finish();
        } catch (NullPointerException ex) {
            ex.getMessage();
        }

    }

    private void loginWithFacebook(CallbackManager mCallBackManager, final User user, final Profile mProfile) {

        LoginManager loginManager = LoginManager.getInstance();
        loginManager.logInWithReadPermissions(this, PERMISSIONS);
        loginManager.registerCallback(mCallBackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        try {

                            if (loginResult.getAccessToken().getUserId().equals(user.getFacebookID())) {
                                moveToMainScreen();

                            } else throw new NullPointerException();
                        } catch (NullPointerException e) {
                            final String accessToken = loginResult.getAccessToken().getToken();
                            try {
                                uriImageAvatar = mProfile.getProfilePictureUri(500, 500).toString();
                            } catch (NullPointerException ex) {
                                ex.getMessage();
                            }

                            GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                    new GraphRequest.GraphJSONObjectCallback() {
                                        @Override
                                        public void onCompleted(JSONObject object, GraphResponse response) {

                                            if (response.getError() != null) {
                                                Log.i(Constant.TAG, response.getError().toString());
                                            } else {
                                                //TODO Tạo đối tượng User để lưu lại tất cả các thông tin.
                                                Log.i(Constant.TAG, "Result: " + String.valueOf(object));
                                                try {

                                                    ResultLoginFacebook result = (new Gson()).fromJson(object.toString(), ResultLoginFacebook.class);
                                                    result.setAccessToken(accessToken);
                                                    result.setUriImageAvatar(uriImageAvatar);
                                                    sendInfoToRegister(null, result);

                                                } catch (NullPointerException e) {
                                                    Log.i(Constant.TAG, e.toString());
                                                }
                                            }
                                        }
                                    });
                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "id,name,gender,birthday,email,locale");
                            graphRequest.setParameters(parameters);
                            graphRequest.executeAsync();
                        }
                    }

                    @Override
                    public void onCancel() {
                        Log.i(Constant.TAG, "Cancel");
                        Toast.makeText(LoginActivity.this, R.string.cancel_login_facebook, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.i(Constant.TAG, "Error");
                        Toast.makeText(LoginActivity.this, R.string.not_login_facebook, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void sendInfoToRegister(@Nullable ResultLoginGoogle resultLoginGoogle, @Nullable ResultLoginFacebook resultLoginFacebook) {

        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        if (resultLoginFacebook != null) {
            //Register with Facebook
            //Xử lý khi truy xuất đến biến có giá trị null.
            LoginAsyncTask loginFacebook = new LoginAsyncTask();
            loginFacebook.execute(LOGIN_API, Constant.AUTH_FACEBOOK, resultLoginFacebook.getId());
            Log.i(Constant.TAG, resultLoginFacebook.getId());
            try {
                String jsonResult = loginFacebook.get();
                ResultLogin resultFacebook = (new Gson()).fromJson(jsonResult, ResultLogin.class);

                if (resultFacebook.getSuccess() == 0) {
                    //TODO Sync thông tin vào DB
                    moveToMainScreen();

                } else {

                    intent = ValidateInputValue.onPassValueToIntent(intent, resultLoginFacebook);
                    intent.putExtra("loginType", Constant.AUTH_FACEBOOK);
                    startActivity(intent);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        }
        if (resultLoginGoogle != null) {

            LoginAsyncTask loginGoogle = new LoginAsyncTask();
            loginGoogle.execute(LOGIN_API, Constant.AUTH_GOOGLE, resultLoginGoogle.getGg_id());
            Log.i(Constant.TAG, "resultLoginGoogle != null " + resultLoginGoogle.getGg_id());
            try {
                String jsonResult = loginGoogle.get();
                if (jsonResult != null) {
                    Log.i(Constant.TAG, "Json: " + jsonResult);
                    ResultLogin resultGoogle = (new Gson()).fromJson(jsonResult, ResultLogin.class);

                    if (resultGoogle.getSuccess() == 0) {
                        moveToMainScreen();
                    } else {

                        intent.putExtra("id", resultLoginGoogle.getGg_id());
                        intent.putExtra("gender", resultLoginGoogle.getGender());
                        intent.putExtra("email", resultLoginGoogle.getEmail());
                        intent.putExtra("name", resultLoginGoogle.getDisplayName());
                        intent.putExtra("loginType", Constant.AUTH_GOOGLE);
                        startActivity(intent);
                    }
                }

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        }
        if (resultLoginFacebook == null && resultLoginGoogle == null) {
            Toast.makeText(LoginActivity.this, R.string.please_try_again, Toast.LENGTH_SHORT).show();
        }
    }

    private void onPersonalTracker() {
        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.i(Constant.TAG, currentAccessToken.getToken());
            }
        };

        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                mProfile = currentProfile;
            }
        };

        mAccessTokenTracker.startTracking();
        mProfileTracker.startTracking();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mGoogleApiClient.disconnect();
        mAccessTokenTracker.stopTracking();
        mProfileTracker.stopTracking();
        if (AccessToken.getCurrentAccessToken() != null && Profile.getCurrentProfile() != null) {
            LoginManager.getInstance().logOut();
        }
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient);
            mGoogleApiClient.disconnect();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // If the error resolution was not successful we should not resolve further.
            if (resultCode != RESULT_OK) {
                mShouldResolve = false;
            }

            mIsResolving = false;
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }

        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        // onConnected indicates that an account was selected on the device, that the selected
        // account has granted any requested permissions to our app and that we were able to
        // establish a service connection to Google Play services.
        Log.d(Constant.TAG, "onConnected:" + bundle);
        mShouldResolve = false;

        // Show the signed-in UI


        showSignedInUI();

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
        updateUI(false);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Could not connect to Google Play Services.  The user needs to select an account,
        // grant permissions or resolve an error in order to sign in. Refer to the javadoc for
        // ConnectionResult to see possible error codes.
        Log.d(Constant.TAG, "onConnectionFailed:" + connectionResult);

        if (!mIsResolving && mShouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                    mIsResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    Log.e(Constant.TAG, "Could not resolve ConnectionResult. ", e);
                    mIsResolving = false;
                    mGoogleApiClient.connect();
                }
            } else {
                // Could not resolve the connection result, show the user an
                // error dialog.
                showErrorDialog(connectionResult);
            }
        } else {
            //Show the sign-out UI
            showSignedOutUI();
        }
    }

    private void showErrorDialog(ConnectionResult connectionResult) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode == ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, RC_SIGN_IN, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mShouldResolve = false;
                        showSignedOutUI();
                    }
                }).show();
            }
        } else {
            Log.w(Constant.TAG, "Google Play Services Error:" + connectionResult);
            String errorString = apiAvailability.getErrorString(resultCode);
            Toast.makeText(this, errorString, Toast.LENGTH_SHORT).show();

            mShouldResolve = false;
            showSignedOutUI();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        View v = getCurrentFocus();
        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText && !v.getClass().getName().startsWith("android.webkit.")) {
            if (ev.getRawX() < v.getLeft() || ev.getRawX() > v.getRight() || ev.getRawY() < v.getTop() || ev.getRawY() > v.getBottom()) {
                ValidationHelper.hideKeyboard(this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private void showSignedOutUI() {
        updateUI(false);
    }

    private void showSignedInUI() {
        updateUI(true);
    }

    /**
     * @param v       The view the key has been dispatched to.
     * @param keyCode The code for the physical key that was pressed
     * @param event   The KeyEvent object containing full information about
     *                the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        switch (v.getId()) {
            case R.id.edtPassword:
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    ValidationHelper.hideKeyboard(this);
                    onClick(btnLogin);
                    return true;
                }
                break;

        }
        return false;
    }
}
