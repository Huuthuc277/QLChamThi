package com.example.detaigiuaki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.detaigiuaki.R;
import com.example.detaigiuaki.adapter.BaseApdapterSpinner;
import com.example.detaigiuaki.database.DBQuanLyChamBai;
import com.example.detaigiuaki.model.MonHoc;
import com.example.detaigiuaki.model.PhieuChamBai;
import com.example.detaigiuaki.model.ThongTinChamBai;

import java.util.ArrayList;

public class ThemUpdateTTCBActitity extends AppCompatActivity {
    Spinner spTenMH;
    ArrayList<MonHoc> listMH = new ArrayList<MonHoc>();
    TextView tvMaMH;
    EditText edSoBai;
    TextView tvSoPhieu;
    Button btnSave;
    String flag;
    PhieuChamBai pcbCha= null;
    Animation scaleUp, scaleDown;
    int image[] = {R.drawable.iconsubject,R.drawable.iconsubject,R.drawable.iconsubject,R.drawable.iconsubject,R.drawable.iconsubject,R.drawable.iconsubject};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themupdate_ttchambai);
        setControl();
        setEvent();
    }

    void setControl() {
        spTenMH = (Spinner) findViewById(R.id.spTenMHTTCB);
        tvMaMH = findViewById(R.id.tvMaMHTTCB);
        tvSoPhieu = findViewById(R.id.tvSoPhieuTTCB);
        edSoBai = findViewById(R.id.etSoBai);
        btnSave = findViewById(R.id.btnSaveTTCB);
        scaleUp = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);
    }

    void setEvent() {
        khoiTao();
    }

    void khoiTao() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             pcbCha = (PhieuChamBai) extras.getSerializable("PhieuChamBai");
        }

        String maMH="";
        String soPhieu="";
        String soBai="";
        flag = getIntent().getStringExtra("flag");
        if(flag.equals("edit")){
            ThongTinChamBai ttcb = (ThongTinChamBai) getIntent().getSerializableExtra("ThongTinChamBai");
             maMH = ttcb.getMaMH();
             soPhieu = String.valueOf(ttcb.getSoPhieu());
             soBai= String.valueOf(ttcb.getSoBai());
        }
        DBQuanLyChamBai db = new DBQuanLyChamBai(ThemUpdateTTCBActitity.this);
        listMH = db.ReadMonHoc();
        ArrayList<String> listStrMH= new ArrayList<>();
        int vt=-1;
        for(int i=0;i<listMH.size();i++){
            listStrMH.add(listMH.get(i).getName());
            if(listMH.get(i).getId().equals(maMH))
            {
                vt= i;

            }
        }
        BaseApdapterSpinner adapter= new BaseApdapterSpinner(this,image,listStrMH);
        spTenMH.setAdapter(adapter);
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listStrMH);
//        spTenMH.setAdapter(adapter);
        System.out.println("\n\ni: "+ vt);
        if(flag.equals("edit"))
        {
            tvMaMH.setText(maMH);
            spTenMH.setSelection(vt);
            tvSoPhieu.setText(soPhieu);
            edSoBai.setText(soBai);
        }
        else
        {
            if(listMH.size()>0) tvMaMH.setText(listMH.get(0).toString());
            tvSoPhieu.setText(String.valueOf(pcbCha.getSoPhieu()));
        }
        spTenMH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tvMaMH.setText(String.valueOf(listMH.get(i).getId()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag.equals("add"))
                {
                    if(!checkValidate(edSoBai.getText().toString())){
                        Toast.makeText(ThemUpdateTTCBActitity.this, "Không dược bỏ trống số bài", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int soPhieu=Integer.valueOf(tvSoPhieu.getText().toString());
                    String maMH= tvMaMH.getText().toString();
                    int soBai= Integer.valueOf(edSoBai.getText().toString());

                    ThongTinChamBai ttcb= new ThongTinChamBai();
                    ttcb.setSoPhieu(soPhieu);
                    ttcb.setMaMH(maMH);
                    ttcb.setSoBai(soBai);
                    try {
                        DBQuanLyChamBai db = new DBQuanLyChamBai(ThemUpdateTTCBActitity.this);
                        db.AddTTChamBai(ttcb);
                        Toast.makeText(ThemUpdateTTCBActitity.this, "Đã thêm thành công", Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(ThemUpdateTTCBActitity.this, "Thêm TTCB thất bại", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(ThemUpdateTTCBActitity.this, TTChamBaiActivity.class);
                    intent.putExtra("PhieuChamBai",pcbCha);
                    startActivity(intent);
                }
               else if(flag.equals("edit"))
                {
                    spTenMH.setEnabled(true);
                    if(!checkValidate(edSoBai.getText().toString())){
                        Toast.makeText(ThemUpdateTTCBActitity.this, "Không dược bỏ trống số bài", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int soPhieu=Integer.valueOf(tvSoPhieu.getText().toString());
                    String maMH= tvMaMH.getText().toString();
                    int soBai= Integer.valueOf(edSoBai.getText().toString());
                    System.out.println("\n\nSỐ BÀI: "+ soBai);
                    ThongTinChamBai ttcb= new ThongTinChamBai();
                    ttcb.setSoPhieu(soPhieu);
                    ttcb.setMaMH(maMH);
                    ttcb.setSoBai(soBai);
                    try {
                        DBQuanLyChamBai db = new DBQuanLyChamBai(ThemUpdateTTCBActitity.this);
                        db.UpdateTTChamBai(ttcb,maMH);
                        Toast.makeText(ThemUpdateTTCBActitity.this, "Cập Nhật thành công", Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(ThemUpdateTTCBActitity.this, "Cập Nhật TTCB thất bại", Toast.LENGTH_SHORT).show();
                    }
                    spTenMH.setEnabled(true);
                    Intent intent = new Intent(ThemUpdateTTCBActitity.this, TTChamBaiActivity.class);
                    intent.putExtra("PhieuChamBai",pcbCha);
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
}
