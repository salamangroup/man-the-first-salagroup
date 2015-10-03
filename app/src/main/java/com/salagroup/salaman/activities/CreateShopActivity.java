package com.salagroup.salaman.activities;

import android.app.AlertDialog;
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
import com.salagroup.salaman.adapter.MyRegionAdapter;
import com.salagroup.salaman.helper.ValidationHelper;
import com.salagroup.salaman.pojo.Industry;
import com.salagroup.salaman.pojo.Region;
import com.salagroup.salaman.pojo.Shop;
import com.salagroup.salaman.pojo.ShopIndustry;
import com.salagroup.salaman.pojo.ShopWorkingPeriod;

import java.util.List;

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

    private List<Region> provincials;
    private List<Region> districts;
    private List<Industry> industries;
    private MyRegionAdapter provincialAdapter;
    private MyRegionAdapter districtsAdapter;
    private IndustryAdapter industryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_info);
        ButterKnife.bind(this);

        // Set listener
        tvNganhKhac.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        spnTinhThanh.setOnItemSelectedListener(this);
        spnQuanHuyen.setOnItemSelectedListener(this);

        industries = Industry.getAllIndustry();
        industryAdapter = new IndustryAdapter(this, industries);
        initProvincial();
        initDistrict();
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spnTinhThanh) {
            try {
                long _id = provincials.get(position).getId();
                initDistrict(_id);
            } catch (NullPointerException ex) {
                initDistrict();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

    private void initProvincial() {
        provincials = Region.getRegionByLevel(4);
        Region region = new Region();
        region.setRegionName("Tỉnh / Thành");
        provincials.add(region);
        provincialAdapter = new MyRegionAdapter(this, provincials);
        spnTinhThanh.setAdapter(provincialAdapter);
        spnTinhThanh.setSelection(provincialAdapter.getCount());
    }

    private void initDistrict(long provincialID) {
        districts = Region.getRegionByParentId(provincialID);
        Region region = new Region();
        region.setRegionName("Quận / Huyện");
        districts.add(region);
        districtsAdapter = new MyRegionAdapter(this, districts);
        spnQuanHuyen.setAdapter(districtsAdapter);
        spnQuanHuyen.setSelection(districtsAdapter.getCount());
    }

    private void initDistrict() {
        districts = Region.getNoneRegion();
        Region region = new Region();
        region.setRegionName("Quận / Huyện");
        districts.add(region);
        districts.add(region);
        districtsAdapter = new MyRegionAdapter(this, districts);
        spnQuanHuyen.setAdapter(districtsAdapter);
        spnQuanHuyen.setSelection(districtsAdapter.getCount());
    }

    private boolean checkInput() {
        boolean ok = true;
        String shopName = edtTenCuaHang.getText().toString().trim();
        String phone = edtSoDTGiaoDich.getText().toString().trim();
        String address = edtDiaChi.getText().toString().trim();
        String workingPeriod = edtWorkingPeriod.getText().toString().trim();

        if (shopName.length() < 1) {
            ok = false;
            edtTenCuaHang.setError("Vui lòng nhập tên của hàng.(2 kí tự trở lên)");
            edtTenCuaHang.requestFocus();
        } else if (!ValidationHelper.isViString(shopName)) {
            ok = false;
            edtTenCuaHang.setError("Vui lòng nhập tên của hàng chỉ gồm chữ cái, chữ số, kí tự _,-, khoảng trắng.");
            edtTenCuaHang.requestFocus();
        } else if (phone.isEmpty() || phone.length() > 11) {
            ok = false;
            edtSoDTGiaoDich.setError("Vui lòng nhập tên số điện thoại giao dịch.(tối đa 11 chữ số).");
            edtSoDTGiaoDich.requestFocus();
        } else if (phone.length() < 10) {
            ok = false;
            edtSoDTGiaoDich.setError("Vui lòng nhập tên số điện thoại giao dịch.(tối thiểu 10 chữ số).");
            edtSoDTGiaoDich.requestFocus();
        } else if (address.isEmpty()) {
            ok = false;
            edtDiaChi.setError("Vui lòng nhập địa chỉ của cửa hàng!");
            edtDiaChi.requestFocus();
        } else if (workingPeriod.isEmpty()) {
            ok = false;
            edtWorkingPeriod.setError("Vui lòng nhập thời gian làm việc cửa hàng!");
            edtWorkingPeriod.requestFocus();
        }

        return ok;
    }

    private void saveShopLocal() {
        String shopName = edtTenCuaHang.getText().toString();
        String phone = edtSoDTGiaoDich.getText().toString();
        String address = edtDiaChi.getText().toString();
        String swp = edtWorkingPeriod.getText().toString();

        Shop shop = new Shop();
        shop.setShopName(shopName);
        shop.setPhone(phone);
        shop.setAddress(address);

        long shopID = shop.save();
        if (shopID != -1) {
            Toast.makeText(this, "Tạo shop thành công", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < industries.size(); i++) {
                if (industries.get(i).isChecked()) {
                    ShopIndustry shopIndustry = new ShopIndustry();
                    shopIndustry.setShopID(shopID);
                    shopIndustry.setIndustryID(industries.get(i).getId());
                    shopIndustry.save();
                }
            }
            ShopWorkingPeriod shopWP = new ShopWorkingPeriod();
            shopWP.setShopID(shopID);
            shopWP.setDay(swp);
        }
    }
}