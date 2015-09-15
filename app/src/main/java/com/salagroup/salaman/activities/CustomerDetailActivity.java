package com.salagroup.salaman.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.salagroup.salaman.R;

/**
 * Created by TrytoThuan on 14/09/2015.
 */
public class CustomerDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout customerInfoLayout;
    private TextView tvTitleCustomerInfo;
    private EditText edtCustomerName, edtCustomerPhone, edtAddress;
    private Spinner spnProvince_City, spnDistrict, spnTypeCustomer;
    private DatePicker dprBirthday;
    private RadioGroup rdgGenderType;
    private Button btnCusDetailCancel, btnCusDetailDelete, btnCusDetailUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_customer_detail);

        initFindView(); // FindViewById()

        tvTitleCustomerInfo = (TextView) findViewById(R.id.tvTitleCustomerInfo);
        setFontforTitle(tvTitleCustomerInfo);

        btnCusDetailCancel.setOnClickListener(this);
        btnCusDetailDelete.setOnClickListener(this);
        btnCusDetailUpdate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //TODO Hủy, Xóa, Sửa Customers
        switch (v.getId()){
            case R.id.btnCusDetailCancel:
                //Code here
            case R.id.btnCusDetailDelete:
                //Code here
            case R.id.btnCusDetailUpdate:
                //Code here
        }
    }

    private void initFindView() {

        edtCustomerName = (EditText) findViewById(R.id.edtCustomerName);
        edtCustomerPhone = (EditText) findViewById(R.id.edtCustomerPhone);
        edtAddress = (EditText) findViewById(R.id.edtAddress);

        spnProvince_City = (Spinner) findViewById(R.id.spnProvince_City);
        spnDistrict = (Spinner) findViewById(R.id.spnDistrict);
        spnTypeCustomer = (Spinner) findViewById(R.id.spnTypeCustomer);

        dprBirthday = (DatePicker) findViewById(R.id.dprBirthday);

        rdgGenderType = (RadioGroup) findViewById(R.id.rdgGenderType);

        btnCusDetailCancel = (Button) findViewById(R.id.btnCusDetailCancel);
        btnCusDetailDelete = (Button) findViewById(R.id.btnCusDetailDelete);
        btnCusDetailUpdate = (Button) findViewById(R.id.btnCusDetailUpdate);
    }

    public void setFontforTitle(TextView tvTitleCustomer){
        Typeface robotoFont = Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Light.ttf");
        tvTitleCustomer.setTypeface(robotoFont);
    }
}
