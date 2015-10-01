package com.salagroup.salaman.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.salagroup.salaman.pojo.Region;

import java.util.List;

public class MyRegionAdapter extends ArrayAdapter<Region> {

    private static final int LAYOUT_RESOURCE = android.R.layout.simple_spinner_dropdown_item;
    private static final int TEXTVIEW_ID = android.R.id.text1;

    private Context context;
    private List<Region> regions;

    class ViewHolder {
        TextView tvRegionName;
    }

//    public void addRegion(Region region){
//
//        this.regions.add(region);
//    }

    public MyRegionAdapter(Context context, List<Region> regions) {
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

        if (position == getCount()) {
            ((TextView) convertView.findViewById(android.R.id.text1)).setText("");
            ((TextView) convertView.findViewById(android.R.id.text1)).setHint(getItem(getCount()).getRegionName());
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return super.getCount() - 1; // you dont display last item. It is used as hint.
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}