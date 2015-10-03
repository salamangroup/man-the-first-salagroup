package com.salagroup.salaman.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.salagroup.salaman.R;
import com.salagroup.salaman.adapter.CustomerAdapter;
import com.salagroup.salaman.pojo.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TrytoThuan on 14/09/2015.
 */
public class CustomerListFragment extends Fragment implements ActionMode.Callback {

    private FloatingActionButton fabAddCustomer;
    private LinearLayout customerLayout;
    private CustomerAdapter mAdapter;
    private Context mContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_list, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        mAdapter.setModel(Customer.getAllActive());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = this.getActivity();

        TextView tvTitleCustomer = (TextView) getActivity().findViewById(R.id.tvTitleCustomer);
        setFontforTitle(tvTitleCustomer);

        customerLayout = (LinearLayout) getActivity().findViewById(R.id.customerLayout);
        ListView lvCustomer = (ListView) getActivity().findViewById(R.id.lvAddCustomer);

        mAdapter = new CustomerAdapter(mContext, Customer.getAllActive(), this);
        lvCustomer.setAdapter(mAdapter);

        fabAddCustomer = (FloatingActionButton) getActivity().findViewById(R.id.fabAddCustomer);
        fabAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startCustomerDetailsFragment(-1);
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK) {
//
//            mAdapter.setModel(Customer.getAllActive());
//            mAdapter.notifyDataSetChanged();
//
//            Snackbar.make(customerLayout, "Cập nhật thành công...", Snackbar.LENGTH_LONG).show();
//        }
//        if (resultCode == -2) {
//
//            mAdapter.setModel(Customer.getAllActive());
//            mAdapter.notifyDataSetChanged();
//
//            Snackbar.make(customerLayout, "Đã xóa khách hàng...", Snackbar.LENGTH_LONG).show();
//        }
//    }

    public void setFontforTitle(TextView tvTitleCustomer) {
        Typeface robotoFont = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf");
        tvTitleCustomer.setTypeface(robotoFont);
    }

    public void startCustomerDetailsFragment(long id) {

//        Intent intent = new Intent(this.getActivity(), CustomerDetailsFragment.class);
//        intent.putExtra("id", id);
//        startActivity(intent);

        Fragment detailFragment = new CustomerDetailsFragment();


        Bundle mBundle = new Bundle();
        mBundle.putLong("id", id);
        detailFragment.setArguments(mBundle);

        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //check if the device is landscape or portrait
        Configuration configuration = getActivity().getResources().getConfiguration();
        int ori = configuration.orientation;

        fragmentTransaction.replace(R.id.container, detailFragment);

        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    //region Multidelete
    public ActionMode actionMode;

    public void startActionMode() {

        actionMode = getActivity().startActionMode(CustomerListFragment.this);
    }

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

                                final ArrayList<Long> undoLists = new ArrayList<Long>();

                                for (int i = mAdapter.customers.size() - 1; i >= 0; i--) {

                                    if (mAdapter.customers.get(i).isChecked()) {

                                        long id = mAdapter.customers.get(i).getId();
                                        Customer c = Customer.getCustomerById(id);
                                        c.setChecked(false);
                                        c.setStatus(false);
                                        c.save();

                                        undoLists.add(id);
                                    }
                                }

                                mAdapter.setModel(Customer.getAllActive());
                                mAdapter.notifyDataSetChanged();

                                Snackbar.make(customerLayout, "Đã xóa " + actionMode.getTitle() + "...", Snackbar.LENGTH_LONG)
                                        .setAction("Hoàn tác", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                List<Customer> customers = Customer.getAll();
                                                for (int i = 0; i < customers.size(); i++) {

                                                    if (undoLists.contains(customers.get(i).getId())) {

                                                        long id = customers.get(i).getId();
                                                        Customer c = Customer.getCustomerById(id);
                                                        c.setStatus(true);
                                                        c.save();
                                                    }
                                                }

                                                mAdapter.setModel(Customer.getAllActive());
                                                mAdapter.notifyDataSetChanged();
                                            }
                                        })
                                        .show();

                                if (actionMode != null) {

                                    actionMode.finish();
                                    actionMode = null;
                                }
                            }
                        }).show();
                return true;

            case R.id.action_select_all:

                for (int i = 0; i < mAdapter.customers.size(); i++) {

                    mAdapter.customers.get(i).setChecked(true);
                }
                mAdapter.setModel(Customer.getAllActive());
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
        for (int i = 0; i < mAdapter.customers.size(); i++) {

            mAdapter.customers.get(i).setChecked(false);
        }
        mAdapter.setModel(Customer.getAllActive());
        mAdapter.notifyDataSetChanged();
    }
    //endregion
}
