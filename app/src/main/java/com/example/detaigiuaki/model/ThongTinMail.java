package com.example.detaigiuaki.model;

public class ThongTinMail {
    int soPhieu;
    String hoTenGV;
    String ngayGiao;
    int soBai;

    public ThongTinMail() {
    }

    public ThongTinMail(int soPhieu, String hoTenGV, String ngayGiao, int soBai) {
        this.soPhieu = soPhieu;
        this.hoTenGV = hoTenGV;
        this.ngayGiao = ngayGiao;
        this.soBai = soBai;
    }

    public int getSoPhieu() {
        return soPhieu;
    }

    public void setSoPhieu(int soPhieu) {
        this.soPhieu = soPhieu;
    }

    public String getHoTenGV() {
        return hoTenGV;
    }

    public void setHoTenGV(String hoTenGV) {
        this.hoTenGV = hoTenGV;
    }

    public String getNgayGiao() {
        return ngayGiao;
    }

    public void setNgayGiao(String ngayGiao) {
        this.ngayGiao = ngayGiao;
    }

    public int getSoBai() {
        return soBai;
    }

    public void setSoBai(int soBai) {
        this.soBai = soBai;
    }
}
