package com.salagroup.salaman.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.salagroup.salaman.helper.Constant;
import com.salagroup.salaman.helper.ValidationHelper;
import com.salagroup.salaman.pojo.Customer;
import com.salagroup.salaman.pojo.CustomerGroup;
import com.salagroup.salaman.pojo.Region;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class CustomerDetailsFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private LinearLayout customerDetailsLayout;
    private TextView tvTitleCustomerInfo;
    private EditText edtCustomerName, edtCustomerPhone, edtAddress;
    private CustomSpinner spnProvincials;
    private CustomSpinner spnDistricts;
    private Spinner spnCustomerGroup;
    private DatePicker dpkBirthday;
    private RadioGroup rdgGenderType;
    private Button btnCusDetailCancel, btnCusDetailDelete, btnCusDetailUpdate;

    private Context mContext;
    private MyRegionAdapter provincialsAdapter;
    private MyRegionAdapter districtsAdapter;
    private CustomerGroupSpinnerAdapter cgSpinnerAdapter;
    private long _id;

    private static final int MINIMUM_BIRTH_YEAR = Calendar.getInstance().get(Calendar.YEAR) - 16;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_customer_detail, container, false);

        customerDetailsLayout = (LinearLayout) view.findViewById(R.id.customerDetailsLayout);

        edtCustomerName = (EditText) view.findViewById(R.id.edtCustomerName);
        edtCustomerPhone = (EditText) view.findViewById(R.id.edtCustomerPhone);
        edtAddress = (EditText) view.findViewById(R.id.edtAddress);

        spnProvincials = (CustomSpinner) view.findViewById(R.id.spnProvincials);
        spnDistricts = (CustomSpinner) view.findViewById(R.id.spnDistricts);
        spnCustomerGroup = (Spinner) view.findViewById(R.id.spnCustomerGroup);

        dpkBirthday = (DatePicker) view.findViewById(R.id.dpkBirthday);
        dpkBirthday.updateDate(MINIMUM_BIRTH_YEAR, Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        rdgGenderType = (RadioGroup) view.findViewById(R.id.rdgGenderType);
        rdgGenderType.check(R.id.rdbMale);

        btnCusDetailCancel = (Button) view.findViewById(R.id.btnCusDetailCancel);
        btnCusDetailDelete = (Button) view.findViewById(R.id.btnCusDetailDelete);
        btnCusDetailUpdate = (Button) view.findViewById(R.id.btnCusDetailUpdate);

        ValidationHelper.addValidSpacesTextChanged(edtCustomerName);
        ValidationHelper.addValidSpacesTextChanged(edtCustomerPhone);
        ValidationHelper.addValidSpacesTextChanged(edtAddress);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = this.getActivity();

        _id = getArguments().getLong("id");

        tvTitleCustomerInfo = (TextView) getActivity().findViewById(R.id.tvTitleCustomerInfo);
        setFontforTitle(tvTitleCustomerInfo);

        btnCusDetailCancel.setOnClickListener(this);
        btnCusDetailDelete.setOnClickListener(this);
        btnCusDetailUpdate.setOnClickListener(this);

        initTinhThanh();
//        initQuanHuyen(0);
        spnProvincials.setOnItemSelectedListener(this);
        spnDistricts.setOnItemSelectedListener(this);

        List<CustomerGroup> customerGroups = CustomerGroup.getAllActive();
        CustomerGroup cg = new CustomerGroup();
        cg.setCustomerGroupName("- không nhóm -");
        customerGroups.add(0, cg);
        cgSpinnerAdapter = new CustomerGroupSpinnerAdapter(mContext, customerGroups);
        spnCustomerGroup.setAdapter(cgSpinnerAdapter);

        if (_id == -1) {

            btnCusDetailDelete.setVisibility(View.GONE);
            btnCusDetailUpdate.setText("Lưu");
        } else {

            Customer c = Customer.getCustomerById(_id);

            edtCustomerName.setText(c.getCustomerName());
            edtCustomerName.setSelection(c.getCustomerName().length());

            edtCustomerPhone.setText(c.getPhone());

            for (int i = 0; i < provincialsAdapter.regions.size(); i++) {

                if (provincialsAdapter.regions.get(i).getId() == c.getRegionL4()) {

                    spnProvincials.setSelection(i);
                    initQuanHuyen(provincialsAdapter.regions.get(i).getId());
                    break;
                }
            }
//            for (int i = 0; i < districtsAdapter.regions.size(); i++) {
//
//                if (districtsAdapter.regions.get(i).getId() == c.getRegionL5()) {
//
//                    spnDistricts.setSelection(i);
//                    break;
//                }
//            }
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

            for (int i = 1; i < cgSpinnerAdapter.getCount(); i++) {

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

            case R.id.spnProvincials:

                try {
                    long parentId = provincialsAdapter.getItem(position).getId();
                    initQuanHuyen(parentId);
                } catch (Exception ex) {
                    initQuanHuyen(0);
                }

                break;
            case R.id.spnDistricts:

                Region r = (Region) spnDistricts.getSelectedItem();
                if (r.getRegionName().length() == 0) {
                    spnDistricts.setSelection(1);
                }

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

        spnProvincials.setAdapter(provincialsAdapter);
        spnProvincials.setSelection(provincialsAdapter.getCount());
    }

    private void initQuanHuyen(long parentId) {


        if (parentId != 0) {
            List<Region> districts = Region.getRegionByParentId(parentId);
            districtsAdapter = new MyRegionAdapter(mContext, districts);
            Region r = new Region();
            r.setRegionName("Quận / Huyện");
            districtsAdapter.add(r);
            spnDistricts.setAdapter(districtsAdapter);

            if (_id != -1) {

                Customer c = Customer.getCustomerById(_id);
                for (int i = 0; i < districtsAdapter.regions.size(); i++) {

                    if (districtsAdapter.regions.get(i).getId() == c.getRegionL5()) {

                        spnDistricts.setSelection(i);
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
            spnDistricts.setAdapter(districtsAdapter);
            spnDistricts.setSelection(districtsAdapter.getCount());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCusDetailCancel:

                getActivity().onBackPressed();

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

                                CustomerListFragment.FRAGMENT_RESULT = -1;
                                getActivity().onBackPressed();
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

                if (checkInput()) {

                    c.setCustomerName(String.valueOf(edtCustomerName.getText()));

                    c.setPhone(edtCustomerPhone.getText().toString());

                    long regionL4Id = provincialsAdapter.getItem(spnProvincials.getSelectedItemPosition()).getId();
                    c.setRegionL4(regionL4Id);
                    long regionL5Id = districtsAdapter.getItem(spnDistricts.getSelectedItemPosition()).getId();
                    c.setRegionL5(regionL5Id);
                    String address = edtAddress.getText().toString().trim();
                    c.setAddress(address);


//                    String day = String.valueOf(dpkBirthday.getDayOfMonth());
//                    day = day.length() <= 1 ? "0" + day : day;
//                    String month = String.valueOf(dpkBirthday.getMonth() + 1);
//                    month = month.length() <= 1 ? "0" + month : month;
//                    String year = String.valueOf(dpkBirthday.getYear());
//                    String birthday = day + "-" + month + "-" + year;

                    Calendar birthdayCalendar = Calendar.getInstance();
                    birthdayCalendar.set(dpkBirthday.getYear(), dpkBirthday.getMonth(), dpkBirthday.getDayOfMonth());
                    Date birthDay = birthdayCalendar.getTime();
                    SimpleDateFormat birthdayFormat = new SimpleDateFormat(Constant.DATE_FORMAT_VIETNAM);
                    c.setBirthday(birthdayFormat.format(birthDay));

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
                    long groupId;
                    try {
                        groupId = cgSpinnerAdapter.getItem(groupPos).getId();
                    } catch (Exception ex) {
                        groupId = 0;
                    }
                    c.setCustomerGroupID(groupId);

                    Calendar calendar = Calendar.getInstance();
                    Date createdDate = calendar.getTime();
                    SimpleDateFormat createdFormat = new SimpleDateFormat(Constant.DATETIME_FORMAT_TIMEZONE);
                    String createdDatetime = createdFormat.format(createdDate);
                    c.setCreatedDateTime(createdDatetime);

                    c.setStatus(true);

                    c.save();
                    CustomerListFragment.FRAGMENT_RESULT = 1;
                    getActivity().onBackPressed();
                }

                break;
        }
    }

    private boolean checkInput() {

        String customerName = edtCustomerName.getText().toString().trim();
        StringTokenizer nameTokenizer = new StringTokenizer(customerName, " ");
        String customerPhone = ValidationHelper.getValidPhoneNumber(edtCustomerPhone.getText().toString());
        String customerAddress = edtAddress.getText().toString().trim();

        if (nameTokenizer.countTokens() < 2) {
            edtCustomerName.setError("Vui lòng nhập tên khách hàng gồm họ và tên (phân cách bởi khoảng trắng)");
            edtCustomerName.requestFocus();
            return false;
        } else {
            String lastName = nameTokenizer.nextToken();
            String firstName = nameTokenizer.nextToken();
            if (lastName.length() < 2 || firstName.length() < 2) {
                edtCustomerName.setError("Vui lòng nhập họ hoặc tên từ 2 ký tự");
                edtCustomerName.requestFocus();
                return false;
            }
        }
        if (!ValidationHelper.checkViNameString(customerName)) {
            edtCustomerName.setError("Vui lòng nhập tên khách hàng không có chữ số và ký tự đặc biệt");
            edtCustomerName.requestFocus();
            return false;
        }
        if (customerPhone.length() < 9) {

            edtCustomerPhone.setError("Vui lòng nhập số ĐT ít nhất 9 chữ số");
            edtCustomerPhone.requestFocus();
            return false;
        }
        try {
            long id = provincialsAdapter.regions.get(spnProvincials.getSelectedItemPosition()).getId();
        } catch (Exception ex) {
            Snackbar.make(customerDetailsLayout, "Vui lòng chọn Tỉnh / Thành", Snackbar.LENGTH_LONG).show();
            spnProvincials.requestFocus();
            return false;
        }
        if (customerAddress.isEmpty()) {

            edtAddress.setError("Vui lòng nhập địa chỉ (tên đường, thôn, ấp, phường, xã...)");
            edtAddress.requestFocus();
            return false;
        }
        if (dpkBirthday.getYear() > MINIMUM_BIRTH_YEAR) {

            Snackbar.make(customerDetailsLayout, "Vui lòng chọn năm sinh trước " + MINIMUM_BIRTH_YEAR + 1, Snackbar.LENGTH_LONG).show();
            dpkBirthday.requestFocus();
            return false;
        }

        return true;
    }

    public void setFontforTitle(TextView tvTitleCustomer) {
        Typeface robotoFont = Typeface.createFromAsset(this.getActivity().getAssets(), "fonts/Roboto-Light.ttf");
        tvTitleCustomer.setTypeface(robotoFont);
    }
}
