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
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.detaigiuaki.R;
import com.example.detaigiuaki.adapter.BaseApdapterSpinner;
import com.example.detaigiuaki.database.DBQuanLyChamBai;
import com.example.detaigiuaki.model.GiaoVien;
import com.example.detaigiuaki.model.PhieuChamBai;

import java.util.ArrayList;

public class UpDatePhieuChamBai extends AppCompatActivity {
    Spinner spUpdateTenGV;
    ArrayList<GiaoVien> listGV= new ArrayList<GiaoVien>();
    DatePicker dpUpdateNgayGiao;
    TextView txtUpdateMaGV;
    Button btnCapNhat;
    Animation scaleUp, scaleDown;
    int image[] = {R.drawable.iconsteacher,R.drawable.iconsteacher,R.drawable.iconsteacher,R.drawable.iconsteacher,R.drawable.iconsteacher,R.drawable.iconsteacher};

    PhieuChamBai pcbUpdate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             pcbUpdate = (PhieuChamBai) extras.getSerializable("PhieuChamBai");
        }

        setContentView(R.layout.activity_update_phieuchambai);
        setControl();
        setEvent();
    }
    void setControl(){
        spUpdateTenGV=(Spinner) findViewById(R.id.spUpdateTenGV);
        dpUpdateNgayGiao= findViewById(R.id.dpUpdateNgayGiao);
        txtUpdateMaGV=findViewById(R.id.txtUpdateMaGV);
        btnCapNhat=findViewById(R.id.btnCapNhat);

        scaleUp = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);
    }
    void setEvent()
    {
        khoiTao();
        spUpdateTenGV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txtUpdateMaGV.setText(String.valueOf(listGV.get(i).getId()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soPhieu= pcbUpdate.getSoPhieu();
                String maGV= (String) txtUpdateMaGV.getText();
                String tenGv= spUpdateTenGV.getSelectedItem().toString();
                String ngayGiao="";
                int day = dpUpdateNgayGiao.getDayOfMonth();
                int month = dpUpdateNgayGiao.getMonth() + 1;
                int year = dpUpdateNgayGiao.getYear();
                ngayGiao =String.valueOf(day)+"/"+String.valueOf(month)+"/"+String.valueOf(year);

                try {
                    DBQuanLyChamBai dbQuanLyChamBai= new DBQuanLyChamBai(UpDatePhieuChamBai.this);
                    dbQuanLyChamBai.updatePhieuChamBai(new PhieuChamBai(soPhieu,maGV,tenGv,ngayGiao));

                    Intent intent = new Intent(UpDatePhieuChamBai.this, QLPhieuChamBaiActivity.class);
                    intent.putExtra("message", "Cập Nhật PCB Thành Công !");
                    startActivity(intent);
                }
                catch (Exception ex)
                {
                    Toast toast =  Toast.makeText(UpDatePhieuChamBai.this, "Thêm mới CTCB Thất Bại!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 20, 30);
                    toast.show();
                }
            }
        });
        btnCapNhat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == motionEvent.ACTION_UP){
                    btnCapNhat.startAnimation(scaleUp);

                } else if(motionEvent.getAction() == motionEvent.ACTION_DOWN){
                    btnCapNhat.startAnimation(scaleDown);

                }
                return false;
            }
        });

    }
    void khoiTao()
    {
        DBQuanLyChamBai dbQuanLyChamBai= new DBQuanLyChamBai(UpDatePhieuChamBai.this);
        listGV=dbQuanLyChamBai.getDSGiaoVien();

        try {
            System.out.println(pcbUpdate.getNgayGiao());
            String[] parts = pcbUpdate.getNgayGiao().trim().split("/");
            dpUpdateNgayGiao.updateDate(Integer.valueOf(parts[2]),Integer.valueOf(parts[1]),Integer.valueOf(parts[0]));
        }
        catch(Exception ex)
        {
            Toast.makeText(getApplicationContext(), "Lỗi setNgayGiao UpDatePCB", Toast.LENGTH_LONG).show();
        }
        ArrayList<String> listStrGV= new ArrayList<>();
        String magv= pcbUpdate.getMaGV();
        int vt=-1;
        for(int i=0;i<listGV.size();i++){
            listStrGV.add(listGV.get(i).getName());
            if(listGV.get(i).getId().equals(magv))
            {
                vt= i;
            }
        }
//        ArrayAdapter adapter= new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,listStrGV);
//        spUpdateTenGV.setAdapter(adapter);

        BaseApdapterSpinner adapter= new BaseApdapterSpinner(this,image,listStrGV);
        spUpdateTenGV.setAdapter(adapter);
        if(vt>=0)
        {
            spUpdateTenGV.setSelection(vt);
        }

    }
}
