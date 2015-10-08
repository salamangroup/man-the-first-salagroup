package com.salagroup.salaman.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.salagroup.salaman.R;
import com.salagroup.salaman.adapter.CustomerGroupAdapter;
import com.salagroup.salaman.helper.Constant;
import com.salagroup.salaman.helper.ValidationHelper;
import com.salagroup.salaman.pojo.Customer;
import com.salagroup.salaman.pojo.CustomerGroup;

import java.util.ArrayList;
import java.util.List;

public class CustomerGroupListFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {


    private LinearLayout customerGroupLayout;
    private EditText edtCustomerGroup;

    private Context mContext;
    private CustomerGroupAdapter mAdapter;

    private CustomerGroup cg;
    private int mPos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_group_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();

        TextView tvTitleCustomer = (TextView) getActivity().findViewById(R.id.tvTitleCustomerGroup);
        setFontforTitle(tvTitleCustomer);

        customerGroupLayout = (LinearLayout) getActivity().findViewById(R.id.customerGroupLayout);
        ListView lvCustomerGroup = (ListView) getActivity().findViewById(R.id.lvAddCustomerGroup);
        FloatingActionButton fabAddCustomerGroup = (FloatingActionButton) getActivity().findViewById(R.id.fabAddCustomerGroup);

        mAdapter = new CustomerGroupAdapter(mContext, CustomerGroup.getAllActive());
        mNotifyDataSetChanged();
        lvCustomerGroup.setAdapter(mAdapter);

        fabAddCustomerGroup.setOnClickListener(this);

        lvCustomerGroup.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
                //TODO Cho phép xóa nhiều khách hàng ( hiện ra context menu )
            }
        });

        lvCustomerGroup.setOnItemClickListener(this);
    }

    private void mNotifyDataSetChanged() {

        List<CustomerGroup> customerGroups = CustomerGroup.getAllActive();
        CustomerGroup cg = new CustomerGroup();
        cg.setCustomerGroupName("- Không nhóm -");
        customerGroups.add(0, cg);
        mAdapter.setModel(customerGroups);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAddCustomerGroup:

                showDialogInsertUpdate(Constant.Statement.IS_INSERTING);

            case R.id.fabAddCustomer:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        if (position != 0) {

            PopupMenu popup = new PopupMenu(mContext, view);
            popup.getMenuInflater().inflate(R.menu.popup_menu_customergroup, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    mPos = position;

                    switch (item.getItemId()) {

                        case R.id.action_popup_update:

                            showDialogInsertUpdate(Constant.Statement.IS_UPDATETING);

                            return true;

                        case R.id.action_popup_delete:

                            showDialogDelete();

                            return true;
                    }

                    return false;
                }
            });
            popup.show();//showing popup menu
        }
    }

    private void showDialogInsertUpdate(final int mState) {

        cg = new CustomerGroup();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View mDialog = inflater.inflate(R.layout.dialog_add_group_customer,
                (ViewGroup) getActivity().findViewById(R.id.dialog_add_customer_group));

        edtCustomerGroup = (EditText) mDialog.findViewById(R.id.edtCustomerGroup);
        edtCustomerGroup.setSingleLine();
        ValidationHelper.addValidNameSpacesTextChanged(edtCustomerGroup);
        //TODO xử lý tên nhóm nhập vào

        if (mState == Constant.Statement.IS_UPDATETING) {

            cg = CustomerGroup.getCustomerGroupById(mAdapter.customerGroups.get(mPos).getId());
            edtCustomerGroup.setText(cg.getCustomerGroupName());
            edtCustomerGroup.setSelection(cg.getCustomerGroupName().length());
        }

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

                        if (checkInput()) {

                            String name = edtCustomerGroup.getText().toString();
                            cg.setCustomerGroupName(name);
                            //TODO cg.setShopID
                            cg.setStatus(true);
                            cg.save();

                            mNotifyDataSetChanged();

                            if(mState == Constant.Statement.IS_INSERTING){

                                Snackbar.make(customerGroupLayout, "Thêm nhóm thành công",
                                        Snackbar.LENGTH_LONG).show();
                            }else {

                                Snackbar.make(customerGroupLayout, "Cập nhật thành công",
                                        Snackbar.LENGTH_LONG).show();
                            }

                            mAlertDialog.dismiss();
                        }
                    }
                });
            }
        });
        mAlertDialog.show();
    }

    private boolean checkInput() {

        String name = edtCustomerGroup.getText().toString().trim();
        if (name.length() == 0) {

            edtCustomerGroup.setError("Vui lòng nhập tên nhóm!");
            edtCustomerGroup.setText("");
            edtCustomerGroup.setSelection(0);
            return false;
        } else {

            edtCustomerGroup.setText(name);
        }

        return true;
    }

    private void showDialogDelete() {

        cg = CustomerGroup.getCustomerGroupById(mAdapter.customerGroups.get(mPos).getId());

        String mMessage = null;
        int customerCount = CustomerGroup.getCustomerCountById(cg.getId());
        if (customerCount != 0) {
            mMessage = "Nhóm khách hàng \"" + cg.getCustomerGroupName() + "\" có " + customerCount + " khách hàng, bạn có chắc muốn xóa?";
        }

        new AlertDialog.Builder(mContext)
                .setTitle("Xóa nhóm khách hàng \"" + cg.getCustomerGroupName() + "\"")
                .setMessage(mMessage)
                .setCancelable(true)
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        cg.setStatus(false);
                        cg.save();

                        final List<Long> undoIds = new ArrayList<>();

                        List<Customer> customers = Customer.getCustomersByGroupId(cg.getId());
                        for (Customer c : customers) {

                            c.setCustomerGroupID(0);
                            c.save();
                            undoIds.add(c.getId());
                        }

                        mNotifyDataSetChanged();

//                                                final Thread mThread = new Thread(new Runnable() {
//                                                    @Override
//                                                    public void run() {
//
//                                                        try {
//                                                            Thread.sleep(2750);
//
//                                                            List<Customer> customers = Customer.getCustomersByGroupId(cg.getId());
//                                                            for (Customer c : customers) {
//
//                                                                c.setCustomerGroupID(0);
//                                                                c.save();
//                                                            }
//                                                            Log.d("myDebug", "run");
//
//                                                        } catch (InterruptedException e) {
//                                                            e.printStackTrace();
//                                                        }
//                                                    }
//                                                });
//                                                mThread.start();

                        Snackbar.make(customerGroupLayout, "Đã xóa nhóm \"" + cg.getCustomerGroupName() + "\"",
                                Snackbar.LENGTH_LONG)
                                .setAction("Hoàn tác", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

//                                                                mThread.interrupt();

                                        cg.setStatus(true);
                                        cg.save();

                                        for (long id : undoIds) {

                                            Customer c = Customer.getCustomerById(id);
                                            c.setCustomerGroupID(cg.getId());
                                            c.save();
                                        }

                                        mNotifyDataSetChanged();
                                    }
                                })
                                .show();
                    }
                })
                .setNegativeButton("Không", null)
                .show();
    }

    public void setFontforTitle(TextView tvTitleCustomer) {
        Typeface robotoFont = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf");
        tvTitleCustomer.setTypeface(robotoFont);
    }
}


