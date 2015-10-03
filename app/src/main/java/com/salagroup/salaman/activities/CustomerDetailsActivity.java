package com.salagroup.salaman.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.salagroup.salaman.R;
import com.salagroup.salaman.adapter.CustomerGroupSpinnerAdapter;
import com.salagroup.salaman.adapter.MyRegionAdapter;
import com.salagroup.salaman.customview.CustomSpinner;
import com.salagroup.salaman.pojo.Customer;
import com.salagroup.salaman.pojo.CustomerGroup;
import com.salagroup.salaman.pojo.Region;

import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by TrytoThuan on 14/09/2015.
 */
public class CustomerDetailsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private LinearLayout customerInfoLayout;
    private TextView tvTitleCustomerInfo;
    private EditText edtCustomerName, edtCustomerPhone, edtAddress;
    private CustomSpinner spnProvince_City;
    private CustomSpinner spnDistrict;
    private Spinner spnCustomerGroup;
    private DatePicker dpkBirthday;
    private RadioGroup rdgGenderType;
    private Button btnCusDetailCancel, btnCusDetailDelete, btnCusDetailUpdate;

    private Context mContext;
    private MyRegionAdapter provincialsAdapter;
    private MyRegionAdapter districtsAdapter;
    private CustomerGroupSpinnerAdapter cgSpinnerAdapter;
    private long _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_customer_detail);
        mContext = this;

        _id = getIntent().getLongExtra("id", -1);
        initFindView(); // FindViewById()

        tvTitleCustomerInfo = (TextView) findViewById(R.id.tvTitleCustomerInfo);
        setFontforTitle(tvTitleCustomerInfo);

        btnCusDetailCancel.setOnClickListener(this);
        btnCusDetailDelete.setOnClickListener(this);
        btnCusDetailUpdate.setOnClickListener(this);

        initTinhThanh();
