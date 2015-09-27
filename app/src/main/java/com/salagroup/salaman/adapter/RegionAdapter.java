package com.salagroup.salaman.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.salagroup.salaman.pojo.Region;

import java.util.ArrayList;

public class RegionAdapter extends ArrayAdapter<Region> {

    private static final int LAYOUT_RESOURCE = android.R.layout.simple_list_item_1;
    private static final int TEXTVIEW_ID = android.R.id.text1;

    private Context context;
    private ArrayList<Region> regions;

    class ViewHolder {
        TextView tvRegionName;
    }

    public RegionAdapter(Context context, ArrayList<Region> regions) {
        super(context, LAYOUT_RESOURCE, regions);
        this.context = context;
        this.regions = regions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, LAYOUT_RESOURCE, null);
            viewHolder = new ViewHolder();
            viewHolder.tvRegionName = (TextView) convertView.findViewById(TEXTVIEW_ID);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvRegionName.setText(regions.get(position).getRegionName());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
