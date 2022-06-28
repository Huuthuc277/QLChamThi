package com.example.detaigiuaki.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.detaigiuaki.R;
import com.example.detaigiuaki.database.DBQuanLyChamBai;
import com.example.detaigiuaki.model.MonHoc;


public class MonHocActivity extends AppCompatActivity {
    EditText etMaMH, etTenMH, etChiPhi;
    Button btnSave;
    String flag;

    Animation scaleUp, scaleDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_hoc);
        setControl();
        setEvent();
    }

    private void setEvent() {
        khoiTao();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag.equals("add")){
                    DBQuanLyChamBai db = new DBQuanLyChamBai(MonHocActivity.this);
                    MonHoc mh = new MonHoc();
                    String id = etMaMH.getText().toString();
                    String name = etTenMH.getText().toString();
                    String cost = etChiPhi.getText().toString();
                    if(!checkValidate(id) || id.contains(" ") ){
                        Toast.makeText(MonHocActivity.this, "Không được bỏ trống hay có kí tự trắng trong mã môn học.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!checkValidate(name)){
                        Toast.makeText(MonHocActivity.this, "Không được bỏ trống tên môn học", Toast.LENGTH_SHORT).show();
                    }
                    if(!checkValidate(cost)){
                        Toast.makeText(MonHocActivity.this, "Không dược bỏ trống chi phí", Toast.LENGTH_SHORT).show();
                    }
                    mh.setId(id);
                    mh.setName(name);
                    mh.setCost(Integer.parseInt(cost));
                    db.AddMonHoc(mh);
                    Toast.makeText(MonHocActivity.this, "Đã thêm thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MonHocActivity.this, QLMonHocActivity.class);
                    startActivity(intent);
                }
                if(flag.equals("edit")){
                    DBQuanLyChamBai db = new DBQuanLyChamBai(MonHocActivity.this);
                    MonHoc mh = new MonHoc();
                    String id = etMaMH.getText().toString();
                    String name = etTenMH.getText().toString();
                    String cost = etChiPhi.getText().toString();
                    if(!checkValidate(id) || id.contains(" ") ){
                        Toast.makeText(MonHocActivity.this, "Không được bỏ trống hay có kí tự trắng trong mã môn học.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!checkValidate(name)){
                        Toast.makeText(MonHocActivity.this, "Không được bỏ trống tên môn học", Toast.LENGTH_SHORT).show();
                    }
                    if(!checkValidate(cost)){
                        Toast.makeText(MonHocActivity.this, "Không dược bỏ trống chi phí", Toast.LENGTH_SHORT).show();
                    }
                    mh.setId(id);
                    mh.setName(name);
                    mh.setCost(Integer.parseInt(cost));
                    db.UpdateMonHoc(mh);
                    Toast.makeText(MonHocActivity.this, "Cập Nhật Môn Học Thành Công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MonHocActivity.this, QLMonHocActivity.class);
                    startActivity(intent);
                }
            }
        });
        btnSave.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == motionEvent.ACTION_UP){
                    btnSave.startAnimation(scaleUp);

                } else if(motionEvent.getAction() == motionEvent.ACTION_DOWN){
                    btnSave.startAnimation(scaleDown);

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
            String id = getIntent().getStringExtra("maMH");
            String name = getIntent().getStringExtra("tenMH");
            String cost = getIntent().getStringExtra("chiPhi");

            etMaMH.setText(id);
            etMaMH.setEnabled(false);
            etTenMH.setText(name);
            etChiPhi.setText(cost);
        }
    }

    private void setControl() {
        etMaMH = findViewById(R.id.etMaMH);
        etTenMH = findViewById(R.id.etTenMH);
        etChiPhi = findViewById(R.id.etChiPhi);
        btnSave = findViewById(R.id.btnLuuMH);
        scaleUp = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);
    }


}