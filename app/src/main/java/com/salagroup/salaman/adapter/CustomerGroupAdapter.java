package com.salagroup.salaman.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.salagroup.salaman.R;
import com.salagroup.salaman.pojo.CustomerGroup;

import java.util.List;

public class CustomerGroupAdapter extends ArrayAdapter<CustomerGroup> {

    private static final int LAYOUT_RESOURCE = R.layout.custom_item_fragment_customer_group;

    private Context context;
    private List<CustomerGroup> customerGroups;

    public void setCustomerGroups(List<CustomerGroup> customerGroups) {
        this.customerGroups = customerGroups;
    }

    private class ViewHolder {

//        @Bind(R.id.tvNameCustomerGroup) TextView tvNameCustomerGroup;
//        @Bind(R.id.tvCountCustomerGroup) TextView tvCountCustomerGroup;
        TextView tvNameCustomerGroup;
        TextView tvCountCustomerGroup;
    }

    public CustomerGroupAdapter(Context context, List<CustomerGroup> customerGroups) {
        super(context, LAYOUT_RESOURCE, customerGroups);
        this.context = context;
        this.customerGroups = customerGroups;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, LAYOUT_RESOURCE, null);
//            ButterKnife.bind(this, convertView);
            viewHolder.tvNameCustomerGroup = (TextView) convertView.findViewById(R.id.tvNameCustomerGroup);
            viewHolder.tvCountCustomerGroup = (TextView) convertView.findViewById(R.id.tvCountCustomerGroup);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvNameCustomerGroup.setText(customerGroups.get(position).getCustomerGroupName());
        viewHolder.tvCountCustomerGroup.setText("("+CustomerGroup.getCustomerCountById(customerGroups.get(position).getId())+")");
        return convertView;
    }
}