package com.example.detaigiuaki.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.detaigiuaki.R;
import com.example.detaigiuaki.activity.QLPhieuChamBaiActivity;
import com.example.detaigiuaki.activity.UpDatePhieuChamBai;
import com.example.detaigiuaki.database.DBQuanLyChamBai;
import com.example.detaigiuaki.model.PhieuChamBai;

import java.util.ArrayList;

public class AdapterPhieuChamBai extends ArrayAdapter<PhieuChamBai> {
    Context context;
    int resource;
    ArrayList<PhieuChamBai> list;


    public AdapterPhieuChamBai(@NonNull Context context, int resource, ArrayList<PhieuChamBai> list) {
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
        TextView tvSoPhieu = convertView.findViewById(R.id.tvSoPhieuCB);
        TextView tvTenGiaoVien = convertView.findViewById(R.id.tvTenGV);
        TextView tvNgayGiao = convertView.findViewById(R.id.tvNgayGiao);
        ImageView btnXoa = convertView.findViewById(R.id.ivDeletePhieuChamBai);
        tvSoPhieu.setTypeface(null, Typeface.BOLD);
        tvTenGiaoVien.setTypeface(null, Typeface.BOLD);
        tvNgayGiao.setTypeface(null, Typeface.BOLD);
        tvSoPhieu.setTextColor(Color.rgb(184 ,134 ,11));
        tvTenGiaoVien.setTextColor(Color.rgb(184 ,134 ,11));
        tvNgayGiao.setTextColor(Color.rgb(184 ,134 ,11));




        PhieuChamBai phieuChamBai = list.get(position);
        tvSoPhieu.setText(String.valueOf(phieuChamBai.getSoPhieu()));
        tvTenGiaoVien.setText(phieuChamBai.getTenGV().toString());
        tvNgayGiao.setText(phieuChamBai.getNgayGiao().toString());

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, btnXoa);
                MenuInflater inflater = popup.getMenuInflater();
                popup.setForceShowIcon(true);
                inflater.inflate(R.menu.popupmenu_pcb, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.itSuaPCB:
                                try {
                                    Intent intent = new Intent(context, UpDatePhieuChamBai.class);
                                    intent.putExtra("PhieuChamBai", phieuChamBai);
                                    context.startActivity(intent);
                                }
                                catch (Exception ex)
                                {
                                    Toast.makeText(context, "Lỗi khi gửi Phiếu Chấm bài qua UpDatePCB", Toast.LENGTH_LONG).show();
                                }

                                return true;
                            case R.id.itXoaPCB:
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                                alertDialogBuilder.setMessage("Bạn có chắc chắn muốn xóa Phiếu ?");
                                alertDialogBuilder.setCancelable(false);
                                alertDialogBuilder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.R)
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        DBQuanLyChamBai dbQuanLyChamBai = new DBQuanLyChamBai(context);
                                        int kq = dbQuanLyChamBai.deletePhieuChamBai(phieuChamBai);
                                        System.out.println("\nSố phiếu: " + phieuChamBai.getSoPhieu());
                                        if (kq == 0) {
                                            Toast.makeText(context, "Xóa Thất Bại!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, "Xóa Thành Công!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(context, QLPhieuChamBaiActivity.class);
                                            context.startActivity(intent);
                                        }
                                    }
                                });
                                alertDialogBuilder.setNeutralButton("Hủy", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alertDialogBuilder.create().show();


                                return true;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
        return convertView;
    }


}
