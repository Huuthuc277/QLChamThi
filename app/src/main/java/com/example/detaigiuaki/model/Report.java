package com.example.detaigiuaki.model;

public class Report {
    private String tenMH;
    private int soBai, chiPhi, thanhTien;

    public Report() {
    }

    public Report(String tenMH, int soBai, int chiPhi, int thanhTien) {
        this.tenMH = tenMH;
        this.soBai = soBai;
        this.chiPhi = chiPhi;
        this.thanhTien = thanhTien;
    }

    public String getTenMH() {
        return tenMH;
    }

    public void setTenMH(String tenMH) {
        this.tenMH = tenMH;
    }

    public int getSoBai() {
        return soBai;
    }

    public void setSoBai(int soBai) {
        this.soBai = soBai;
    }

    public int getChiPhi() {
        return chiPhi;
    }

    public void setChiPhi(int chiPhi) {
        this.chiPhi = chiPhi;
    }

    public int getThanhTien() {
        return this.getChiPhi()*this.getSoBai();
    }

    public void setThanhTien(int thanhTien) {
        this.thanhTien = thanhTien;
    }
}
