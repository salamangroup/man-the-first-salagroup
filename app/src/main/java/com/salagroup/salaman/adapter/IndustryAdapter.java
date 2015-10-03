package com.salagroup.salaman.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.salagroup.salaman.R;
import com.salagroup.salaman.pojo.Industry;

import java.util.List;

public class IndustryAdapter extends ArrayAdapter<Industry> {

    private static final int LAYOUT_RESOURCE = R.layout.industry_item;

    private Context context;
    private List<Industry> industries;

    private class ViewHolder {
        CheckBox ckbIndustry;
    }

    public IndustryAdapter(Context context, List<Industry> industries) {
        super(context, LAYOUT_RESOURCE, industries);
        this.context = context;
        this.industries = industries;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, LAYOUT_RESOURCE, null);
            viewHolder.ckbIndustry = (CheckBox) convertView.findViewById(R.id.ckbIndustry);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ckbIndustry.setText(industries.get(position).getIndustryName());
        viewHolder.ckbIndustry.setChecked(industries.get(position).isChecked());
        viewHolder.ckbIndustry.setClickable(false);
        return convertView;
    }
}
