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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.detaigiuaki.R;
import com.example.detaigiuaki.database.DBQuanLyChamBai;
import com.example.detaigiuaki.model.MonHoc;

import java.util.ArrayList;

public class AdapterMonHoc extends ArrayAdapter<MonHoc> {
    Context context;
    int resource;
    ArrayList<MonHoc> list;

    public AdapterMonHoc(@NonNull Context context, int resource, @NonNull ArrayList<MonHoc> list) {
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
        TextView tvMaMH= convertView.findViewById(R.id.tvMaMH);
        TextView tvTenMH= convertView.findViewById(R.id.tvTenMH);
        TextView tvChiPhi= convertView.findViewById(R.id.tvChiPhi);
        ImageView ivDelete = convertView.findViewById(R.id.ivDeleteMH);

        MonHoc mh = list.get(position);
        tvMaMH.setText("Mã môn học: " + mh.getId());
        tvTenMH.setText("Tên môn học: " + mh.getName());
        tvChiPhi.setText("Chi phí: " + String.valueOf(mh.getCost()));
        tvMaMH.setTextColor(Color.rgb(253, 91 ,78));
        tvTenMH.setTextColor(Color.rgb(40 ,135 ,217));
        tvChiPhi.setTextColor(Color.rgb(28 ,182 ,32));
        tvChiPhi.setTypeface(null, Typeface.BOLD);
        tvTenMH.setTypeface(null, Typeface.BOLD);
        tvMaMH.setTypeface(null, Typeface.BOLD);

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Xác nhận xóa Môn Học: " + mh.getName()).setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBQuanLyChamBai db = new DBQuanLyChamBai(context);
                        db.DeleteMonHoc(mh);
                    }
                }).show();
            }
        });
        return convertView;
    }
}
