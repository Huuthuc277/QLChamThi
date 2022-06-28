package com.example.detaigiuaki.adapter;

import android.content.Context;
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
import com.example.detaigiuaki.model.ChucNang;

import java.util.ArrayList;

public class AdapterChucNang extends ArrayAdapter<ChucNang> {
    Context context;
    int resource;
    ArrayList<ChucNang> list;
    public AdapterChucNang(@NonNull Context context, int resource, @NonNull ArrayList<ChucNang> list) {
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
        ImageView ivHinhAnhFunc = convertView.findViewById(R.id.ivHinhAnhFunc);
        TextView tvTenFunc = convertView.findViewById(R.id.tvTenFunc);
        ChucNang chucNang = list.get(position);
        tvTenFunc.setText(chucNang.getName());
        tvTenFunc.setTextColor(Color.rgb(184 ,134 ,11));
        tvTenFunc.setTextSize(18);
        tvTenFunc.setBackgroundColor(Color.rgb(180 ,238, 180));
        tvTenFunc.setPadding(18,28,8,28);
        tvTenFunc.setTypeface(null, Typeface.BOLD);

        ivHinhAnhFunc.setBackgroundResource(chucNang.getIcon());
        ivHinhAnhFunc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        return convertView;
    }
}
