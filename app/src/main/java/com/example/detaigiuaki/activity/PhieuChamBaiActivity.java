package com.example.detaigiuaki.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.detaigiuaki.R;
import com.example.detaigiuaki.adapter.BaseApdapterSpinner;
import com.example.detaigiuaki.database.DBQuanLyChamBai;
import com.example.detaigiuaki.model.GiaoVien;
import com.example.detaigiuaki.model.NguoiDung;
import com.example.detaigiuaki.model.PhieuChamBai;

import java.util.ArrayList;

public class PhieuChamBaiActivity extends AppCompatActivity {
    Spinner spTenGV;
    ArrayList<GiaoVien> listGV= new ArrayList<GiaoVien>();
    DatePicker dpNgayGiao;
    TextView txtMaGV;
    Button btnThemPCB;

    Animation scaleUp, scaleDown;
    int image[] = {R.drawable.iconsteacher,R.drawable.iconsteacher,R.drawable.iconsteacher,R.drawable.iconsteacher,R.drawable.iconsteacher,R.drawable.iconsteacher};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_phieuchambai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setControl();
        setEvent();

    }
    void setControl(){
        spTenGV=(Spinner) findViewById(R.id.spTenGiaoVien);
        dpNgayGiao= findViewById(R.id.dpngayGiao);
        txtMaGV=findViewById(R.id.txtMaGV);
        btnThemPCB=findViewById(R.id.btnThemPCB);
        scaleUp = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
            finish();
            break;
        }

        return super.onOptionsItemSelected(item);
    }

    void setEvent()
    {
        khoiTao();

        ArrayList<String> listStrGV= new ArrayList<>();
        for(GiaoVien sv:listGV)
        {
            listStrGV.add(sv.getName());
        }
       // ArrayAdapter adapter= new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,listStrGV);
       // spTenGV.setAdapter(adapter);

        BaseApdapterSpinner adapter= new BaseApdapterSpinner(this,image,listStrGV);
        spTenGV.setAdapter(adapter);

        spTenGV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txtMaGV.setText(String.valueOf(listGV.get(i).getId()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnThemPCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Số phiếu chấm bài
                String maGV= (String) txtMaGV.getText();
                String tenGv=listStrGV.get(spTenGV.getSelectedItemPosition()).toString() ;
                String ngayGiao="";
                int day = dpNgayGiao.getDayOfMonth();
                int month = dpNgayGiao.getMonth() + 1;
                int year = dpNgayGiao.getYear();
                ngayGiao =String.valueOf(day)+"/"+String.valueOf(month)+"/"+String.valueOf(year);

                try {
                    DBQuanLyChamBai dbQuanLyChamBai= new DBQuanLyChamBai(PhieuChamBaiActivity.this);
                    dbQuanLyChamBai.AddPhieuChamBai(new PhieuChamBai(maGV,tenGv,ngayGiao));

                    Intent intent = new Intent(PhieuChamBaiActivity.this, QLPhieuChamBaiActivity.class);
                    intent.putExtra("message", "Thêm Mới CTCB Thành Công");
                    startActivity(intent);
                }
                catch (Exception ex)
                {
                    Toast toast =  Toast.makeText(PhieuChamBaiActivity.this, "Thêm mới CTCB Thất Bại!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 20, 30);
                    toast.show();
                }

            }
        });

        btnThemPCB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == motionEvent.ACTION_UP){
                    btnThemPCB.startAnimation(scaleUp);

                } else if(motionEvent.getAction() == motionEvent.ACTION_DOWN){
                    btnThemPCB.startAnimation(scaleDown);

                }
                return false;
            }
        });


    }
    void khoiTao()
    {
        DBQuanLyChamBai dbQuanLyChamBai= new DBQuanLyChamBai(PhieuChamBaiActivity.this);
        listGV=dbQuanLyChamBai.getDSGiaoVien();


    }
}