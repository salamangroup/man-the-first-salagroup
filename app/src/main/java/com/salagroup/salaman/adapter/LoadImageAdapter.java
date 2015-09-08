package com.salagroup.salaman.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.salagroup.salaman.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by TrytoThuan on 06/09/2015.
 */
public class LoadImageAdapter extends ArrayAdapter<String> {

    private Context context;
    private int resId;
    private ArrayList<String> arrayList;

    public LoadImageAdapter(Context context, int resource, ArrayList<String> strImageLists) {
        super(context, resource, strImageLists);
        this.context = context;
        this.resId = resource;
        this.arrayList = strImageLists;
    }

    class ViewHolder {
        private ImageView imvFromUrl;
    }

    ViewHolder viewHolder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imvFromUrl = (ImageView) convertView.findViewById(R.id.imvFromUrl);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        final String strFromUrl = arrayList.get(position);
        if (strFromUrl != null) {

            Picasso.with(context).load(strFromUrl).resize(150,150).centerCrop().into(viewHolder.imvFromUrl);

        }

        return convertView;
    }
}
