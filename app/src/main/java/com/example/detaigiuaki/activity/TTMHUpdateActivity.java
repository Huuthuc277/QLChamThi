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
import android.widget.TextView;
import android.widget.Toast;

import com.example.detaigiuaki.R;
import com.example.detaigiuaki.database.DBQuanLyChamBai;
import com.example.detaigiuaki.model.ThongTinChamBai;


public class TTMHUpdateActivity extends AppCompatActivity {
    EditText etSoPhieu, etSoBai, etMaMH;
    Button btnLuu;
    String flag;
    Animation scaleUp, scaleDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttmhupdate);
        setControl();
        setEvent();
    }

    private void setEvent() {
        khoiTao();
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag.equals("add")){
                    DBQuanLyChamBai db = new DBQuanLyChamBai(TTMHUpdateActivity.this);
                    ThongTinChamBai ttcb = new ThongTinChamBai();
                    String maMh = etMaMH.getText().toString();
                    String soPhieu = etSoPhieu.getText().toString();
                    String soBai = etSoBai.getText().toString();
                    if(!checkValidate(maMh) || maMh.contains(" ") ){
                        Toast.makeText(TTMHUpdateActivity.this, "Không được bỏ trống hay có kí tự trắng trong mã môn học.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!checkValidate(soPhieu)){
                        Toast.makeText(TTMHUpdateActivity.this, "Không được bỏ trống tên môn học", Toast.LENGTH_SHORT).show();
                    }
                    if(!checkValidate(soBai)){
                        Toast.makeText(TTMHUpdateActivity.this, "Không dược bỏ trống chi phí", Toast.LENGTH_SHORT).show();
                    }
                    ttcb.setSoPhieu(Integer.parseInt(soPhieu));
                    ttcb.setSoBai(Integer.parseInt(soBai));
                    ttcb.setMaMH(maMh);
                    db.AddTTChamBai(ttcb);
                    Toast.makeText(TTMHUpdateActivity.this, "Đã thêm thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TTMHUpdateActivity.this, QLMonHocActivity.class);
                    startActivity(intent);
                }
                if(flag.equals("edit")){
                    DBQuanLyChamBai db = new DBQuanLyChamBai(TTMHUpdateActivity.this);
                    ThongTinChamBai ttcb = new ThongTinChamBai();
                    String maMh = etMaMH.getText().toString();
                    String soPhieu = etSoPhieu.getText().toString();
                    String soBai = etSoBai.getText().toString();
                    if(!checkValidate(maMh) || maMh.contains(" ") ){
                        Toast.makeText(TTMHUpdateActivity.this, "Không được bỏ trống hay có kí tự trắng trong mã môn học.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!checkValidate(soPhieu)){
                        Toast.makeText(TTMHUpdateActivity.this, "Không được bỏ trống số phiếu", Toast.LENGTH_SHORT).show();
                    }
                    if(!checkValidate(soBai)){
                        Toast.makeText(TTMHUpdateActivity.this, "Không dược bỏ trống số bài", Toast.LENGTH_SHORT).show();
                    }
                    ttcb.setSoPhieu(Integer.parseInt(soPhieu));
                    ttcb.setSoBai(Integer.parseInt(soBai));
                    ttcb.setMaMH(maMh);
                  //  db.UpdateMonHoc(ttcb);
                    //Intent intent = new Intent(TTMHUpdateActivity.this, QLMonHocActivity.class);
                    //startActivity(intent);
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
            ThongTinChamBai ttcb = (ThongTinChamBai) getIntent().getSerializableExtra("ThongTinChamBai");
            String maMH = ttcb.getMaMH();
            String soPhieu = String.valueOf(ttcb.getSoPhieu());
            String soBai = String.valueOf(ttcb.getSoBai());

            etMaMH.setText(maMH);
            etMaMH.setEnabled(false);
            etSoPhieu.setText(soPhieu);
            etSoBai.setText(soBai);
        }
    }

    private void setControl() {
        etSoPhieu = findViewById(R.id.etSoPhieu);
        etSoBai = findViewById(R.id.etSoBai);
        etMaMH = findViewById(R.id.etMaMHCB);
        btnLuu = findViewById(R.id.btnLuuTTCB);
        scaleUp = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);
    }
}