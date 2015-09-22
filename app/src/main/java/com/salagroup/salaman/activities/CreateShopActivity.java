package com.salagroup.salaman.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.salagroup.salaman.R;
import com.salagroup.salaman.pojo.Shop;


public class CreateShopActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtTenCuaHang, edtSoDTGiaoDich, edtDiaChi;
    private Spinner spnTinhThanh;
    private Spinner spnQuanHuyen;
    private CheckBox ckbThucPham, ckbThoiTrang, ckbCongNghe, ckbGiaDung, ckbVanPhongPham, ckbSach, ckbMeVaBe;
    private boolean chkThucPham, chkThoiTrang, chkCongNghe, chkGiaDung, chkVanPhongPham, chkSach, chkMeVaBe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_info);

        edtTenCuaHang = (EditText) findViewById(R.id.edtTenCuaHang);
        edtSoDTGiaoDich = (EditText) findViewById(R.id.edtSoDTGiaoDich);
        edtDiaChi = (EditText) findViewById(R.id.edtDiaChi);
        spnTinhThanh = (Spinner) findViewById(R.id.spnTinhThanh);
        spnQuanHuyen = (Spinner) findViewById(R.id.spnQuanHuyen);
        ckbThucPham = (CheckBox) findViewById(R.id.ckbThucPham);
        ckbThoiTrang = (CheckBox) findViewById(R.id.ckbThoiTrang);
        ckbCongNghe = (CheckBox) findViewById(R.id.ckbCongNghe);
        TextView tvNganhKhac = (TextView) findViewById(R.id.tvNganhKhac);
        Button btnUpdate = (Button) findViewById(R.id.btnUpdate);

        initTinhThanh();
        initQuanHuyen(0);

        spnTinhThanh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                initQuanHuyen(id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                initQuanHuyen(0);
            }
        });

        tvNganhKhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = LayoutInflater.from(CreateShopActivity.this);
                View mDialog = inflater.inflate(R.layout.activity_store_info_dialog_other_types, (ViewGroup) findViewById(R.id.dialog_other_types_root_layout));

                ckbGiaDung = (CheckBox) mDialog.findViewById(R.id.chkGiaDung);
                ckbGiaDung.setChecked(chkGiaDung);
                ckbGiaDung.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        chkGiaDung = isChecked;
                    }
                });
                ckbVanPhongPham = (CheckBox) mDialog.findViewById(R.id.chkVanPhongPham);
                ckbVanPhongPham.setChecked(chkVanPhongPham);
                ckbVanPhongPham.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        chkVanPhongPham = isChecked;
                    }
                });
                ckbSach = (CheckBox) mDialog.findViewById(R.id.chkSach);
                ckbSach.setChecked(chkSach);
                ckbSach.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        chkSach = isChecked;
                    }
                });
                ckbMeVaBe = (CheckBox) mDialog.findViewById(R.id.chkMeVaBe);
                ckbMeVaBe.setChecked(chkMeVaBe);
                ckbMeVaBe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        chkMeVaBe = isChecked;
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(CreateShopActivity.this);
                builder.setView(mDialog)
                        .setTitle(R.string.store_info_item_other_types)
                        .setPositiveButton("Ok", null)
                        .show();
            }
        });

        btnUpdate.setOnClickListener(this);
    }

    private void initTinhThanh() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateShopActivity.this, android.R.layout.simple_spinner_dropdown_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount()));
                }
                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //TODO thêm các item quận/huyện từ database anh Phước cung cấp vào đây sau đó xóa "adapter.add("");"
        adapter.add("");
        adapter.add("Tỉnh / Thành");

        spnTinhThanh.setAdapter(adapter);
        spnTinhThanh.setSelection(adapter.getCount());
    }

    private void initQuanHuyen(long tinhthanhID) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateShopActivity.this, android.R.layout.simple_spinner_dropdown_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount()));
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }

        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (tinhthanhID != 0) {
            //TODO thêm các item quận/huyện từ database anh Phước cung cấp vào đây sau đó xóa "adapter.add("");"
            adapter.add("");
            adapter.add("Quận / Huyện");
        } else {
            adapter.add("");
            adapter.add("Quận / Huyện");
        }

        spnQuanHuyen.setAdapter(adapter);
        spnQuanHuyen.setSelection(adapter.getCount());
    }

    @Override
    public void onClick(View v) {
    }

    private Shop checkInput() {
        String shopName = edtTenCuaHang.getText().toString();
        String contactPhone = edtSoDTGiaoDich.getText().toString();
        String address = edtDiaChi.getText().toString();
        if (shopName.trim().isEmpty()) {
            edtTenCuaHang.setError("Vui lòng nhập tên của hàng.");
            edtTenCuaHang.requestFocus();
        } else if (contactPhone.trim().isEmpty()) {
            edtSoDTGiaoDich.setError("Vui lòng nhập tên số điện thoại giao dịch.");
            edtSoDTGiaoDich.requestFocus();
        } else if (address.trim().isEmpty()) {
            edtDiaChi.setError("Vui lòng nhập địa chỉ của cửa hàng!");
            edtDiaChi.requestFocus();
        }


        return null;
    }
}