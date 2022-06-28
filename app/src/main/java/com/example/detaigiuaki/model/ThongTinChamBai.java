package com.example.detaigiuaki.model;

import java.io.Serializable;

public class ThongTinChamBai implements Serializable {
    private String maMH;
    private int soPhieu;
    private int soBai;
    private String tenMH;

    public ThongTinChamBai(String maMH,String tenMH, int soPhieu, int soBai) {
        this.maMH = maMH;
        this.soPhieu = soPhieu;
        this.soBai = soBai;
        this.tenMH= tenMH;
    }

    public String getTenMH() {
        return tenMH;
    }

    public void setTenMH(String tenMH) {
        this.tenMH = tenMH;
    }

    public ThongTinChamBai() {
    }

    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH;
    }

    public int getSoPhieu() {
        return soPhieu;
    }

    public void setSoPhieu(int soPhieu) {
        this.soPhieu = soPhieu;
    }

    public int getSoBai() {
        return soBai;
    }

    public void setSoBai(int soBai) {
        this.soBai = soBai;
    }
}
