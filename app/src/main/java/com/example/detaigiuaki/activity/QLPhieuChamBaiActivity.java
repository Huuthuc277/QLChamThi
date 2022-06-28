package com.example.detaigiuaki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.detaigiuaki.R;
import com.example.detaigiuaki.adapter.AdapterPhieuChamBai;
import com.example.detaigiuaki.database.DBQuanLyChamBai;
import com.example.detaigiuaki.model.GiaoVien;
import com.example.detaigiuaki.model.NguoiDung;
import com.example.detaigiuaki.model.PhieuChamBai;

import java.util.ArrayList;

public class QLPhieuChamBaiActivity extends AppCompatActivity {
    ListView lvDS;
    SearchView svDSPCB;
    ArrayList<PhieuChamBai> data = new ArrayList<>();
    ImageButton btnThemPCB ;
    Animation scaleUp, scaleDown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phieuchambai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setcontrol();
        setEvent();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value="";
           value += extras.getString("message");
            Toast toast= Toast.makeText(getApplicationContext(), value, Toast.LENGTH_LONG);
            toast.show();
        }

    }
    private void khoiTao()
    {
        DBQuanLyChamBai db = new DBQuanLyChamBai(this);
        data = db.readPhieuChamBai();

    }
    private void setcontrol()
    {
        lvDS=findViewById(R.id.lvPhieuChamBai);
        btnThemPCB= findViewById(R.id.btnThemPhieuChamBai);
        svDSPCB=(SearchView) findViewById(R.id.svDSPCB);
        scaleUp = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);
    }
    private void setEvent(){
        khoiTao();
        AdapterPhieuChamBai adapter = new AdapterPhieuChamBai(this,R.layout.layout_itemphieuchambai,data);
        lvDS.setAdapter(adapter);
        btnThemPCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QLPhieuChamBaiActivity.this, PhieuChamBaiActivity.class);
                startActivity(intent);
            }

        });

        lvDS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ///HIỆN THÔNG TIN TTCB
                Intent intent = new Intent(QLPhieuChamBaiActivity.this, TTChamBaiActivity.class);
                intent.putExtra("PhieuChamBai",data.get(i));
                startActivity(intent);

            }
        });
        svDSPCB.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(s.trim().equals("")==false)
                {
                    DBQuanLyChamBai db = new DBQuanLyChamBai(QLPhieuChamBaiActivity.this);
                    String sql="select SOPHIEU,NGAYGIAO,GiaoVien.MAGV,HOTENGV \n" +
                            "from PhieuChamBai,GiaoVien \n" +
                            "WHERE PhieuChamBai.MAGV=GiaoVien.MAGV and" +
                            "( instr(upper(HOTENGV), upper('"+s+"'))!=0 or (instr(upper(NGAYGIAO), upper('"+s+"'))!=0))"+
                            "ORDER BY SoPhieu DESC";
                    data = db.readPhieuChamBai(sql);
                    AdapterPhieuChamBai adapter = new AdapterPhieuChamBai(QLPhieuChamBaiActivity.this,R.layout.layout_itemphieuchambai,data);
                    lvDS.setAdapter(adapter);
                }
                else
                {
                    DBQuanLyChamBai db = new DBQuanLyChamBai(QLPhieuChamBaiActivity.this);
                    data = db.readPhieuChamBai();
                    AdapterPhieuChamBai adapter = new AdapterPhieuChamBai(QLPhieuChamBaiActivity.this,R.layout.layout_itemphieuchambai,data);
                    lvDS.setAdapter(adapter);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.trim().equals("")==false)
                {
                    DBQuanLyChamBai db = new DBQuanLyChamBai(QLPhieuChamBaiActivity.this);
                    String sql="select SOPHIEU,NGAYGIAO,GiaoVien.MAGV,HOTENGV \n" +
                            "from PhieuChamBai,GiaoVien \n" +
                            "WHERE PhieuChamBai.MAGV=GiaoVien.MAGV and" +
                            "( instr(upper(HOTENGV), upper('"+s+"'))!=0 or (instr(upper(NGAYGIAO), upper('"+s+"'))!=0))"+
                            "ORDER BY SoPhieu DESC";
                    data = db.readPhieuChamBai(sql);
                    AdapterPhieuChamBai adapter = new AdapterPhieuChamBai(QLPhieuChamBaiActivity.this,R.layout.layout_itemphieuchambai,data);
                    lvDS.setAdapter(adapter);
                }
                else
                {
                    DBQuanLyChamBai db = new DBQuanLyChamBai(QLPhieuChamBaiActivity.this);
                    data = db.readPhieuChamBai();
                    AdapterPhieuChamBai adapter = new AdapterPhieuChamBai(QLPhieuChamBaiActivity.this,R.layout.layout_itemphieuchambai,data);
                    lvDS.setAdapter(adapter);
                }
                return false;
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
