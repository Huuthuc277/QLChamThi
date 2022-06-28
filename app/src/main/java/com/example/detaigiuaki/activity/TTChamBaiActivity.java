package com.example.detaigiuaki.activity;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.detaigiuaki.R;
import com.example.detaigiuaki.adapter.AdapterTTChamBai;
import com.example.detaigiuaki.database.DBQuanLyChamBai;
import com.example.detaigiuaki.model.PhieuChamBai;
import com.example.detaigiuaki.model.Report;
import com.example.detaigiuaki.model.ThongTinChamBai;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class TTChamBaiActivity extends AppCompatActivity {
    ImageButton fabAddTTCB;
    ListView lvTTCB;
    SearchView svDSTTCB;
    ArrayList<ThongTinChamBai> list;
    DBQuanLyChamBai database;
    AdapterTTChamBai adapterTTChamBai;

    PhieuChamBai pcbCha= new PhieuChamBai();
    TextView txtNgayGiaoPCBC;
    TextView txtTenGVPCBC;
    TextView txtMaGVPCBC;
    TextView txtSoPhieuPCBC;

    int pageHeight = 1120;
    int pageWidth = 792;
    Bitmap bmp, scaledbmp;
    private static final int PERMISSION_REQUEST_CODE = 200;

    Animation scaleUp, scaleDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttchambai);
        setControl();
        setEvent();
    }

    private void setEvent() {
        khoiTao();
        if (list != null) {
            adapterTTChamBai = new AdapterTTChamBai(this, R.layout.layout_item_ttchambai,list, pcbCha);
            lvTTCB.setAdapter(adapterTTChamBai);
        }
        lvTTCB.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), ThemUpdateTTCBActitity.class);
                intent.putExtra("ThongTinChamBai", list.get(i));
                System.out.println("TTCB: "+ list.get(i).getMaMH());
                intent.putExtra("flag", "edit");
                intent.putExtra("PhieuChamBai",pcbCha);
                startActivity(intent);


            }
        });
        svDSTTCB.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(s.trim().equals("")==false)
                {
                    DBQuanLyChamBai db = new DBQuanLyChamBai(TTChamBaiActivity.this);
                    String sql="select SOPHIEU,MONHOC.MAMH,TENMH,SOBAI \n" +
                            "from ThongTinChamBai,MONHOC \n" +
                            "WHERE " +
                            "(instr(upper(TENMH), upper('"+s+"'))!=0)) OR (instr(upper(SOBAI), upper('"+s+"'))!=0)) "+
                            "ORDER BY SoPhieu DESC";
                    list = db.readTTCB(sql);
                    AdapterTTChamBai adapter = new AdapterTTChamBai(TTChamBaiActivity.this,R.layout.layout_item_ttchambai,list);
                    lvTTCB.setAdapter(adapter);
                }
                else
                {
                    DBQuanLyChamBai db = new DBQuanLyChamBai(TTChamBaiActivity.this);
                    list = db.getTTChamBai(pcbCha.getSoPhieu());
                    AdapterTTChamBai adapter = new AdapterTTChamBai(TTChamBaiActivity.this,R.layout.layout_itemphieuchambai,list);
                    lvTTCB.setAdapter(adapter);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.trim().equals("")==false)
                {
                    DBQuanLyChamBai db = new DBQuanLyChamBai(TTChamBaiActivity.this);
                    String sql="select SOPHIEU,MONHOC.MAMH,TENMH,SOBAI \n" +
                            "from ThongTinChamBai,MONHOC \n" +
                            "WHERE " +
                            "(instr(upper(TENMH), upper('"+s+"'))!=0)) OR (instr(upper(SOBAI), upper('"+s+"'))!=0)) "+
                            "ORDER BY SoPhieu DESC";
                    list = db.readTTCB(sql);
                    AdapterTTChamBai adapter = new AdapterTTChamBai(TTChamBaiActivity.this,R.layout.layout_itemphieuchambai,list);
                    lvTTCB.setAdapter(adapter);
                }
                else
                {
                    DBQuanLyChamBai db = new DBQuanLyChamBai(TTChamBaiActivity.this);
                    list = db.getTTChamBai((pcbCha.getSoPhieu()));
                    AdapterTTChamBai adapter = new AdapterTTChamBai(TTChamBaiActivity.this,R.layout.layout_itemphieuchambai,list);
                    lvTTCB.setAdapter(adapter);
                }
                return false;
            }
        });

        fabAddTTCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ThemUpdateTTCBActitity.class);
                intent.putExtra("flag", "add");
                intent.putExtra("PhieuChamBai",pcbCha);
                startActivity(intent);
            }
        });
        fabAddTTCB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == motionEvent.ACTION_UP){
                    fabAddTTCB.startAnimation(scaleUp);

                } else if(motionEvent.getAction() == motionEvent.ACTION_DOWN){
                    fabAddTTCB.startAnimation(scaleDown);

                }
                return false;
            }
        });
    }

    private void khoiTao() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pcbCha = (PhieuChamBai) extras.getSerializable("PhieuChamBai");
            txtSoPhieuPCBC.setText(String.valueOf(pcbCha.getSoPhieu()));
            txtMaGVPCBC.setText(pcbCha.getMaGV());
            txtTenGVPCBC.setText(pcbCha.getTenGV());
            txtNgayGiaoPCBC.setText(pcbCha.getNgayGiao());

            database = new DBQuanLyChamBai(this);
            list = database.getTTChamBai(pcbCha.getSoPhieu());
            try {
                list = database.getTTChamBai(pcbCha.getSoPhieu());
            } catch (Exception ex) {
                Toast.makeText(this, "Lỗi Load TTCB!", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            list=new ArrayList<>();
        }
    }

    private void setControl() {
        lvTTCB = findViewById(R.id.lvTTCB);
        fabAddTTCB = findViewById(R.id.fabAddTTCB);
        svDSTTCB=(SearchView) findViewById(R.id.svDSTTCB);

        txtSoPhieuPCBC = findViewById(R.id.txtSoPhieuPCBC);
        txtMaGVPCBC = findViewById(R.id.txtMaGVPCBC);
        txtTenGVPCBC = findViewById(R.id.txtTenGVPCBC);
        txtNgayGiaoPCBC = findViewById(R.id.txtNgayGiaoPCBC);

        scaleUp = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_baocao, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itPDF:
                if (checkPermission()) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    requestPermission();
                }
                createPDF();
                return true;
            case R.id.itExcel:
                if (checkPermission()) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    requestPermission();
                }
                createExcel();
                return true;
            case R.id.itChart:
                Intent intent = new Intent(getApplicationContext(), ChartActivity.class);
                intent.putExtra("soPhieu", pcbCha.getSoPhieu());
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createPDF() {
        ArrayList<Report> data = new DBQuanLyChamBai(this).GetReport(Integer.parseInt(txtSoPhieuPCBC.getText().toString()));
        // khởi tạo hình logo
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logoptithcm);
        scaledbmp = Bitmap.createScaledBitmap(bmp, pageWidth, 70, false);

        // khởi tạo biến văn bản pdf
        PdfDocument pdfDocument = new PdfDocument();

        // khởi tạo 2 biến paint để vẽ
        Paint title = new Paint();
        Paint paint = new Paint();

        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();

        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        Canvas canvas = myPage.getCanvas();

        canvas.drawBitmap(scaledbmp, 0, 0, paint);

        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        title.setTextSize(40);
        title.setColor(ContextCompat.getColor(this, R.color.purple_500));
        title.setTextAlign(Paint.Align.CENTER);

        canvas.drawText("PHIẾU THANH TOÁN CHẤM BÀI THI", pageWidth/2, 150, title);

        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        title.setTextSize(16);
        title.setColor(ContextCompat.getColor(this, R.color.purple_500));
        title.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Mã giáo viên: ", 100, 170, title);
        canvas.drawText("Họ tên giáo viên: ", 100, 200, title);

        canvas.drawText(txtMaGVPCBC.getText().toString(), 220, 170, title);
        canvas.drawText(txtTenGVPCBC.getText().toString(), 220, 200, title);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawRect(100, 250, pageWidth - 100, 300, paint);

        title.setTextAlign(Paint.Align.CENTER);
        title.setStyle(Paint.Style.FILL);
        canvas.drawText("STT", 120, 280, title);
        canvas.drawText("Tên môn học", 220, 280, title);
        canvas.drawText("Số bài", 405, 280, title);
        canvas.drawText("Chi phí", 500, 280, title);
        canvas.drawText("Thành tiền", 620, 280, title);

        canvas.drawLine(150, 250, 150, 300, paint);
        canvas.drawLine(370, 250, 370, 300, paint);
        canvas.drawLine(450, 250, 450, 300, paint);
        canvas.drawLine(560, 250, 560, 300, paint);

        for(int i = 0; i< data.size(); i++){
            canvas.drawText(String.valueOf(i+1), 120, 330 + i*20, title);
            canvas.drawText(data.get(i).getTenMH(), 220, 330 + i*20, title);
            canvas.drawText(String.valueOf(data.get(i).getSoBai()), 405, 330 + i*20, title);
            canvas.drawText(String.valueOf(data.get(i).getChiPhi()), 500, 330 + i*20, title);
            canvas.drawText(String.valueOf(data.get(i).getThanhTien()), 620, 330 + i*20, title);//chi phí * số bài
        }

