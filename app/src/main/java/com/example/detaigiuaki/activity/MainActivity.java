package com.example.detaigiuaki.activity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.detaigiuaki.R;
import com.example.detaigiuaki.adapter.AdapterChucNang;
import com.example.detaigiuaki.database.DBQuanLyChamBai;
import com.example.detaigiuaki.model.ChucNang;
import com.example.detaigiuaki.model.GiaoVien;
import com.example.detaigiuaki.model.NguoiDung;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvFunc;
    AdapterChucNang adapterChucNang;
    ArrayList<ChucNang> list = new ArrayList<>();
    NguoiDung nguoiDung;
    Button btnDoiAnh;
    ImageView ivHinhAnh;
    NguoiDung nd;
    GiaoVien gv;
    Button btnDoiMK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();
    }

    private void setEvent() {
        khoiTao();
        adapterChucNang = new AdapterChucNang(this, R.layout.layout_item, this.list);
        lvFunc.setAdapter(adapterChucNang);
        lvFunc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (list.get(i).getName().equals("Quản lý môn học")) {
                    Intent intent = new Intent(getApplicationContext(), QLMonHocActivity.class);
                    startActivity(intent);
                }
                if (list.get(i).getName().equals("Quản lý tài khoản")) {
                    Intent intent = new Intent(getApplicationContext(), QLNguoiDungActivity.class);
                    startActivity(intent);
                }
                if (list.get(i).getName().equals("Quản lý chấm bài")) {
                    Intent intent = new Intent(getApplicationContext(), QLPhieuChamBaiActivity.class);
                    startActivity(intent);
                }
                if (list.get(i).getName().equals("Quản lý giáo viên")) {
                    Intent intent = new Intent(getApplicationContext(), QLGiaoVienActivity.class);
                    startActivity(intent);
                }
                if (list.get(i).getName().equals("Gửi Mail Thông Tin")) {
                    Intent intent = new Intent(getApplicationContext(), GuiMailActivity.class);
                    startActivity(intent);
                }
            }
        });
        btnDoiAnh.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), btnDoiAnh);
                MenuInflater inflater = popup.getMenuInflater();
                popup.setForceShowIcon(true);
                inflater.inflate(R.menu.popupmenu_doianh, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.itChonAnh:
                                Intent i = new Intent();
                                i.setType("image/*");
                                i.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(i, "Select Picture"), 200);
                                return true;
                            case R.id.itChupAnh:
                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                try {
                                    startActivityForResult(takePictureIntent, 1);
                                } catch (ActivityNotFoundException e) {
                                }
                                return true;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hàm này là ấn vào nút đổi mật khẩu nè
//                //v bay h cho nhấn nút nó link vào màn hình send há m, đúng r, link qua send
////              Intent intent = new Intent(getApplicationContext(), DoiMatKhauActivity.class);
//                intent.putExtra("NguoiDung",nd);
//                startActivity(intent);
//                finish();

                Intent intent = new Intent(getApplicationContext(), SendOTPActivity.class);
                intent.putExtra("NguoiDung",nd);
                startActivity(intent);
                finish();

            }
        });

    }

    private void khoiTao() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nd=(NguoiDung) extras.getSerializable("NguoiDung");
            System.out.println("nd "+nd.getUserName());
        }
        if(nd!=  null)
        {
            System.out.println("Vào đây lấy gv");
           DBQuanLyChamBai db = new DBQuanLyChamBai(this);
            System.out.println(nd.getMaGV());
           gv=db.getGV(nd.getMaGV());
            System.out.println(gv.getName());
            // thông tin người dùng
            TextView tvTenND = this.findViewById(R.id.tvName);
            TextView tvSDT = this.findViewById(R.id.tvSDT);
            TextView tvChucVu = this.findViewById(R.id.tvChucVu);
            tvTenND.setText(gv.getName());
            tvSDT.setText("SĐT: "+gv.getPhone());
            tvChucVu.setText("Chức Vụ: "+nd.getRole());
        }

        ChucNang chucNang1 = new ChucNang("Quản lý tài khoản", R.drawable.ic_baseline_person_24);
        ChucNang chucNang2 = new ChucNang("Quản lý môn học", R.drawable.book);
        ChucNang chucNang3 = new ChucNang("Quản lý chấm bài", R.drawable.papers);
        ChucNang chucNang4 = new ChucNang("Quản lý giáo viên", R.drawable.card_search);
        ChucNang chucNang5 = new ChucNang("Gửi Mail Thông Tin", R.drawable.ic_mail);
        list.add(chucNang1);
        list.add(chucNang2);
        list.add(chucNang3);
        list.add(chucNang4);
        list.add(chucNang5);

    }

    private void setControl() {
        lvFunc = findViewById(R.id.lvFunc);
        btnDoiAnh = findViewById(R.id.btnDoiAnh);
        ivHinhAnh = findViewById(R.id.ivHinhAnh);
        btnDoiMK=findViewById(R.id.btnMK);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 200) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    ivHinhAnh.setImageURI(selectedImageUri);
                }
            }
            if (requestCode == 1) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                ivHinhAnh.setImageBitmap(imageBitmap);
            }
        }
    }
}