package com.example.detaigiuaki.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.detaigiuaki.R;
import com.example.detaigiuaki.activity.TTChamBaiActivity;
import com.example.detaigiuaki.activity.ThemUpdateTTCBActitity;
import com.example.detaigiuaki.database.DBQuanLyChamBai;
import com.example.detaigiuaki.model.PhieuChamBai;
import com.example.detaigiuaki.model.ThongTinChamBai;

import java.util.ArrayList;

public class AdapterTTChamBai extends ArrayAdapter<ThongTinChamBai> {
    Context context;
    int resource;
    ArrayList<ThongTinChamBai> list;
    PhieuChamBai pcbCha;

    public AdapterTTChamBai(@NonNull Context context, int resource, @NonNull ArrayList<ThongTinChamBai> list, PhieuChamBai pcb) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
        this.pcbCha=pcb;
    }

    public AdapterTTChamBai(TTChamBaiActivity context, int resource, ArrayList<ThongTinChamBai> list) {
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
        TextView tvSoPhieu = convertView.findViewById(R.id.tvSoPhieu);
        TextView tvMaMHCB = convertView.findViewById(R.id.tvMaMHCB);
        TextView tvSoBai = convertView.findViewById(R.id.tvSoBai);
        ImageView ivDelete = convertView.findViewById(R.id.ivDeleteTTCB);

        ThongTinChamBai ttcb = list.get(position);
        tvSoPhieu.setText("Số phiếu: " + String.valueOf(ttcb.getSoPhieu()));
        tvMaMHCB.setText("Tên môn học: " + ttcb.getTenMH());
        tvSoBai.setText("Số bài: " + String.valueOf(ttcb.getSoBai()));

        tvSoPhieu.setTextColor(Color.rgb(253, 91 ,78));
        tvMaMHCB.setTextColor(Color.rgb(40 ,135 ,217));
        tvSoBai.setTextColor(Color.rgb(28 ,182 ,32));
        tvSoPhieu.setTypeface(null, Typeface.BOLD);
        tvMaMHCB.setTypeface(null, Typeface.BOLD);
        tvSoBai.setTypeface(null, Typeface.BOLD);
        tvSoPhieu.setTypeface(null, Typeface.BOLD);


        System.out.println("apdertTTCBC");

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Xác nhận xóa Chấm bài môn : " + ttcb.getTenMH()).setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBQuanLyChamBai db = new DBQuanLyChamBai(context);
                        db.DeleteTTChamBai(ttcb);
                        Intent intent = new Intent(context, TTChamBaiActivity.class);
                        intent.putExtra("PhieuChamBai",pcbCha);
                        context.startActivity(intent);

                    }
                }).show();
            }
        });

        return convertView;
    }
}
