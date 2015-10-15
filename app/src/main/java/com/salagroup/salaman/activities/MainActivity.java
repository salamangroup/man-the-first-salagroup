package com.salagroup.salaman.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.salagroup.salaman.R;
import com.salagroup.salaman.fragments.Fragment_Customer;
import com.salagroup.salaman.fragments.Fragment_Product;
import com.salagroup.salaman.fragments.Fragment_SettingSystem;
import com.salagroup.salaman.fragments.Fragment_Shop;
import com.salagroup.salaman.helper.EncryptionUtil;
import com.salagroup.salaman.pojo.User;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    boolean status = false;
    private User user;
    private boolean doubleBackToComeBackLogin = false;
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToComeBackLogin = false;
        }
    };
    private Handler mHandler = new Handler();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_navigation);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Cửa hàng", Fragment_Shop.class)
                .add("Khách hàng", Fragment_Customer.class)
                .add("Sản phẩm", Fragment_Product.class)
                .add("Cài đặt", Fragment_SettingSystem.class)
                .create());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);

    }

    @Override
    protected void onStop() {
        super.onStop();
        status = true;
        //Cập nhật trạng thái trước khi thoát
        try {
            User user2 = User.load(User.class, LoginActivity.USER_ROOT);
            user2.status = 0;
            user2.save();
        } catch (NullPointerException e) {
            e.getMessage();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            user = User.load(User.class, LoginActivity.USER_ROOT);
            if (user != null) {
                if (status)
                    showAlertDialog();
            }
        } catch (NullPointerException e) {
            e.getMessage();
        }

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToComeBackLogin) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            try {
                User user = User.load(User.class, LoginActivity.USER_ROOT);
                user.status = 0;
                user.save();
            } catch (NullPointerException e) {
                e.getMessage();
            }
            startActivity(intent);
            super.onBackPressed();
            return;
        }
        this.doubleBackToComeBackLogin = true;
        Toast.makeText(this, R.string.press_back_to_logout, Toast.LENGTH_SHORT).show();
        mHandler.postDelayed(mRunnable, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    private void showAlertDialog() {

        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View mDialog = inflater.inflate(R.layout.dialog_re_enter_passwd,
                (ViewGroup) findViewById(R.id.dialog_re_enter_passwd), false);

        final EditText edtReEnterPwd = (EditText) mDialog.findViewById(R.id.edtReEnterPwd);
        edtReEnterPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(mDialog)
                .setCancelable(false)
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        try {
                            User user1 = User.load(User.class, LoginActivity.USER_ROOT);
                            user1.status = 0;
                            user1.save();
                        } catch (NullPointerException e) {
                            e.getMessage();
                        }
                        startActivity(intent);
                        finish();

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String passwd = edtReEnterPwd.getText().toString().trim();
                        try {
                            if (!EncryptionUtil.SHA1(passwd).equals("")
                                    && EncryptionUtil.SHA1(passwd).equals(user.getPassword())) {
                                dialog.cancel();

                            } else {
                                showAlertDialog();
                            }
                        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }).show();
    }
}
