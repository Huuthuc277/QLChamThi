package com.example.detaigiuaki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.detaigiuaki.R;
import com.example.detaigiuaki.adapter.AdapterGiaoVien;
import com.example.detaigiuaki.adapter.AdapterNguoiDung;
import com.example.detaigiuaki.database.DBQuanLyChamBai;
import com.example.detaigiuaki.model.NguoiDung;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;

public class QLNguoiDungActivity extends AppCompatActivity {
    ListView lvDSND;
    ArrayList<NguoiDung> list;
    AdapterNguoiDung adapterNguoiDung;
    SearchView svDSND;

    Animation scaleUp, scaleDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlnguoidung);
        setControl();
        setEvent();
    }

    private void setEvent() {
        khoiTao();
        adapterNguoiDung = new AdapterNguoiDung(this, R.layout.layout_item_nguoidung, this.list);
        lvDSND.setAdapter(adapterNguoiDung);
        svDSND.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(s.trim().equals("")==false)
                {
                    DBQuanLyChamBai db = new DBQuanLyChamBai(QLNguoiDungActivity.this);
                    String sql="SELECT TENDANGNHAP,MATKHAU,ROLE,NguoiDung.MAGV,GIAOVIEN.HOTENGV FROM NguoiDung,GIAOVIEN Where GIAOVIEN.MAGV=NguoiDung.MAGV AND \n" +
                            "(instr(upper(HOTENGV), upper('"+s+"'))!=0 or instr(upper(TENDANGNHAP), upper('"+s+"'))!=0)"+
                            "ORDER BY HOTENGV DESC";
                    list = db.getDSNguoiDung(sql);
                    AdapterNguoiDung adapter = new AdapterNguoiDung(QLNguoiDungActivity.this,R.layout.layout_item_nguoidung,list);
                    lvDSND.setAdapter(adapter);
                }
                else
                {
                    DBQuanLyChamBai db = new DBQuanLyChamBai(QLNguoiDungActivity.this);
                    list = db.getDSNguoiDung();

                    AdapterNguoiDung adapter = new AdapterNguoiDung(QLNguoiDungActivity.this,R.layout.layout_item_nguoidung,list);
                    lvDSND.setAdapter(adapter);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.trim().equals("")==false)
                {
                    DBQuanLyChamBai db = new DBQuanLyChamBai(QLNguoiDungActivity.this);
                    String sql="SELECT TENDANGNHAP,MATKHAU,ROLE,NguoiDung.MAGV,GIAOVIEN.HOTENGV FROM NguoiDung,GIAOVIEN Where GIAOVIEN.MAGV=NguoiDung.MAGV AND \n" +
                            "(instr(upper(HOTENGV), upper('"+s+"'))!=0 or instr(upper(TENDANGNHAP), upper('"+s+"'))!=0)"+
                            "ORDER BY HOTENGV DESC";
                    list = db.getDSNguoiDung(sql);
                    System.out.println(list.size());
                    AdapterNguoiDung adapter = new AdapterNguoiDung(QLNguoiDungActivity.this,R.layout.layout_item_nguoidung,list);
                    lvDSND.setAdapter(adapter);
                }
                else
                {
                    DBQuanLyChamBai db = new DBQuanLyChamBai(QLNguoiDungActivity.this);
                    list = db.getDSNguoiDung();
                    AdapterNguoiDung adapter = new AdapterNguoiDung(QLNguoiDungActivity.this,R.layout.layout_item_nguoidung,list);
                    lvDSND.setAdapter(adapter);
                }
                return false;
            }
        });
    }
    private void khoiTao() {
        DBQuanLyChamBai db = new DBQuanLyChamBai(this);
        list = db.getDSNguoiDung();
    }

    private void setControl() {
        lvDSND = findViewById(R.id.lvDSND);
        svDSND = findViewById(R.id.svDSND);

        scaleUp = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);
    }
}