//        canvas.drawText("1", 120, 330, title);
//        canvas.drawText("Access2", 220, 330, title);
//        canvas.drawText("25", 405, 330, title);
//        canvas.drawText("3,500", 500, 330, title);
//        canvas.drawText("87,500", 620, 330, title);//chi phí * số bài

        pdfDocument.finishPage(myPage);

        File file = new File(Environment.getExternalStorageDirectory(),"report.pdf");
        System.out.println(file.getAbsolutePath());

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(this, "Đã tạo file pdf thành công!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdfDocument.close();
    }

    private void createExcel(){
        ArrayList<Report> data = new DBQuanLyChamBai(this).GetReport(Integer.parseInt(txtSoPhieuPCBC.getText().toString()));
        Workbook wb = new HSSFWorkbook();
        Cell cell = null;
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor((short) 0x30);

        Sheet sheet = null;
        sheet = wb.createSheet("QuanLyChamDiem");
        Row row = sheet.createRow(0);

        cell = row.createCell(0);
        cell.setCellValue("STT");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        cell.setCellValue("Tên Môn Học");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue("Số Bài");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3);
        cell.setCellValue("Chi Phí");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(4);
        cell.setCellValue("Thành Tiền");
        cell.setCellStyle(cellStyle);

        for(int i = 0; i<data.size(); i++){
            Row row1 = sheet.createRow(i+1);

            cell = row1.createCell(0);
            cell.setCellValue(i+1);
            cell.setCellStyle(cellStyle);

            cell = row1.createCell(1);
            cell.setCellValue(data.get(i).getTenMH());
            cell.setCellStyle(cellStyle);

            cell = row1.createCell(2);
            cell.setCellValue(data.get(i).getSoBai());
            cell.setCellStyle(cellStyle);

            cell = row1.createCell(3);
            cell.setCellValue(data.get(i).getChiPhi());
            cell.setCellStyle(cellStyle);

            cell = row1.createCell(4);
            cell.setCellValue(data.get(i).getThanhTien());
            cell.setCellStyle(cellStyle);
        }

        sheet.setDefaultColumnWidth(20);

        File file = new File(Environment.getExternalStorageDirectory(), "reportxl.xls");
        System.out.println(file.getAbsolutePath());
        try {
            wb.write(new FileOutputStream(file));
            Toast.makeText(this, "Đã tạo file excel thành công!", Toast.LENGTH_SHORT).show();
            wb.close();
        } catch (IOException e) {
            Toast.makeText(this, "Đã tạo file thất bại!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private boolean checkPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int result = ContextCompat.checkSelfPermission(TTChamBaiActivity.this, READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(TTChamBaiActivity.this, WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
                startActivityForResult(intent, 2296);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 2296);
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(TTChamBaiActivity.this, new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
}