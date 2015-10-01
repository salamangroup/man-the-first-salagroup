package com.salagroup.salaman.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.salagroup.salaman.R;
import com.salagroup.salaman.adapter.CustomerAdapter;
import com.salagroup.salaman.pojo.Customer;

import java.util.List;

/**
 * Created by TrytoThuan on 14/09/2015.
 */
public class CustomerListActivity extends AppCompatActivity {

    private FloatingActionButton fabAddCustomer;
    private LinearLayout customerLayout;
    private CustomerAdapter mAdapter;
    private ListView lvCustomer;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_customer_list);
        mContext = this;

        TextView tvTitleCustomer = (TextView) findViewById(R.id.tvTitleCustomer);
        setFontforTitle(tvTitleCustomer);

        customerLayout = (LinearLayout) findViewById(R.id.customerLayout);
        lvCustomer = (ListView) findViewById(R.id.lvAddCustomer);

        mAdapter = new CustomerAdapter(this, new Customer().getAll());
        lvCustomer.setAdapter(mAdapter);

        fabAddCustomer = (FloatingActionButton) findViewById(R.id.fabAddCustomer);
        fabAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startCustomerDetailsActivity(-1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            CustomerAdapter mAdapter = new CustomerAdapter(this, new Customer().getAll());
            lvCustomer.setAdapter(mAdapter);

            Snackbar.make(customerLayout, "Cập nhật thành công...", Snackbar.LENGTH_LONG).show();
        }
        if (resultCode == -2) {

            CustomerAdapter mAdapter = new CustomerAdapter(this, new Customer().getAll());
            lvCustomer.setAdapter(mAdapter);

            Snackbar.make(customerLayout, "Đã xóa khách hàng...", Snackbar.LENGTH_LONG).show();
        }
    }

    public void setFontforTitle(TextView tvTitleCustomer) {
        Typeface robotoFont = Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Light.ttf");
        tvTitleCustomer.setTypeface(robotoFont);
    }

    public void startCustomerDetailsActivity(long id) {

        Intent intent = new Intent(this, CustomerDetailsActivity.class);
        intent.putExtra("id", id);
        startActivityForResult(intent, 0);
    }

    //Delete MultiItem
    public ActionMode actionMode;
    public ActionMode.Callback callback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.action_mode_delete, menu);

            fabAddCustomer.setVisibility(View.GONE);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete_all:

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Xóa " + actionMode.getTitle() + "?")
                            .setCancelable(true)
                            .setNegativeButton("Không", null)
                            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    for (int i = mAdapter.getCount() - 1; i >= 0; i--) {

                                        if (mAdapter.getItem(i).isChecked()) {

                                            long id = mAdapter.getItem(i).getId();
                                            Customer c = Customer.getCustomerById(id);
                                            c.setStatus(false);
                                            c.save();
                                        }
                                    }

                                    mAdapter = new CustomerAdapter(mContext, new Customer().getAll());
                                    lvCustomer.setAdapter(mAdapter);

                                    Snackbar.make(customerLayout, "Đã xóa " + actionMode.getTitle() + "...", Snackbar.LENGTH_LONG).show();

                                    if (actionMode != null) {

                                        actionMode.finish();
                                        actionMode = null;
                                    }
                                }
                            }).show();
                    return true;

                case R.id.action_select_all:

                    for (int i = 0; i < mAdapter.getCount(); i++) {

                        mAdapter.getItem(i).setChecked(true);
                    }
                    mAdapter.notifyDataSetChanged();
                    return true;
            }
            return false;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

            actionMode = null;
            fabAddCustomer.setVisibility(View.VISIBLE);
            for (int i = 0; i < mAdapter.getCount(); i++) {

                mAdapter.getItem(i).setChecked(false);
            }
            mAdapter.notifyDataSetChanged();
        }

    };
}
