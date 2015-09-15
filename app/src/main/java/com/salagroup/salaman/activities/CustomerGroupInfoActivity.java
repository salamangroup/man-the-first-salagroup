package com.salagroup.salaman.activities;

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

/**
 * Created by TrytoThuan on 14/09/2015.
 */
public class CustomerGroupInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView lvCustomerGroup;
    private FloatingActionButton fabAddCustomerGroup;
    private LinearLayout customerGroupLayout;
    private EditText edtCustomerGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_customer_group_list);

        TextView tvTitleCustomer = (TextView) findViewById(R.id.tvTitleCustomerGroup);
        setFontforTitle(tvTitleCustomer);

        //TODO Adapter & ListView
        //Khai báo Adapter, kèm layout đã khai báo custom_item_fragment_customer_group
        //Set adapter cho listview
        customerGroupLayout = (LinearLayout) findViewById(R.id.customerGroupLayout);
        lvCustomerGroup = (ListView) findViewById(R.id.lvAddCustomerGroup);
        fabAddCustomerGroup = (FloatingActionButton) findViewById(R.id.fabAddCustomerGroup);

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

                LayoutInflater inflater = LayoutInflater.from(CustomerGroupInfoActivity.this);
                View mDialog = inflater.inflate(R.layout.dialog_add_group_customer,
                        (ViewGroup) findViewById(R.id.dialog_add_customer_group));

                edtCustomerGroup = (EditText) mDialog.findViewById(R.id.edtCustomerGroup);
                //TODO xử lý tên nhóm nhập vào

                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerGroupInfoActivity.this);
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
                                //TODO Lưu tên nhóm mà shop nhập
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


