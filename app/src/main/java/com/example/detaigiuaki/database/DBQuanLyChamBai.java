package com.example.detaigiuaki.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.detaigiuaki.model.GiaoVien;
import com.example.detaigiuaki.model.MonHoc;
import com.example.detaigiuaki.model.NguoiDung;
import com.example.detaigiuaki.model.PhieuChamBai;
import com.example.detaigiuaki.model.Report;
import com.example.detaigiuaki.model.ThongTinChamBai;
import com.example.detaigiuaki.model.ThongTinMail;

import java.util.ArrayList;

public class DBQuanLyChamBai extends SQLiteOpenHelper {
    //database values
    private static final String DATABASE_NAME      = "QuanLyChamBai";
    private static final int DATABASE_VERSION      = 1;

    //team table
    private String CreateMonHoc = "CREATE TABLE IF NOT EXISTS MonHoc (MAMH NVARCHAR PRIMARY KEY, TENMH NVARCHAR, CHIPHI int)";
    private String CreateGiaoVien = "CREATE TABLE IF NOT EXISTS GiaoVien (MAGV NVARCHAR PRIMARY KEY, HOTENGV NVARCHAR, SODT NVARCHAR)";
    private String CreatePhieuChamBai = "CREATE TABLE IF NOT EXISTS PhieuChamBai (SOPHIEU INTEGER PRIMARY KEY AUTOINCREMENT, NGAYGIAO date, MAGV NVARCHAR," +
                                        "FOREIGN KEY (MAGV) REFERENCES GiaoVien(MAGV))";
    private String CreateThongTinChamBai = "CREATE TABLE IF NOT EXISTS ThongTinChamBai (SOPHIEU INTEGER, MAMH NVARCHAR, SOBAI int, CONSTRAINT thongtinchambai_pk PRIMARY KEY (SOPHIEU, MAMH)," +
                                            "FOREIGN KEY (SOPHIEU) REFERENCES PhieuChamBai(SOPHIEU)," +
                                            "FOREIGN KEY (MAMH) REFERENCES MonHoc(MAMH))";
    private String CreateNguoiDung = "CREATE TABLE IF NOT EXISTS NguoiDung (TENDANGNHAP NVARCHAR PRIMARY KEY, MATKHAU NVARCHAR, ROLE NVARCHAR, MAGV NVARCHAR," +
                                     "FOREIGN KEY(MAGV) REFERENCES GiaoVien(MAGV))";

