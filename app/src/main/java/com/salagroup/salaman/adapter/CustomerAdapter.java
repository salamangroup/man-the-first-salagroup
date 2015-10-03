package com.salagroup.salaman.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.salagroup.salaman.R;
import com.salagroup.salaman.fragments.CustomerListFragment;
import com.salagroup.salaman.pojo.Customer;
import com.salagroup.salaman.pojo.Region;

import java.util.List;

/**
 * Created by Leon on 29/09/2015.
 */
public class CustomerAdapter extends ArrayAdapter<Customer> {

    private static final int LAYOUT_RESOURCE = R.layout.custom_item_fragment_customer;

    private Context context;
    public List<Customer> customers;
    private CustomerListFragment mCustomerListFragment;

    public void setModel(List<Customer> customers) {
        this.customers = customers;
    }

    private class ViewHolder {

        TextView tvNameCustomer;
        TextView tvCountCustomer;
        TextView tvAddressCustomer;
        TextView tvPhoneCustomer;

        LinearLayout layoutRoot;
        CheckBox chkSelect;
    }

    public CustomerAdapter(Context context, List<Customer> customers, CustomerListFragment mCustomerListFragment) {
        super(context, LAYOUT_RESOURCE, customers);
        this.context = context;
        this.customers = customers;
        this.mCustomerListFragment = mCustomerListFragment;
    }

    @Override
    public int getCount() {
        if (customers != null) {
            return customers.size();
        }
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, LAYOUT_RESOURCE, null);
            viewHolder.tvNameCustomer = (TextView) convertView.findViewById(R.id.tvNameCustomer);
            viewHolder.tvCountCustomer = (TextView) convertView.findViewById(R.id.tvCountCustomer);
            viewHolder.tvAddressCustomer = (TextView) convertView.findViewById(R.id.tvAddressCustomer);
            viewHolder.tvPhoneCustomer = (TextView) convertView.findViewById(R.id.tvPhoneCustomer);

            viewHolder.layoutRoot = (LinearLayout) convertView.findViewById(R.id.layoutRoot);
            viewHolder.chkSelect = (CheckBox) convertView.findViewById(R.id.chkSelect);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvNameCustomer.setText(customers.get(position).getCustomerName());
        String diachi = customers.get(position).getAddress() + ", " + Region.getRegionNameById(customers.get(position).getRegionL5()) + ", " + Region.getRegionNameById(customers.get(position).getRegionL4());
        viewHolder.tvAddressCustomer.setText(diachi);
        viewHolder.tvPhoneCustomer.setText(customers.get(position).getPhone());
        viewHolder.tvCountCustomer.setText("("+Customer.getInvoiceCountById(customers.get(position).getId())+")");

        //region Multidelete
        viewHolder.chkSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                customers.get(position).setChecked(isChecked);
                notifyDataSetChanged();

                if (isChecked) {
                    viewHolder.layoutRoot.setBackgroundColor(Color.parseColor("#B6B6B6"));
                } else {
                    viewHolder.layoutRoot.setBackgroundColor(Color.parseColor("#00000000"));
                }

                int chkCount = 0;
                for (int i = 0; i < customers.size(); i++) {

                    if (customers.get(i).isChecked()) {
                        chkCount++;
                    }
                }

                if (chkCount == 0) {

                    if (mCustomerListFragment.actionMode != null) {

                        mCustomerListFragment.actionMode.finish();
                        mCustomerListFragment.actionMode = null;

                    }
                } else {

                    if (mCustomerListFragment.actionMode == null) {

                        mCustomerListFragment.startActionMode();
                        mCustomerListFragment.actionMode.setTitle(chkCount + " khách hàng");

                    } else {

                        mCustomerListFragment.actionMode.setTitle(chkCount + " khách hàng");
                    }
                }
            }
        });
        viewHolder.chkSelect.setChecked(customers.get(position).isChecked());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mCustomerListFragment.actionMode == null) {
                    mCustomerListFragment.startCustomerDetailsActivity(customers.get(position).getId());
                } else {
                    viewHolder.chkSelect.setChecked(!viewHolder.chkSelect.isChecked());
                }
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                viewHolder.chkSelect.setChecked(true);
                return true;
            }
        });
        //endregion

        convertView.setBackgroundResource(R.drawable.view_touch_selector);

        return convertView;
    }
}