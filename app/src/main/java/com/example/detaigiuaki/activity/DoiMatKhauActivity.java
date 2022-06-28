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

public class DoiMatKhauActivity extends AppCompatActivity {
    EditText edMKC, edMKM, edXNMKM;
    Button btnDoiMK;
    Animation scaleUp, scaleDown;
    NguoiDung nd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doimatkhau);
        setControl();
        setEvent();
    }

    private void setEvent() {
        khoiTao();
        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkValidate(edMKC.getText().toString()))
                {
                    Toast.makeText(DoiMatKhauActivity.this, "Không được bỏ trống Mật Khẩu Cũ.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!edMKC.getText().toString().equals(nd.getPassWord()))
                {
                    Toast.makeText(DoiMatKhauActivity.this, "Sai Mật Khẩu Cũ.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!checkValidate(edMKM.getText().toString()))
                {
                    Toast.makeText(DoiMatKhauActivity.this, "Không được bỏ trống Mật Khẩu Mới.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!checkValidate(edXNMKM.getText().toString()))
                {
                    Toast.makeText(DoiMatKhauActivity.this, "Không được bỏ trống Xác Nhận Mật Khẩu Mới.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!edXNMKM.getText().toString().equals(edMKM.getText().toString()))
                {
                    Toast.makeText(DoiMatKhauActivity.this, "Mật Khẩu Mới không khớp.", Toast.LENGTH_SHORT).show();
                    return;
                }
                nd.setPassWord(edMKM.getText().toString());
                System.out.println(nd.getPassWord());
                System.out.println("usernaem: "+ nd.getUserName());
                DBQuanLyChamBai db = new DBQuanLyChamBai(getApplicationContext());
                try {
                    db.updateNguoiDung(nd);
                    Toast.makeText(DoiMatKhauActivity.this, "Đổi mật khẩu thành công.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("NguoiDung",nd);
                    startActivity(intent);
                }
                catch (Exception e)
                {
                    System.out.println("Lỗi: "+ e.getMessage());
                    Toast.makeText(DoiMatKhauActivity.this, "Đổi mật khẩu thất bại.", Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        });
    }

    private boolean checkValidate(String s) {
        if(s.isEmpty()) return false;
        return true;
    }

    private void khoiTao() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nd=(NguoiDung) extras.getSerializable("NguoiDung");
            System.out.println(nd.getUserName());
        }

    }

    private void setControl() {
        edMKC= findViewById(R.id.etMKC);
        edMKM= findViewById(R.id.etMKM);
        edXNMKM= findViewById(R.id.etXNMKM);
        btnDoiMK=findViewById(R.id.btnDoiMK);
        scaleUp = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);
    }
}