    public DBQuanLyChamBai(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(CreateNguoiDung);
            sqLiteDatabase.execSQL(CreateMonHoc);
            sqLiteDatabase.execSQL(CreateGiaoVien);
            sqLiteDatabase.execSQL(CreatePhieuChamBai);
            sqLiteDatabase.execSQL(CreateThongTinChamBai);
        } catch (SQLException e) {
            System.out.println("Lỗi tạo db");
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS NguoiDung");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS MonHoc");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS GiaoVien");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PhieuChamBai");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS ThongTinChamBai");

        onCreate(sqLiteDatabase);
    }
    //CRUD Phiếu Chấm Bài
    public int AddPhieuChamBai(PhieuChamBai pcb)
    {
        try
        {
             SQLiteDatabase database = getWritableDatabase();
             ContentValues values = new ContentValues();
             values.put("NGAYGIAO", pcb.getNgayGiao());
             values.put("MAGV",pcb.getMaGV());
             database.insert("PhieuChamBai",null,values);
             database.close();
             return 1;
        }
        catch (SQLException ex)
        {
            return 0;
        }
    }
    public int updatePhieuChamBai(PhieuChamBai pcb)
    {
        try {
            String sql = "UPDATE PhieuChamBai SET MAGV = ?, NGAYGIAO = ? WHERE SOPHIEU = ?";
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(sql, new Object[]{pcb.getMaGV(), pcb.getNgayGiao(), pcb.getSoPhieu()});
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
    public int deletePhieuChamBai(PhieuChamBai pcb)
    {
        try {
            String sql = "DELETE FROM PhieuChamBai WHERE SOPHIEU = ?";
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(sql, new Object[]{pcb.getSoPhieu()});
            db.close();

        } catch (SQLException e) {
            System.out.println("LỖI");
            System.out.println(e.getMessage());
            return 0;
        }
        return 1;

    }
    public ArrayList<PhieuChamBai> readPhieuChamBai() {
        ArrayList<PhieuChamBai> data = new ArrayList<>();
        String sql = "select SOPHIEU,NGAYGIAO,GiaoVien.MAGV,HOTENGV \n" +
                     "from PhieuChamBai,GiaoVien \n" +
                     "WHERE PhieuChamBai.MAGV=GiaoVien.MAGV \n" +
                     "ORDER BY SoPhieu DESC";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                PhieuChamBai pcb = new PhieuChamBai();
                pcb.setSoPhieu(cursor.getInt(0));
                pcb.setMaGV(cursor.getString(2));
                pcb.setNgayGiao(cursor.getString(1));
                pcb.setTenGV(cursor.getString(3));
                data.add(pcb);
            }
            while (cursor.moveToNext());
        }
        return data;
    }
    public ArrayList<PhieuChamBai> readPhieuChamBai(String sql) {
        ArrayList<PhieuChamBai> data = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                PhieuChamBai pcb = new PhieuChamBai();
                pcb.setSoPhieu(cursor.getInt(0));
                pcb.setMaGV(cursor.getString(2));
                pcb.setNgayGiao(cursor.getString(1));
                pcb.setTenGV(cursor.getString(3));
                data.add(pcb);
            }
            while (cursor.moveToNext());
        }
        return data;
    }
    public GiaoVien getGV(String maGV)
    {
        String sql = "SELECT * FROM GiaoVien WHERE MAGV='"+maGV+"'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        GiaoVien gv = new GiaoVien();
        if(cursor.moveToFirst()){
           gv.setId(cursor.getString(0));
           gv.setName(cursor.getString(1));
           gv.setPhone(cursor.getString(2));
        }
        return gv;
    }
    // CRUD MonHoc
    public int AddMonHoc(MonHoc mh){
        try {
            String sql = "INSERT INTO MonHoc VALUES('" + mh.getId() + "', '" + mh.getName() + "', " + mh.getCost() + ")";
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(sql);
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    public ArrayList<MonHoc> ReadMonHoc(){
        ArrayList<MonHoc> list = new ArrayList<>();
        String sql = "SELECT * FROM MonHoc";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do {
                MonHoc mh = new MonHoc();
                mh.setId(cursor.getString(0));
                mh.setName(cursor.getString(1));
                mh.setCost((int) cursor.getFloat(2));
                list.add(mh);
            }while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<MonHoc> ReadMonHoc(String sql){
        ArrayList<MonHoc> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do {
                MonHoc mh = new MonHoc();
                mh.setId(cursor.getString(0));
                mh.setName(cursor.getString(1));
                mh.setCost((int) cursor.getFloat(2));
                list.add(mh);
            }while (cursor.moveToNext());
        }
        return list;
    }

    public int UpdateMonHoc(MonHoc mh){
        try {
            String sql = "UPDATE MonHoc SET TENMH = ?, CHIPHI = ? WHERE MAMH = ?";
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(sql, new Object[]{mh.getName(), mh.getCost(), mh.getId()});
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    public int DeleteMonHoc(MonHoc mh){
        try {
            String sql = "DELETE FROM MonHoc WHERE MAMH = ? ";
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(sql, new String[]{mh.getId()});
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    // read NguoiDung
    public ArrayList<NguoiDung> ReadNguoiDung(){
        ArrayList<NguoiDung> list = new ArrayList<>();
        String sql = "SELECT * FROM NguoiDung";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do {
                NguoiDung nd = new NguoiDung();
                nd.setUserName(cursor.getString(0));
                nd.setPassWord(cursor.getString(1));
                nd.setRole(cursor.getString(2));
                list.add(nd);
            }while (cursor.moveToNext());
        }
        return list;
    }
    public ArrayList<NguoiDung> getDSNguoiDung(){
        ArrayList<NguoiDung> list = new ArrayList<>();
        String sql = "SELECT TENDANGNHAP,MATKHAU,ROLE,NguoiDung.MAGV,GIAOVIEN.HOTENGV FROM NguoiDung,GIAOVIEN Where GIAOVIEN.MAGV=NguoiDung.MAGV";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do {
                NguoiDung nd = new NguoiDung();
                nd.setUserName(cursor.getString(0));
                nd.setPassWord(cursor.getString(1));
                nd.setRole(cursor.getString(2));
                nd.setMaGV(cursor.getString(3));
                nd.setTenGV(cursor.getString(4));
                list.add(nd);
            }while (cursor.moveToNext());
        }
        return list;
    }
    public ArrayList<NguoiDung> getDSNguoiDung(String sql){
        ArrayList<NguoiDung> list = new ArrayList<>();
       // String sql = "SELECT TENDANGNHAP,MATKHAU,ROLE,NguoiDung.MAGV,GIAOVIEN.HOTENGV FROM NguoiDung,GIAOVIEN Where GIAOVIEN.MAGV=NguoiDung.MAGV";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do {
                NguoiDung nd = new NguoiDung();
                nd.setUserName(cursor.getString(0));
                nd.setPassWord(cursor.getString(1));
                nd.setRole(cursor.getString(2));
                nd.setMaGV(cursor.getString(3));
                nd.setTenGV(cursor.getString(4));
                list.add(nd);
            }while (cursor.moveToNext());
        }
        return list;
    }


    public NguoiDung getNguoiDung(String tenDN){
        NguoiDung nd = new NguoiDung();
        String sql = "SELECT * FROM NguoiDung WHERE TENDANGNHAP = '" + tenDN+"'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            nd.setUserName(cursor.getString(0));
            nd.setPassWord(cursor.getString(1));
            nd.setRole(cursor.getString(2));
            nd.setMaGV(cursor.getString(3));
        }

        return nd;
    }

    public void AddNguoiDung(NguoiDung nd, String maGV){
        String sql = "INSERT INTO NguoiDung VALUES(?,?,?,?)";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql, new Object[]{nd.getUserName(), nd.getPassWord(), nd.getRole(), maGV});
        db.close();
    }
    public void updateNguoiDung(NguoiDung nd){
        String sql = "UPDATE NguoiDung SET MATKHAU = ? WHERE TENDANGNHAP = ?";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql, new Object[]{ nd.getPassWord(),nd.getUserName()});
        db.close();
    }

    public void DeleteNguoiDung(String id) {
        String sql = "DELETE FROM NguoiDung WHERE MAGV = ?";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql, new Object[]{id});
        db.close();
    }

    // CRUD Thông tin chấm bài
    public ArrayList<ThongTinChamBai> readTTCB(String sql) {
        ArrayList<ThongTinChamBai> data = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                ThongTinChamBai ttcb = new ThongTinChamBai();
                ttcb.setSoPhieu(cursor.getInt(0));
                ttcb.setSoBai(cursor.getInt(2));
                ttcb.setMaMH(cursor.getString(1));
                ttcb.setTenMH(cursor.getString(3));
                data.add(ttcb);
            }
            while (cursor.moveToNext());
        }
        return data;
    }

    public ArrayList<ThongTinChamBai> getTTChamBai(int soPhieu){
        ArrayList<ThongTinChamBai> list = new ArrayList<>();
        String sql = "SELECT SOPHIEU,MONHOC.MAMH,TENMH,SOBAI FROM ThongTinChamBai,MONHOC WHERE MONHOC.MAMH=THONGTINCHAMBAI.MAMH and SOPHIEU = "+soPhieu;
        System.out.println("\nSo Phiếu"+soPhieu);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                ThongTinChamBai ttcb = new ThongTinChamBai();
                ttcb.setSoPhieu(cursor.getInt(0));
                ttcb.setMaMH(cursor.getString(1));
                ttcb.setSoBai(cursor.getInt(3));
                ttcb.setTenMH(cursor.getString(2));
                list.add(ttcb);
            }while (cursor.moveToNext());
        }
        return list;
    }

    public void AddTTChamBai(ThongTinChamBai ttcb){
        String sql = "INSERT INTO ThongTinChamBai VALUES(?,?,?)";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql, new Object[]{ttcb.getSoPhieu(), ttcb.getMaMH(), ttcb.getSoBai()});
        db.close();
    }

    public void UpdateTTChamBai(ThongTinChamBai ttcb, String mamh){
        String sql = "UPDATE ThongTinChamBai SET  SOBAI = ?,MAMH= ? WHERE SOPHIEU = ? AND MAMH= ?";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql, new Object[]{ttcb.getSoBai(), ttcb.getMaMH(),ttcb.getSoPhieu(),mamh});
        db.close();
    }

    public void DeleteTTChamBai(ThongTinChamBai ttcb){
        String sql = "DELETE FROM ThongTinChamBai WHERE MAMH = ? AND SOPHIEU = ?";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql, new Object[]{ttcb.getMaMH(), ttcb.getSoPhieu()});
        db.close();
    }

    // CRUD Giáo Viên
    public ArrayList<GiaoVien> getDSGiaoVien(){
        ArrayList<GiaoVien> list = new ArrayList<>();
        String sql = "SELECT * FROM GiaoVien";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                GiaoVien gv = new GiaoVien();
                gv.setId(cursor.getString(0));
                gv.setName(cursor.getString(1));
                gv.setPhone(cursor.getString(2));
                list.add(gv);
            }while (cursor.moveToNext());
        }
        return list;
    }
    public ArrayList<GiaoVien> getDSGiaoVien(String sql){
        ArrayList<GiaoVien> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                GiaoVien gv = new GiaoVien();
                gv.setId(cursor.getString(0));
                gv.setName(cursor.getString(1));
                gv.setPhone(cursor.getString(2));
                list.add(gv);
            }while (cursor.moveToNext());
        }
        return list;
    }

    public void AddGiaoVien(GiaoVien gv){
        String sql = "INSERT INTO GiaoVien VALUES(?,?,?)";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql, new Object[]{gv.getId(), gv.getName(), gv.getPhone()});
        db.close();
    }

    public void UpdateGiaoVien(GiaoVien gv){
        String sql = "UPDATE GiaoVien SET HOTENGV = ?, SODT = ? WHERE MAGV = ?";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql, new Object[]{gv.getName(), gv.getPhone(), gv.getId()});
        db.close();
    }

    public void DeleteGiaoVien(GiaoVien gv){
        String sql = "DELETE FROM GiaoVien WHERE MAGV = ?";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql, new Object[]{gv.getId()});
        db.close();
    }

    public ArrayList<Report> GetReport(int soPhieu){
        ArrayList<Report> list = new ArrayList<>();
        String sql = "SELECT TENMH, SOBAI, CHIPHI FROM MonHoc INNER JOIN ThongTinChamBai ON MonHoc.MAMH = ThongTinChamBai.MAMH AND SOPHIEU ='" + soPhieu + "'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                Report rp = new Report();
                rp.setTenMH(cursor.getString(0));
                rp.setSoBai(cursor.getInt(1));
                rp.setChiPhi(cursor.getInt(2));
                list.add(rp);
            }while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<ThongTinMail> readThongTinMail(String sql) {
        ArrayList<ThongTinMail> data = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                ThongTinMail pcb = new ThongTinMail();
                pcb.setSoPhieu(cursor.getInt(0));
                pcb.setHoTenGV(cursor.getString(1));
                pcb.setNgayGiao(cursor.getString(2));
                pcb.setSoBai(cursor.getInt(3));
                data.add(pcb);
            }
            while (cursor.moveToNext());
        }
        return data;
    }
}
