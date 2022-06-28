package com.example.detaigiuaki.model;

public class ChucNang {
    private String name;
    private int icon;

    public ChucNang(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public ChucNang() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
