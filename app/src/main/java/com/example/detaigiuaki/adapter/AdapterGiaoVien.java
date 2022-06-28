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

import java.util.ArrayList;

public class AdapterGiaoVien extends ArrayAdapter<GiaoVien> {
    Context context;
    int resource;
    ArrayList<GiaoVien> list;
    public AdapterGiaoVien(@NonNull Context context, int resource, @NonNull ArrayList<GiaoVien> list) {
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
        TextView tvTenGV = convertView.findViewById(R.id.tvTenGV);
        TextView tvSoDT = convertView.findViewById(R.id.tvSoDT);
        ImageView ivDelete = convertView.findViewById(R.id.ivDeleteGV);

        GiaoVien gv = list.get(position);
        tvTenGV.setText("Tên giáo viên: " + gv.getName());
        tvSoDT.setText("Số ĐT: " + gv.getPhone());
        tvTenGV.setTextColor(Color.rgb(184 ,134 ,11));
        tvSoDT.setTextColor(Color.rgb(184 ,134 ,11));
        tvTenGV.setTypeface(null, Typeface.BOLD);
        tvSoDT.setTypeface(null, Typeface.BOLD);


        tvSoDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Xác nhận xóa " + gv.getName()).setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBQuanLyChamBai db = new DBQuanLyChamBai(context);
                        db.DeleteGiaoVien(gv);
                        db.DeleteNguoiDung(gv.getId());
                    }
                }).show();
            }
        });

        return convertView;
    }
}
