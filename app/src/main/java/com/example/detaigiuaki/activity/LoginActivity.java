package com.example.detaigiuaki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.detaigiuaki.R;
import com.example.detaigiuaki.database.DBQuanLyChamBai;
import com.example.detaigiuaki.model.GiaoVien;
import com.example.detaigiuaki.model.NguoiDung;

import org.mindrot.jbcrypt.BCrypt;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {
    EditText etTaiKhoan;
    EditText etMatKhau;
    Button btnDangNhap;
    DBQuanLyChamBai db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setControl();
        setEvent();
    }

    private void setEvent() {
        khoiTao();
        db = new DBQuanLyChamBai(this);
        db.AddGiaoVien(new GiaoVien("01", "Thuc", "0338086422" ));
        db.AddNguoiDung(new NguoiDung("admin","123","GV","01","Thuc"),"01");
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taiKhoan = etTaiKhoan.getText().toString();
                String matKhau = etMatKhau.getText().toString();
                if(isEmpty(taiKhoan)){
                    Toast.makeText(LoginActivity.this, "Không được bỏ trống tên tài khoản.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(isEmpty(matKhau)){
                    Toast.makeText(LoginActivity.this, "Không được bỏ trống mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }
                NguoiDung nguoiDung = db.getNguoiDung(taiKhoan);
                if(nguoiDung == null){
                    Toast.makeText(LoginActivity.this, "Sai tên tài khoản!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(matKhau.equals(nguoiDung.getPassWord())){
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Sai mật khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // thực hiện việc chuyển qua màn hình chính
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("NguoiDung",nguoiDung);
                startActivity(intent);
                finish();
            }
        });
    }

    private void khoiTao() {

    }

    private boolean isEmpty(String s) {
        return s.isEmpty();
    }


    private void setControl() {
        etTaiKhoan = findViewById(R.id.etTaiKhoan);
        etMatKhau = findViewById(R.id.etMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
    }
}