package com.salagroup.salaman.activities;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.salagroup.salaman.R;
import com.salagroup.salaman.adapter.IndustryAdapter;
import com.salagroup.salaman.adapter.RegionAdapter;
import com.salagroup.salaman.data.ClientDBContract;
import com.salagroup.salaman.data.SQLiteDatasource;
import com.salagroup.salaman.pojo.Industry;
import com.salagroup.salaman.pojo.Region;
import com.salagroup.salaman.pojo.Shop;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class CreateShopActivity extends AppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final int INDUSTRY_FOOD = 0;
    private static final int INDUSTRY_ELECTRONIC = 4;
    private static final int INDUSTRY_FASHION = 8;

    @Bind(R.id.edtTenCuaHang)
    EditText edtTenCuaHang;
    @Bind(R.id.edtSoDTGiaoDich)
    EditText edtSoDTGiaoDich;
    @Bind(R.id.edtDiaChi)
    EditText edtDiaChi;
    @Bind(R.id.spnTinhThanh)
    Spinner spnTinhThanh;
    @Bind(R.id.spnQuanHuyen)
    Spinner spnQuanHuyen;
    @Bind(R.id.edtWorkingPeriod)
    EditText edtWorkingPeriod;
    @Bind(R.id.ckbThucPham)
    CheckBox ckbThucPham;
    @Bind(R.id.ckbThoiTrang)
    CheckBox ckbThoiTrang;
    @Bind(R.id.ckbCongNghe)
    CheckBox ckbCongNghe;
    @Bind(R.id.tvNganhKhac)
    TextView tvNganhKhac;
    @Bind(R.id.btnUpdate)
    Button btnUpdate;

    private SQLiteDatasource datasource;
    private ArrayList<Region> provincials;
    private ArrayList<Region> districts;
    private ArrayList<Industry> industries;
    private IndustryAdapter industryAdapter;

    private long regionL4;
    private long regionL5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_info);
        ButterKnife.bind(this);

        datasource = new SQLiteDatasource(this);
        provincials = datasource.getRegion(ClientDBContract.TableRegion.REGION_LEVEL_4);

        // Load provincial
        RegionAdapter provincialAdapter = new RegionAdapter(this, provincials);
        spnTinhThanh.setAdapter(provincialAdapter);

        // Load industry
        industries = datasource.getAllIndustries();
        industryAdapter = new IndustryAdapter(CreateShopActivity.this, industries);

        // Set listener
        tvNganhKhac.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        spnTinhThanh.setOnItemSelectedListener(this);
        spnQuanHuyen.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvNganhKhac:
                pickIndustry();
                break;
            case R.id.btnUpdate:
                if (checkInput()) {
                    saveShopLocal();
                }
                break;
        }

    }

    private void saveShopLocal() {
        String shopName = edtTenCuaHang.getText().toString();
        String phone = edtSoDTGiaoDich.getText().toString();
        String address = edtDiaChi.getText().toString();
        String swp = edtWorkingPeriod.getText().toString();
        ContentValues shopValues = new ContentValues();
        shopValues.put(ClientDBContract.TableShop.COL_SHOP_NAME, shopName);
        shopValues.put(ClientDBContract.TableShop.COL_PHONE, phone);
        shopValues.put(ClientDBContract.TableShop.COL_ADDRESS, address);
        shopValues.put(ClientDBContract.TableShop.COL_REGION_L4, regionL4);
        shopValues.put(ClientDBContract.TableShop.COL_REGION_L5, regionL5);
        long shopID = datasource.createShop(shopValues);
        if (shopID != -1) {
            Toast.makeText(this, "Tạo shop thành công", Toast.LENGTH_SHORT).show();

            ContentValues shopIndustryValues;
            for (int i = 0; i < industries.size(); i++) {
                if (industries.get(i).isChecked()) {
                    long industryID = industries.get(i).get_id();
                    shopIndustryValues = new ContentValues();
                    shopIndustryValues.put(ClientDBContract.TableShopIndustry.COL_SHOP_ID, shopID);
                    shopIndustryValues.put(ClientDBContract.TableShopIndustry.COL_INDUSTRY_ID, industryID);
                    datasource.insertShopIndustry(shopIndustryValues);
                }
            }

            ContentValues swpValues = new ContentValues();
            swpValues.put(ClientDBContract.TableSWP.COL_SHOP_ID, shopID);
            swpValues.put(ClientDBContract.TableSWP.COL_DAY, swp);
            datasource.insertSWP(swpValues);
        }
    }

    private void pickIndustry() {

        boolean chkThucPham = ckbThucPham.isChecked();
        boolean chkThoiTrang = ckbThoiTrang.isChecked();
        boolean chkCongNghe = ckbCongNghe.isChecked();

        industries.get(INDUSTRY_FOOD).setIsChecked(chkThucPham);
        industries.get(INDUSTRY_ELECTRONIC).setIsChecked(chkCongNghe);
        industries.get(INDUSTRY_FASHION).setIsChecked(chkThoiTrang);

        LayoutInflater inflater = LayoutInflater.from(CreateShopActivity.this);
        View dialog = inflater.inflate(R.layout.activity_store_info_dialog_other_types, null);
        ListView lvOtherIndustry = (ListView) dialog;
        lvOtherIndustry.setAdapter(industryAdapter);
        lvOtherIndustry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox ckbIndustry = (CheckBox) view.findViewById(R.id.ckbIndustry);
                ckbIndustry.performClick();
                industries.get(position).setIsChecked(ckbIndustry.isChecked());
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateShopActivity.this);
        builder.setView(dialog)
                .setTitle(R.string.store_info_item_other_types)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ckbThucPham.setChecked(industries.get(INDUSTRY_FOOD).isChecked());
                        ckbCongNghe.setChecked(industries.get(INDUSTRY_ELECTRONIC).isChecked());
                        ckbThoiTrang.setChecked(industries.get(INDUSTRY_FASHION).isChecked());
                    }
                })
                .show();
    }

    private boolean checkInput() {
        boolean ok = true;
        String shopName = edtTenCuaHang.getText().toString();
        String phone = edtSoDTGiaoDich.getText().toString();
        String address = edtDiaChi.getText().toString();
        String workingPeriod = edtWorkingPeriod.getText().toString();
        if (shopName.trim().isEmpty()) {
            ok = false;
            edtTenCuaHang.setError("Vui lòng nhập tên của hàng.");
            edtTenCuaHang.requestFocus();
        } else if (phone.trim().isEmpty()) {
            ok = false;
            edtSoDTGiaoDich.setError("Vui lòng nhập tên số điện thoại giao dịch.");
            edtSoDTGiaoDich.requestFocus();
        } else if (address.trim().isEmpty()) {
            ok = false;
            edtDiaChi.setError("Vui lòng nhập địa chỉ của cửa hàng!");
            edtDiaChi.requestFocus();
        } else if (workingPeriod.trim().isEmpty()) {
            ok = false;
            edtWorkingPeriod.setError("Vui lòng nhập thời gian làm việc cửa hàng!");
            edtWorkingPeriod.requestFocus();
        } else {
            Shop shop = datasource.getShopByName(shopName);
            if (shop != null) {
                ok = false;
                edtTenCuaHang.setError("Tên của hàng đã tồn tại!");
                edtTenCuaHang.requestFocus();
            }
        }
        return ok;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spnTinhThanh:
                long parentID = provincials.get(position).get_id();
                regionL4 = parentID;
                districts = datasource.getRegion(parentID);
                RegionAdapter districtAdapter = new RegionAdapter(this, districts);
                spnQuanHuyen.setAdapter(districtAdapter);
                break;
            case R.id.spnQuanHuyen:
                regionL5 = districts.get(position).get_id();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}