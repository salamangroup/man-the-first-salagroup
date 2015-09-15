package com.salagroup.salaman.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.salagroup.salaman.R;

/**
 * Created by TrytoThuan on 14/09/2015.
 */
public class CustomerInfoActivity extends AppCompatActivity {
    private ListView lvCustomer;
    private FloatingActionButton fabAddCustomer;
    private LinearLayout customerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_customer_list);

        TextView tvTitleCustomer = (TextView) findViewById(R.id.tvTitleCustomer);
        setFontforTitle(tvTitleCustomer);

        //TODO Adapter & ListView
        //Khai báo Adapter, kèm layout đã khai báo custom_item_fragment_customer
        //Set adapter cho listview
        customerLayout = (LinearLayout) findViewById(R.id.customerLayout);
        lvCustomer = (ListView) findViewById(R.id.lvAddCustomer);
        fabAddCustomer = (FloatingActionButton) findViewById(R.id.fabAddCustomer);
        fabAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO code thêm khách hàng vào đây, lưu vào CSDL
                Snackbar.make(customerLayout,"hiii",Snackbar.LENGTH_LONG).setAction("Done", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
            }
        });

        lvCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO Chuyển sang thông tin khách hàng
            }
        });

        lvCustomer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
                //TODO Cho phép xóa nhiều khách hàng
            }
        });
    }
    public void setFontforTitle(TextView tvTitleCustomer){
        Typeface robotoFont = Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Light.ttf");
        tvTitleCustomer.setTypeface(robotoFont);
    }

}
