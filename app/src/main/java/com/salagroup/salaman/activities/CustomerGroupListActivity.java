package com.salagroup.salaman.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.salagroup.salaman.R;
import com.salagroup.salaman.adapter.CustomerGroupAdapter;
import com.salagroup.salaman.pojo.CustomerGroup;

/**
 * Created by TrytoThuan on 14/09/2015.
 */
public class CustomerGroupListActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout customerGroupLayout;
    private EditText edtCustomerGroup;
    private Context mContext;
    private CustomerGroupAdapter mAdapter;
    private ListView lvCustomerGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_customer_group_list);
        mContext = this;

        TextView tvTitleCustomer = (TextView) findViewById(R.id.tvTitleCustomerGroup);
        setFontforTitle(tvTitleCustomer);

        customerGroupLayout = (LinearLayout) findViewById(R.id.customerGroupLayout);
        lvCustomerGroup = (ListView) findViewById(R.id.lvAddCustomerGroup);
        FloatingActionButton fabAddCustomerGroup = (FloatingActionButton) findViewById(R.id.fabAddCustomerGroup);

        mAdapter = new CustomerGroupAdapter(mContext, new CustomerGroup().getAll());
        lvCustomerGroup.setAdapter(mAdapter);

        fabAddCustomerGroup.setOnClickListener(this);

        lvCustomerGroup.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
                //TODO Cho phép xóa nhiều khách hàng ( hiện ra context menu )
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAddCustomerGroup:

                LayoutInflater inflater = LayoutInflater.from(CustomerGroupListActivity.this);
                View mDialog = inflater.inflate(R.layout.dialog_add_group_customer,
                        (ViewGroup) findViewById(R.id.dialog_add_customer_group));

                edtCustomerGroup = (EditText) mDialog.findViewById(R.id.edtCustomerGroup);
                //TODO xử lý tên nhóm nhập vào

                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerGroupListActivity.this);
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

                                CustomerGroup cg = new CustomerGroup();
                                cg.setCustomerGroupName(edtCustomerGroup.getText().toString());
                                cg.save();

                                //TODO research how to notifyDataSetChanged()
//                                List<CustomerGroup> customerGroups = new CustomerGroup().getAll();
//                                mAdapter.setCustomerGroups(customerGroups);
//                                mAdapter.notifyDataSetChanged();
                                mAdapter = new CustomerGroupAdapter(mContext, new CustomerGroup().getAll());
                                lvCustomerGroup.setAdapter(mAdapter);

                                Snackbar.make(customerGroupLayout, getString(R.string.notification_add_customer_group_ok),
                                        Snackbar.LENGTH_LONG).show();
                            }
                        }).show();
            case R.id.fabAddCustomer:
                break;
        }
    }

    public void setFontforTitle(TextView tvTitleCustomer){
        Typeface robotoFont = Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Light.ttf");
        tvTitleCustomer.setTypeface(robotoFont);
    }
}


