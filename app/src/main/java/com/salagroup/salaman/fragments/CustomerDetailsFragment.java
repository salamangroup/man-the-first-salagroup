package com.salagroup.salaman.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.salagroup.salaman.R;
import com.salagroup.salaman.adapter.CustomerGroupSpinnerAdapter;
import com.salagroup.salaman.adapter.MyRegionAdapter;
import com.salagroup.salaman.customview.CustomSpinner;
import com.salagroup.salaman.helper.Constant;
import com.salagroup.salaman.helper.SystemInfoHelper;
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
    private ImageButton btnAddGroup;

    private EditText edtCustomerGroup;

    private Context mContext;
    private MyRegionAdapter provincialsAdapter;
    private MyRegionAdapter districtsAdapter;
    private CustomerGroupSpinnerAdapter cgSpinnerAdapter;
    private long _id;

    private static final int MAX_BIRTH_YEAR = Calendar.getInstance().get(Calendar.YEAR) - 13;
    private static final int MIN_BIRTH_YEAR = Calendar.getInstance().get(Calendar.YEAR) - 100;

    private int mState;

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
        dpkBirthday.updateDate(MAX_BIRTH_YEAR, Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        rdgGenderType = (RadioGroup) view.findViewById(R.id.rdgGenderType);
        rdgGenderType.check(R.id.rdbMale);

        btnCusDetailCancel = (Button) view.findViewById(R.id.btnCusDetailCancel);
        btnCusDetailDelete = (Button) view.findViewById(R.id.btnCusDetailDelete);
        btnCusDetailUpdate = (Button) view.findViewById(R.id.btnCusDetailUpdate);

        btnAddGroup = (ImageButton) view.findViewById(R.id.btnAddGroup);

        ValidationHelper.addValidNameSpacesTextChanged(edtCustomerName);
        ValidationHelper.addViMoneyTextChanged(edtCustomerPhone);
        ValidationHelper.addValidNameSpacesTextChanged(edtAddress);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = this.getActivity();

        _id = getArguments().getLong("id");
        if (_id == -1) {
            mState = Constant.Statement.IS_INSERTING;
        } else {
            mState = Constant.Statement.IS_UPDATETING;
        }

        tvTitleCustomerInfo = (TextView) getActivity().findViewById(R.id.tvTitleCustomerInfo);
        setFontforTitle(tvTitleCustomerInfo);

        btnCusDetailCancel.setOnClickListener(this);
        btnCusDetailDelete.setOnClickListener(this);
        btnCusDetailUpdate.setOnClickListener(this);

        btnAddGroup.setOnClickListener(this);

        initTinhThanh();
        spnProvincials.setOnItemSelectedListener(this);
        initQuanHuyen(0);
        spnDistricts.setOnItemSelectedListener(this);

        if (mState == Constant.Statement.IS_INSERTING) {

            btnCusDetailDelete.setVisibility(View.GONE);
            btnCusDetailUpdate.setText("Lưu");
        } else {

            Customer c = Customer.getCustomerById(_id);

            edtCustomerName.setText(c.getCustomerName());
            edtCustomerName.setSelection(c.getCustomerName().length());

            edtCustomerPhone.setText(ValidationHelper.getViFormatPhone(c.getPhone()));

            if (c.getRegionL4() != 0) {

                for (int i = 0; i < provincialsAdapter.regions.size(); i++) {

                    if (provincialsAdapter.regions.get(i).getId() == c.getRegionL4()) {

                        spnProvincials.setSelection(i);
                        initQuanHuyen(provincialsAdapter.regions.get(i).getId());
                        break;
                    }
                }
//                if(c.getRegionL5()!=0){
//                    for (int i = 0; i < districtsAdapter.regions.size(); i++) {
//
//                        if (districtsAdapter.regions.get(i).getId() == c.getRegionL5()) {
//
//                            spnDistricts.setSelection(i);
//                            break;
//                        }
//                    }
//                }
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
        }

        initCustomerGroup();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {

            case R.id.spnProvincials:

                try {
                    long parentId = provincialsAdapter.regions.get(position).getId();
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

    private void initCustomerGroup() {

        List<CustomerGroup> customerGroups = CustomerGroup.getAllActive();
        CustomerGroup cg = new CustomerGroup();
        cg.setCustomerGroupName("- Không nhóm -");
        customerGroups.add(0, cg);
        cgSpinnerAdapter = new CustomerGroupSpinnerAdapter(mContext, customerGroups);
        spnCustomerGroup.setAdapter(cgSpinnerAdapter);

        if (mState == Constant.Statement.IS_UPDATETING) {

            for (int i = 1; i < cgSpinnerAdapter.getCount(); i++) {

                if (cgSpinnerAdapter.getItem(i).getId() == Customer.getCustomerById(_id).getCustomerGroupID()) {

                    spnCustomerGroup.setSelection(i);
                    break;
                }
            }
        }
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
                if (c.getRegionL5() != 0 && c.getRegionL4() == parentId) {
                    for (int i = 0; i < districtsAdapter.regions.size(); i++) {

                        if (districtsAdapter.regions.get(i).getId() == c.getRegionL5()) {

                            spnDistricts.setSelection(i);
                            break;
                        }
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

                                mState = Constant.Statement.IS_DELETING;
                                CustomerListFragment.FRAGMENT_RESULT = mState;
                                getActivity().onBackPressed();
                            }
                        })
                        .setNegativeButton("Không", null)
                        .show();

                break;
            case R.id.btnCusDetailUpdate:

                Customer c = new Customer();

                if (mState == Constant.Statement.IS_UPDATETING) {
                    c = Customer.getCustomerById(_id);
                }

                if (checkInput()) {

                    c.setCustomerName(edtCustomerName.getText().toString().trim());

                    c.setPhone(ValidationHelper.getValidFormatPhone(edtCustomerPhone.getText().toString()));
                    try {
                        long regionL4Id = provincialsAdapter.getItem(spnProvincials.getSelectedItemPosition()).getId();
                        c.setRegionL4(regionL4Id);
                    } catch (NullPointerException ex) {
                        c.setRegionL4(0);
                    }
                    try {
                        long regionL5Id = districtsAdapter.getItem(spnDistricts.getSelectedItemPosition()).getId();
                        c.setRegionL5(regionL5Id);
                    } catch (NullPointerException ex) {
                        c.setRegionL5(0);
                    }

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

                    //TODO c.setNote(); c.setEmail(); c.setCreatedBy(); c.setLastUpdatedBy(); c.setUserID(); c.setShopID();
                    if (mState == Constant.Statement.IS_INSERTING) {
                        c.setCreatedDateTime(SystemInfoHelper.getCurrentDatetime(true));
                    } else {
                        c.setLastUpdatedDateTime(SystemInfoHelper.getCurrentDatetime(true));
                    }

                    c.setStatus(true);

                    c.save();
                    CustomerListFragment.FRAGMENT_RESULT = mState;
                    getActivity().onBackPressed();
                }

                break;
            case R.id.btnAddGroup:

                showDialogInsertCustomerGroup();

                break;
        }
    }

    public void showDialogInsertCustomerGroup() {

        final CustomerGroup cg = new CustomerGroup();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View mDialog = inflater.inflate(R.layout.dialog_add_group_customer,
                (ViewGroup) getActivity().findViewById(R.id.dialog_add_customer_group));

        edtCustomerGroup = (EditText) mDialog.findViewById(R.id.edtCustomerGroup);
        edtCustomerGroup.setSingleLine();
        ValidationHelper.addValidNameSpacesTextChanged(edtCustomerGroup);
        //TODO xử lý tên nhóm nhập vào

        final AlertDialog mAlertDialog = new AlertDialog.Builder(mContext)
                .setView(mDialog)
                .setNegativeButton(getString(R.string.btn_customer_group_cancel), null)
                .setPositiveButton(getString(R.string.btn_customer_group_save), null)
                .create();
        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                Button mPositiveButton = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                mPositiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String name = edtCustomerGroup.getText().toString().trim();

                        if (name.length() > 0) {

                            cg.setCustomerGroupName(name);
                            //TODO cg.setShopID cg.setCode(); cg.setCreatedBy(); cg.setLastUpdatedBy(); cg.setNote();
                            cg.setStatus(true);

                            cg.setCreatedDateTime(SystemInfoHelper.getCurrentDatetime(true));

                            Snackbar.make(customerDetailsLayout, "Thêm nhóm thành công",
                                    Snackbar.LENGTH_LONG).show();

                            cg.save();

                            initCustomerGroup();

                            mAlertDialog.dismiss();
                        } else {

                            edtCustomerGroup.setError("Vui lòng nhập tên nhóm");
                        }
                    }
                });
            }
        });
        mAlertDialog.show();
    }

    private boolean checkInput() {

        String customerName = edtCustomerName.getText().toString().trim();
        String customerPhone = ValidationHelper.getValidFormatPhone(edtCustomerPhone.getText().toString());
        String customerAddress = edtAddress.getText().toString().trim();

//        if (nameTokenizer.countTokens() < 2) {
//            edtCustomerName.setError("Vui lòng nhập tên khách hàng gồm họ và tên (phân cách bởi khoảng trắng)");
//            edtCustomerName.requestFocus();
//            return false;
//        }
        if (customerName.isEmpty()) {
            edtCustomerName.setError("Vui lòng nhập tên khách hàng!");
            edtCustomerName.requestFocus();
            return false;
        }
        if (!ValidationHelper.checkViHumanNameString(customerName)) {
            edtCustomerName.setError("Vui lòng nhập tên khách hàng không có ký tự đặc biệt");
            edtCustomerName.requestFocus();
            return false;
        }
        if (customerPhone.length() < 9) {

            edtCustomerPhone.setError("Vui lòng nhập số ĐT ít nhất 9 chữ số");
            edtCustomerPhone.requestFocus();
            return false;
        }
        if (customerAddress.length() > 0) {
            try {
                long id = provincialsAdapter.regions.get(spnProvincials.getSelectedItemPosition()).getId();
            } catch (Exception ex) {
                Snackbar.make(customerDetailsLayout, "Vui lòng chọn Tỉnh / Thành", Snackbar.LENGTH_LONG).show();
                spnProvincials.requestFocus();
                return false;
            }
        }
        if (dpkBirthday.getYear() > MAX_BIRTH_YEAR) {

            Snackbar.make(customerDetailsLayout, "Vui lòng chọn năm sinh trước " + (MAX_BIRTH_YEAR + 1), Snackbar.LENGTH_LONG).show();
            dpkBirthday.requestFocus();
            return false;
        } else if (dpkBirthday.getYear() <= MIN_BIRTH_YEAR) {

            Snackbar.make(customerDetailsLayout, "Vui lòng chọn năm sinh sau " + MIN_BIRTH_YEAR, Snackbar.LENGTH_LONG).show();
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
