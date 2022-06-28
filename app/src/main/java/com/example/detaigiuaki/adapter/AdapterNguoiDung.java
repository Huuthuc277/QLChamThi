package com.example.detaigiuaki.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.detaigiuaki.R;
import com.example.detaigiuaki.database.DBQuanLyChamBai;
import com.example.detaigiuaki.model.GiaoVien;
import com.example.detaigiuaki.model.NguoiDung;

import java.util.ArrayList;

public class AdapterNguoiDung extends ArrayAdapter<NguoiDung> {
    Context context;
    int resource;
    ArrayList<NguoiDung> list;
    public AdapterNguoiDung(@NonNull Context context, int resource, @NonNull ArrayList<NguoiDung> list) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView tvTaiKhoan = convertView.findViewById(R.id.tvTaiKhoan);
        TextView tvTenGV = convertView.findViewById(R.id.tvTenGVND);
        TextView tvRole = convertView.findViewById(R.id.tvRole);
       // ImageView ivDelete = convertView.findViewById(R.id.ivDeleteND);

        System.out.println("vitri "+ position);
        NguoiDung nd = list.get(position);
        System.out.println(nd.getMaGV());
        System.out.println(nd.getTenGV());
        tvTaiKhoan.setText("Tài Khoản: " + nd.getUserName());
        tvTenGV.setText("Tên Giáo Viên: " + nd.getTenGV());

        tvTenGV.setTextColor(Color.rgb(184 ,134 ,11));

        tvTaiKhoan.setTextColor(Color.rgb(184 ,134 ,11));
        tvTenGV.setTypeface(null, Typeface.BOLD);
        tvTaiKhoan.setTypeface(null, Typeface.BOLD);
        tvRole.setText("Vai Trò: "+ nd.getRole());
        tvRole.setTextColor(Color.rgb(184 ,134 ,11));
        tvRole.setTypeface(null, Typeface.BOLD);
//        ivDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        return convertView;
    }
}
