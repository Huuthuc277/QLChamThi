package com.example.detaigiuaki.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.detaigiuaki.R;
import com.example.detaigiuaki.adapter.AdapterGiaoVien;
import com.example.detaigiuaki.database.DBQuanLyChamBai;
import com.example.detaigiuaki.model.GiaoVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class QLGiaoVienActivity extends AppCompatActivity {
    ListView lvDSGV;
    FloatingActionButton fabAddGV;
    ArrayList<GiaoVien> list;
    AdapterGiaoVien adapterGiaoVien;
    SearchView svDSGV;

    Animation scaleUp, scaleDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlgiao_vien);
        setControl();
        setEvent();
    }

    private void setEvent() {
        khoiTao();
        adapterGiaoVien = new AdapterGiaoVien(this, R.layout.layout_item_giaovien, this.list);
        lvDSGV.setAdapter(adapterGiaoVien);
        lvDSGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ThemGiaoVienActivity.class);
                intent.putExtra("flag", "edit");
                intent.putExtra("GiaoVien", list.get(i));
                startActivity(intent);
            }
        });
        fabAddGV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ThemGiaoVienActivity.class);
                intent.putExtra("flag", "add");
                startActivity(intent);
            }
        });
        svDSGV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(s.trim().equals("")==false)
                {
                    DBQuanLyChamBai db = new DBQuanLyChamBai(QLGiaoVienActivity.this);
                    String sql="select * \n" +
                            "from GiaoVien \n" +
                            "WHERE ( instr(upper(HOTENGV), upper('"+s+"'))!=0 or (instr(upper(MAGV), upper('"+s+"'))!=0)  or (instr(upper(SODT), upper('"+s+"'))!=0))"+
                            "ORDER BY HOTENGV DESC";
                    list = db.getDSGiaoVien(sql);
                    AdapterGiaoVien adapter = new AdapterGiaoVien(QLGiaoVienActivity.this,R.layout.layout_item_giaovien,list);
                    lvDSGV.setAdapter(adapter);
                }
                else
                {
                    DBQuanLyChamBai db = new DBQuanLyChamBai(QLGiaoVienActivity.this);
                    list = db.getDSGiaoVien();
                    AdapterGiaoVien adapter = new AdapterGiaoVien(QLGiaoVienActivity.this,R.layout.layout_item_giaovien,list);
                    lvDSGV.setAdapter(adapter);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.trim().equals("")==false)
                {
                    DBQuanLyChamBai db = new DBQuanLyChamBai(QLGiaoVienActivity.this);
                    String sql="select * \n" +
                            "from GiaoVien \n" +
                            "WHERE ( instr(upper(HOTENGV), upper('"+s+"'))!=0 or (instr(upper(MAGV), upper('"+s+"'))!=0)  or (instr(upper(SODT), upper('"+s+"'))!=0))"+
                            "ORDER BY HOTENGV DESC";
                    list= db.getDSGiaoVien(sql);
                    AdapterGiaoVien adapter = new AdapterGiaoVien(QLGiaoVienActivity.this,R.layout.layout_item_giaovien,list);
                    lvDSGV.setAdapter(adapter);
                }
                else
                {
                    DBQuanLyChamBai db = new DBQuanLyChamBai(QLGiaoVienActivity.this);
                    list = db.getDSGiaoVien();
                    AdapterGiaoVien adapter = new AdapterGiaoVien(QLGiaoVienActivity.this,R.layout.layout_item_giaovien,list);
                    lvDSGV.setAdapter(adapter);
                }
                return false;
            }
        });
        fabAddGV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == motionEvent.ACTION_UP){
                    fabAddGV.startAnimation(scaleUp);

                } else if(motionEvent.getAction() == motionEvent.ACTION_DOWN){
                    fabAddGV.startAnimation(scaleDown);

                }
                return false;
            }
        });
    }

    private void khoiTao() {
        DBQuanLyChamBai db = new DBQuanLyChamBai(this);
        list = db.getDSGiaoVien();
    }

    private void setControl() {
        lvDSGV = findViewById(R.id.lvDSGV);
        fabAddGV = findViewById(R.id.fabAddGV);
        svDSGV = findViewById(R.id.svDSGV);

        scaleUp = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);
    }
}