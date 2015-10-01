package com.salagroup.salaman.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.salagroup.salaman.pojo.CustomerGroup;

import java.util.List;

public class CustomerGroupSpinnerAdapter extends ArrayAdapter<CustomerGroup> {

    private static final int LAYOUT_RESOURCE = android.R.layout.simple_list_item_1;
    private static final int TEXTVIEW_ID = android.R.id.text1;

    private Context context;
    private List<CustomerGroup> customerGroups;

    class ViewHolder {
        TextView tvCustomerGroupName;
    }

    public CustomerGroupSpinnerAdapter(Context context, List<CustomerGroup> customerGroups) {
        super(context, LAYOUT_RESOURCE, customerGroups);
        this.context = context;
        this.customerGroups = customerGroups;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, LAYOUT_RESOURCE, null);
            viewHolder = new ViewHolder();
            viewHolder.tvCustomerGroupName = (TextView) convertView.findViewById(TEXTVIEW_ID);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvCustomerGroupName.setText(customerGroups.get(position).getCustomerGroupName());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}