package com.example.detaigiuaki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.detaigiuaki.R;
import com.example.detaigiuaki.database.DBQuanLyChamBai;
import com.example.detaigiuaki.model.GiaoVien;
import com.example.detaigiuaki.model.NguoiDung;

import java.util.ArrayList;

public class ThemGiaoVienActivity extends AppCompatActivity {
    EditText etTaiKhoan, etMatKhau, etMaGV, etTenGV, etSoDT;
    TextView tvTK, tvMK;
    Button btnLuu;
    String flag;
    Animation scaleUp, scaleDown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_giao_vien);
        setControl();
        setEvent();
    }

    private void setEvent() {
        khoiTao();
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag.equals("add")){
                    DBQuanLyChamBai db = new DBQuanLyChamBai(ThemGiaoVienActivity.this);
                    ArrayList<GiaoVien> list = db.getDSGiaoVien();
                    ArrayList<NguoiDung> listND = db.ReadNguoiDung();
                    GiaoVien gv = new GiaoVien();
                    String maGV = etMaGV.getText().toString();
                    String tenGV = etTenGV.getText().toString();
                    String soDT = etSoDT.getText().toString();
                    String tenTK = etTaiKhoan.getText().toString();
                    String matKhau = etMatKhau.getText().toString();
                    for (GiaoVien item:list) {
                        if(maGV.equals(item.getId())){
                            Toast.makeText(ThemGiaoVienActivity.this, "Đã có mã giáo viên này rồi! Mời nhập lại", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    for (NguoiDung items:listND) {
                        if(tenTK.equals(items.getUserName())){
                            Toast.makeText(ThemGiaoVienActivity.this, "Đã có tài khoản này! Mời nhập lại", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    if(!checkValidate(maGV) || maGV.contains(" ") ){
                        Toast.makeText(ThemGiaoVienActivity.this, "Không được bỏ trống hay có kí tự trắng trong mã giáo viên.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!checkValidate(tenGV)){
                        Toast.makeText(ThemGiaoVienActivity.this, "Không được bỏ trống tên giáo viên", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!checkValidate(soDT) || soDT.contains("\\D")){
                        Toast.makeText(ThemGiaoVienActivity.this, "Không dược bỏ trống số đt hoặc chứa kí tự khác số", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!checkValidate(tenTK) || !checkValidate(matKhau)){
                        Toast.makeText(ThemGiaoVienActivity.this, "Không dược bỏ trống tên tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    gv.setId(maGV);
                    gv.setName(tenGV);
                    gv.setPhone(soDT);
                    NguoiDung nguoiDung = new NguoiDung();
                    nguoiDung.setUserName(tenTK);
                    nguoiDung.setPassWord(matKhau);
                    nguoiDung.setRole("Giáo Viên");
                    db.AddGiaoVien(gv);
                    db.AddNguoiDung(nguoiDung, gv.getId());
                    Toast.makeText(ThemGiaoVienActivity.this, "Đã thêm thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ThemGiaoVienActivity.this, QLGiaoVienActivity.class);
                    startActivity(intent);
                }
                if(flag.equals("edit")){
                    DBQuanLyChamBai db = new DBQuanLyChamBai(ThemGiaoVienActivity.this);
                    GiaoVien gv = new GiaoVien();
                    String maGV = etMaGV.getText().toString();
                    String tenGV = etTenGV.getText().toString();
                    String soDT = etSoDT.getText().toString();
                    if(!checkValidate(tenGV)){
                        Toast.makeText(ThemGiaoVienActivity.this, "Không được bỏ trống tên giáo viên", Toast.LENGTH_SHORT).show();
                    }
                    if(!checkValidate(soDT) || soDT.contains("\\D")){
                        Toast.makeText(ThemGiaoVienActivity.this, "Không dược bỏ trống số đt hoặc chứa kí tự khác số", Toast.LENGTH_SHORT).show();
                    }
                    gv.setId(maGV);
                    gv.setName(tenGV);
                    gv.setPhone(soDT);
                    db.UpdateGiaoVien(gv);
                    Toast.makeText(ThemGiaoVienActivity.this, "Đã Cập Nhật GV thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ThemGiaoVienActivity.this, QLGiaoVienActivity.class);
                    startActivity(intent);
                }
            }
        });
        btnLuu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == motionEvent.ACTION_UP){
                    btnLuu.startAnimation(scaleUp);

                } else if(motionEvent.getAction() == motionEvent.ACTION_DOWN){
                    btnLuu.startAnimation(scaleDown);

                }
                return false;
            }
        });
    }

    private boolean checkValidate(String s) {
        if(s.isEmpty()) return false;
        return true;
    }

    private void khoiTao() {
        flag = getIntent().getStringExtra("flag");
        if(flag.equals("edit")){
            GiaoVien gv = (GiaoVien) getIntent().getSerializableExtra("GiaoVien");
            etTaiKhoan.setVisibility(View.INVISIBLE);
            etMatKhau.setVisibility(View.INVISIBLE);
            tvTK.setVisibility(View.INVISIBLE);
            tvMK.setVisibility(View.INVISIBLE);
            etMaGV.setText(gv.getId());
            etMaGV.setEnabled(false);
            etTenGV.setText(gv.getName());
            etSoDT.setText(gv.getPhone());
        }
    }

    private void setControl() {
        etTaiKhoan = findViewById(R.id.etTaiKhoanGV);
        etMatKhau = findViewById(R.id.etMatKhauGV);
        etMaGV = findViewById(R.id.etMaGV);
        etTenGV = findViewById(R.id.etTenGV);
        etSoDT = findViewById(R.id.etSoDT);
        btnLuu = findViewById(R.id.btnLuuGV);
        tvTK = findViewById(R.id.tvTK);
        tvMK = findViewById(R.id.tvMK);
        scaleUp = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);
    }
}