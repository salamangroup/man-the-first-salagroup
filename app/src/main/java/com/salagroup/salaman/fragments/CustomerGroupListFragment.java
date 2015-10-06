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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.salagroup.salaman.R;
import com.salagroup.salaman.adapter.CustomerGroupAdapter;
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
        mContext = this.getActivity();


        TextView tvTitleCustomer = (TextView) getActivity().findViewById(R.id.tvTitleCustomerGroup);
        setFontforTitle(tvTitleCustomer);

        customerGroupLayout = (LinearLayout) getActivity().findViewById(R.id.customerGroupLayout);
        ListView lvCustomerGroup = (ListView) getActivity().findViewById(R.id.lvAddCustomerGroup);
        FloatingActionButton fabAddCustomerGroup = (FloatingActionButton) getActivity().findViewById(R.id.fabAddCustomerGroup);

        mAdapter = new CustomerGroupAdapter(mContext, CustomerGroup.getAllActive());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAddCustomerGroup:

                showDialogInsertUpdate(false);

            case R.id.fabAddCustomer:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        PopupMenu popup = new PopupMenu(mContext, view);
        popup.getMenuInflater().inflate(R.menu.popup_menu_customergroup, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                mPos = position;

                switch (item.getItemId()) {

                    case R.id.action_popup_update:

                        showDialogInsertUpdate(true);

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

    private void showDialogInsertUpdate(boolean isUpdating) {

        cg = new CustomerGroup();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View mDialog = inflater.inflate(R.layout.dialog_add_group_customer,
                (ViewGroup) getActivity().findViewById(R.id.dialog_add_customer_group));

        edtCustomerGroup = (EditText) mDialog.findViewById(R.id.edtCustomerGroup);
        edtCustomerGroup.setSingleLine();
        //TODO xử lý tên nhóm nhập vào

        if (isUpdating) {

            cg = CustomerGroup.getCustomerGroupById(mAdapter.customerGroups.get(mPos).getId());
            edtCustomerGroup.setText(cg.getCustomerGroupName());
            edtCustomerGroup.setSelection(cg.getCustomerGroupName().length());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(mDialog)
                .setNegativeButton(getString(R.string.btn_customer_group_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton(getString(R.string.btn_customer_group_save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        cg.setCustomerGroupName(edtCustomerGroup.getText().toString());
                        cg.setStatus(true);
                        cg.save();

                        mAdapter.setModel(CustomerGroup.getAllActive());
                        mAdapter.notifyDataSetChanged();

                        Snackbar.make(customerGroupLayout, "Cập nhật thành công",
                                Snackbar.LENGTH_LONG).show();
                    }
                }).show();
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

                        mAdapter.setModel(CustomerGroup.getAllActive());
                        mAdapter.notifyDataSetChanged();

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

                                        mAdapter.setModel(CustomerGroup.getAllActive());
                                        mAdapter.notifyDataSetChanged();
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


