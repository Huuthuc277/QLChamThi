package com.example.detaigiuaki.model;

import java.io.Serializable;

public class PhieuChamBai implements Serializable {
    private int soPhieu;
    private String maGV;
    private String tenGV="";
    private String ngayGiao;

    public PhieuChamBai() {

    }

    public PhieuChamBai( String maGV, String tenGV, String ngayGiao) {
        this.maGV = maGV;
        this.tenGV = tenGV;
        this.ngayGiao = ngayGiao;
    }

    public PhieuChamBai(int soPhieu, String maGV, String tenGV, String ngayGiao) {
        this.soPhieu = soPhieu;
        this.maGV = maGV;
        this.tenGV = tenGV;
        this.ngayGiao = ngayGiao;
    }

    public int getSoPhieu() {
        return soPhieu;
    }

    public void setSoPhieu(int soPhieu) {
        this.soPhieu = soPhieu;
    }

    public String getMaGV() {
        return maGV;
    }

    public void setMaGV(String maGV) {
        this.maGV = maGV;
    }

    public String getTenGV() {
        return tenGV;
    }

    public void setTenGV(String tenGV) {
        this.tenGV = tenGV;
    }

    public String getNgayGiao() {
        return ngayGiao;
    }

    public void setNgayGiao(String ngayGiao) {
        this.ngayGiao = ngayGiao;
    }
}
