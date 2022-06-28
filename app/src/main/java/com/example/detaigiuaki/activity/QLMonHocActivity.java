package com.example.detaigiuaki.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.detaigiuaki.R;
import com.example.detaigiuaki.adapter.AdapterMonHoc;
import com.example.detaigiuaki.database.DBQuanLyChamBai;
import com.example.detaigiuaki.model.MonHoc;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class QLMonHocActivity extends AppCompatActivity {
    SearchView svDSMH;
    FloatingActionButton fabAddMH;
    ListView lvDSMH;
    ArrayList<MonHoc> list = new ArrayList<>();
    AdapterMonHoc adapterMonHoc;
    DBQuanLyChamBai db;

    Animation scaleUp, scaleDown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlmon_hoc);
        setControl();
        setEvent();
    }

    private void setEvent() {
        khoiTao();
        adapterMonHoc = new AdapterMonHoc(this, R.layout.layout_item_monhoc, this.list);
        lvDSMH.setAdapter(adapterMonHoc);
        fabAddMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MonHocActivity.class);
                intent.putExtra("flag", "add");
                startActivity(intent);
            }
        });
        lvDSMH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MonHocActivity.class);
                intent.putExtra("maMH", list.get(i).getId());
                intent.putExtra("tenMH", list.get(i).getName());
                intent.putExtra("chiPhi", String.valueOf(list.get(i).getCost()));
                intent.putExtra("flag", "edit");
                startActivity(intent);
            }
        });
        svDSMH.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.trim().equals("")==false)
                {
                    DBQuanLyChamBai db = new DBQuanLyChamBai(QLMonHocActivity.this);
                    String sql="SELECT * FROM MonHoc \n" +
                            "WHERE ( instr(upper(MaMH), upper('"+query+"'))!=0 or (instr(upper(TenMH), upper('"+query+"'))!=0))"+
                            "ORDER BY MaMH DESC";
                    list = db.ReadMonHoc(sql);
                    AdapterMonHoc adapter = new AdapterMonHoc(QLMonHocActivity.this,R.layout.layout_item_monhoc,list);
                    lvDSMH.setAdapter(adapter);
                }
                else
                {
                    DBQuanLyChamBai db = new DBQuanLyChamBai(QLMonHocActivity.this);
                    list = db.ReadMonHoc();
                    AdapterMonHoc adapter = new AdapterMonHoc(QLMonHocActivity.this,R.layout.layout_item_monhoc,list);
                    lvDSMH.setAdapter(adapter);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.trim().equals("")==false)
                {
                    DBQuanLyChamBai db = new DBQuanLyChamBai(QLMonHocActivity.this);
                    String sql="SELECT * FROM MonHoc \n" +
                            "WHERE ( instr(upper(MaMH), upper('"+s+"'))!=0 or (instr(upper(TenMH), upper('"+s+"'))!=0))"+
                            "ORDER BY MaMH DESC";
                    list = db.ReadMonHoc(sql);
                    AdapterMonHoc adapter = new AdapterMonHoc(QLMonHocActivity.this,R.layout.layout_item_monhoc,list);
                    lvDSMH.setAdapter(adapter);
                }
                else
                {
                    DBQuanLyChamBai db = new DBQuanLyChamBai(QLMonHocActivity.this);
                    list = db.ReadMonHoc();
                    AdapterMonHoc adapter = new AdapterMonHoc(QLMonHocActivity.this,R.layout.layout_item_monhoc,list);
                    lvDSMH.setAdapter(adapter);
                }
                return false;
            }
        });
        fabAddMH.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == motionEvent.ACTION_UP){
                    fabAddMH.startAnimation(scaleUp);

                } else if(motionEvent.getAction() == motionEvent.ACTION_DOWN){
                    fabAddMH.startAnimation(scaleDown);

                }
                return false;
            }
        });
    }

    private void khoiTao() {
        db = new DBQuanLyChamBai(this);
        list = db.ReadMonHoc();
    }

    private void setControl() {
        svDSMH = findViewById(R.id.svDSMH);
        lvDSMH = findViewById(R.id.lvDSMH);
        fabAddMH = findViewById(R.id.fabAddMH);
        svDSMH = findViewById(R.id.svDSMH);
        scaleUp = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);
    }
}