//        initQuanHuyen(0);
        spnProvince_City.setOnItemSelectedListener(this);
        spnDistrict.setOnItemSelectedListener(this);

        cgSpinnerAdapter = new CustomerGroupSpinnerAdapter(mContext, new CustomerGroup().getAllActive());
        spnCustomerGroup.setAdapter(cgSpinnerAdapter);

        if (_id == -1) {

            btnCusDetailDelete.setVisibility(View.GONE);
            btnCusDetailUpdate.setText("Lưu");
        } else {

            Customer c = Customer.getCustomerById(_id);

            edtCustomerName.setText(c.getCustomerName());
            edtCustomerName.setSelection(c.getCustomerName().length());

            edtCustomerPhone.setText(c.getPhone());

            for (int i = 0; i < provincialsAdapter.getCount(); i++) {

                if (provincialsAdapter.getItem(i).getId() == c.getRegionL4()) {

                    spnProvince_City.setSelection(i);
                    initQuanHuyen(provincialsAdapter.getItem(i).getId());
                    break;
                }
            }
            for (int i = 0; i < districtsAdapter.getCount(); i++) {

                if (districtsAdapter.getItem(i).getId() == c.getRegionL5()) {

                    spnDistrict.setSelection(i);
                    break;
                }
            }
            edtAddress.setText(c.getAddress());

            StringTokenizer tokenizer = new StringTokenizer(c.getBirthday(), "-");
            int day = Integer.parseInt(tokenizer.nextToken());
            int month = Integer.parseInt(tokenizer.nextToken()) - 1;
            int year = Integer.parseInt(tokenizer.nextToken());
            dpkBirthday.updateDate(year, month, day);

            switch (c.getGender()) {
                case 1:
                    rdgGenderType.check(R.id.rdbMale);
                    break;
                case 0:
                    rdgGenderType.check(R.id.rdbFemale);
                    break;
                case -1:
                    rdgGenderType.check(R.id.rdbOther);
                    break;
            }

            for (int i = 0; i < cgSpinnerAdapter.getCount(); i++) {

                if (cgSpinnerAdapter.getItem(i).getId() == c.getCustomerGroupID()) {

                    spnCustomerGroup.setSelection(i);
                    break;
                }
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {

            case R.id.spnProvince_City:

                try {
                    long parentId = provincialsAdapter.getItem(position).getId();
                    initQuanHuyen(parentId);
                } catch (Exception ex) {
                    initQuanHuyen(0);
                }

                break;
            case R.id.spnDistrict:

//                try {
//                    long parentId = provincialsAdapter.getItem(spnProvince_City.getSelectedItemPosition()).getId();
//                    if (parentId == 0) {
//                        initQuanHuyen(0);
//                    }
//                }catch (NullPointerException ex){
//                    initQuanHuyen(0);
//                }

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void initTinhThanh() {

        List<Region> provincials = Region.getRegionByLevel(4);
        provincialsAdapter = new MyRegionAdapter(mContext, provincials);
        Region r = new Region();
        r.setRegionName("Tỉnh / Thành");
        provincialsAdapter.add(r);

        spnProvince_City.setAdapter(provincialsAdapter);
        spnProvince_City.setSelection(provincialsAdapter.getCount());
    }

    private void initQuanHuyen(long tinhthanhID) {


        if (tinhthanhID != 0) {
            List<Region> districts = Region.getRegionByParentId(tinhthanhID);
            districtsAdapter = new MyRegionAdapter(mContext, districts);
            Region r = new Region();
            r.setRegionName("Quận / Huyện");
            districtsAdapter.add(r);
            spnDistrict.setAdapter(districtsAdapter);

            if (_id != -1) {

                Customer c = Customer.getCustomerById(_id);
                for (int i = 0; i < districtsAdapter.getCount(); i++) {

                    if (districtsAdapter.getItem(i).getId() == c.getRegionL5()) {

                        spnDistrict.setSelection(i);
                        break;
                    }
                }
            }
        } else {
            List<Region> districts = Region.getNoneRegion();
            districtsAdapter = new MyRegionAdapter(mContext, districts);
            Region r1 = new Region();
            r1.setRegionName("");
            districtsAdapter.add(r1);
            Region r2 = new Region();
            r2.setRegionName("Quận / Huyện");
            districtsAdapter.add(r2);
            spnDistrict.setAdapter(districtsAdapter);
            spnDistrict.setSelection(districtsAdapter.getCount());
        }
    }

    @Override
    public void onClick(View v) {
        //TODO Hủy, Xóa, Sửa Customers
        switch (v.getId()) {
            case R.id.btnCusDetailCancel:

                finish();

                break;
            case R.id.btnCusDetailDelete:

                new AlertDialog.Builder(mContext)
                        .setTitle("Xóa khách hàng?")
                        .setCancelable(true)
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Customer c = Customer.getCustomerById(_id);
                                c.setStatus(false);
                                c.save();

//                                setResult(-2);
                                finish();
                            }
                        })
                        .setNegativeButton("Không", null)
                        .show();

                break;
            case R.id.btnCusDetailUpdate:

                Customer c = new Customer();

                if (_id != -1) {
                    c = Customer.getCustomerById(_id);
                }

                c.setCustomerName(edtCustomerName.getText().toString());

                c.setPhone(edtCustomerPhone.getText().toString());

                long regionL4Id = provincialsAdapter.getItem(spnProvince_City.getSelectedItemPosition()).getId();
                c.setRegionL4(regionL4Id);
                long regionL5Id = districtsAdapter.getItem(spnDistrict.getSelectedItemPosition()).getId();
                c.setRegionL5(regionL5Id);
                String diachi = edtAddress.getText().toString();
                c.setAddress(diachi);

                String day = String.valueOf(dpkBirthday.getDayOfMonth());
                day = day.length() <= 1 ? "0" + day : day;
                String month = String.valueOf(dpkBirthday.getMonth() + 1);
                month = month.length() <= 1 ? "0" + month : month;
                String year = String.valueOf(dpkBirthday.getYear());
                String birthday = day + "-" + month + "-" + year;
                c.setBirthday(birthday);

                switch (rdgGenderType.getCheckedRadioButtonId()) {
                    case R.id.rdbMale:
                        c.setGender(1);
                        break;
                    case R.id.rdbFemale:
                        c.setGender(0);
                        break;
                    case R.id.rdbOther:
                        c.setGender(-1);
                        break;
                }

                int groupPos = spnCustomerGroup.getSelectedItemPosition();
                long groupId = cgSpinnerAdapter.getItem(groupPos).getId();
                c.setCustomerGroupID(groupId);

                Calendar calendar = Calendar.getInstance();
                int createdYear = calendar.get(Calendar.YEAR);
                int createdMonth = calendar.get(Calendar.MONTH) + 1;
                int createdDay = calendar.get(Calendar.DAY_OF_MONTH);
                int createdHour = calendar.get(Calendar.HOUR_OF_DAY);
                int createdMinute = calendar.get(Calendar.MINUTE);
                int createdSecond = calendar.get(Calendar.SECOND);
                String createdDatetime = createdYear + "-" + createdMonth + "-" + createdDay + "-" + createdHour + ":" + createdMinute + ":" + createdSecond;
                c.setCreatedDateTime(createdDatetime);

                c.setStatus(true);

                c.save();

//                setResult(RESULT_OK);
                finish();

                break;
        }
    }

    private void initFindView() {

        edtCustomerName = (EditText) findViewById(R.id.edtCustomerName);
        edtCustomerPhone = (EditText) findViewById(R.id.edtCustomerPhone);
        edtAddress = (EditText) findViewById(R.id.edtAddress);

        spnProvince_City = (CustomSpinner) findViewById(R.id.spnProvince_City);
        spnDistrict = (CustomSpinner) findViewById(R.id.spnDistrict);
        spnCustomerGroup = (Spinner) findViewById(R.id.spnCustomerGroup);

        dpkBirthday = (DatePicker) findViewById(R.id.dpkBirthday);

        rdgGenderType = (RadioGroup) findViewById(R.id.rdgGenderType);

        btnCusDetailCancel = (Button) findViewById(R.id.btnCusDetailCancel);
        btnCusDetailDelete = (Button) findViewById(R.id.btnCusDetailDelete);
        btnCusDetailUpdate = (Button) findViewById(R.id.btnCusDetailUpdate);
    }

    public void setFontforTitle(TextView tvTitleCustomer) {
        Typeface robotoFont = Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Light.ttf");
        tvTitleCustomer.setTypeface(robotoFont);
    }
}
