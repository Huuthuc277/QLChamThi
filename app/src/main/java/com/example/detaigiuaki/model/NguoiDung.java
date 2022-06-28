package com.example.detaigiuaki.model;

import java.io.Serializable;

public class NguoiDung implements Serializable {
    private String userName;
    private String passWord;
    private String role;
    private String maGV;
    private String tenGV;

    public NguoiDung() {
    }

    public NguoiDung(String userName, String passWord, String role, String maGV, String tenGV) {
        this.userName = userName;
        this.passWord = passWord;
        this.role = role;
        this.maGV = maGV;
        this.tenGV = tenGV;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
