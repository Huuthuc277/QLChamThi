package com.example.detaigiuaki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.detaigiuaki.utils.JavaMailAPI;
import com.example.detaigiuaki.R;
import com.example.detaigiuaki.database.DBQuanLyChamBai;
import com.example.detaigiuaki.model.MonHoc;
import com.example.detaigiuaki.model.ThongTinMail;

import java.util.ArrayList;

public class GuiMailActivity extends AppCompatActivity {
    Spinner spTenMH;
    DatePicker dpTuNgay;
    DatePicker dpDenNgay;
    Button btnGuiMail;
    EditText etGmail;
    ArrayList<MonHoc> listMH= new ArrayList<MonHoc>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guimail);
        setControl();
        setEvent();
    }

    private void setEvent() {
        khoiTao();
        ArrayList<String> listStrMH= new ArrayList<>();
        for(MonHoc mh:listMH)
        {
            listStrMH.add(mh.getName());
        }
        ArrayAdapter adapter= new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,listStrMH);
        spTenMH.setAdapter(adapter);
        btnGuiMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("onclick");
                String mail= etGmail.getText().toString().trim();
                String tenMH= spTenMH.getSelectedItem().toString();
                String maMH= listMH.get(spTenMH.getSelectedItemPosition()).getId();
                String tuNgay="";
                int day1 = dpTuNgay.getDayOfMonth();
                int month1 = dpTuNgay.getMonth() + 1;
                int year1 = dpTuNgay.getYear();
                tuNgay =String.valueOf(day1)+"/"+String.valueOf(month1)+"/"+String.valueOf(year1);

                String denNgay="";
                int day2 = dpDenNgay.getDayOfMonth();
                int month2 = dpDenNgay.getMonth() + 1;
                int year2 = dpDenNgay.getYear();
                denNgay =String.valueOf(day2)+"/"+String.valueOf(month2)+"/"+String.valueOf(year2);
                //WHERE NGAYGIAO BETWEEN '"+tuNgay+"' AND '"+denNgay+"'
                String query= "SELECT PCB.SOPHIEU, GiaoVien.HOTENGV,PCB.NGAYGIAO,ThongTinChamBai.SOBAI\n" +
                        "FROM GiaoVien,(SELECT * FROM PhieuChamBai ) AS PCB,\n" +
                        "ThongTinChamBai,(SELECT MAMH,TENMH FROM MONHOC WHERE MAMH='"+maMH+"') AS MH\n" +
                        "WHERE GIAOVIEN.MAGV=PCB.MAGV AND PCB.SOPHIEU=ThongTinChamBai.SOPHIEU AND ThongTinChamBai.MAMH=MH.MAMH";
                System.out.println("trước khi vào try catch");
                try {
                    DBQuanLyChamBai dbQuanLyChamBai= new DBQuanLyChamBai(GuiMailActivity.this);
                    ArrayList<ThongTinMail> ttMail= dbQuanLyChamBai.readThongTinMail(query);
                    System.out.println("đọc thông tin song");
                    System.out.println(maMH);
                    System.out.println();
                    System.out.println();
                    System.out.println(ttMail.size());
                    JavaMailAPI javaMailAPI = new JavaMailAPI(getApplicationContext(),etGmail.getText().toString(),"THÔNG TIN CHẤM BÀI MÔN HỌC "+tenMH ,ttMail);

                    javaMailAPI.execute();
                    for(ThongTinMail a: ttMail)
                    {
                        System.out.println(a.getSoPhieu()+"----"+a.getHoTenGV()+"----"+a.getNgayGiao()+"----"+a.getSoBai());
                    }

//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(intent);
                    Toast toast =  Toast.makeText(getApplicationContext(), "GỬI MAIL THÀNH CÔNG!", Toast.LENGTH_SHORT);
                    // toast.setGravity(Gravity.TOP | Gravity.CENTER, 20, 30);
                    toast.show();
                    finish();

                }
                catch (Exception ex)
                {
                    Toast toast =  Toast.makeText(getApplicationContext(), "Select Gửi mail Thất Bại!", Toast.LENGTH_SHORT);
                   // toast.setGravity(Gravity.TOP | Gravity.CENTER, 20, 30);
                    toast.show();
                }
            }
        });
    }

    private void setControl() {
        spTenMH=findViewById(R.id.spTenMonHoc);
        dpTuNgay=findViewById(R.id.dpTuNgay);
        dpDenNgay=findViewById(R.id.dpDenNgay);
        btnGuiMail= findViewById(R.id.btnGuiMail);
        etGmail=findViewById(R.id.etGmail);
    }
    void khoiTao()
    {
        DBQuanLyChamBai dbQuanLyChamBai= new DBQuanLyChamBai(GuiMailActivity.this);
        listMH=dbQuanLyChamBai.ReadMonHoc();
    }
}
