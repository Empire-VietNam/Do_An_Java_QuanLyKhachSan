package com.example.do_an_java.entity;

public enum TrangThaiDatPhong {
    DA_DAT("Đã đặt"),
    DANG_O("Đang ở"),
    DA_TRA("Đã trả"),
    DA_HUY("Đã hủy");

    private final String tenHienThi;

    TrangThaiDatPhong(String tenHienThi) {
        this.tenHienThi = tenHienThi;
    }

    public String getTenHienThi() {
        return tenHienThi;
    }

    public static TrangThaiDatPhong fromString(String text) {
        for (TrangThaiDatPhong tt : TrangThaiDatPhong.values()) {
            if (tt.name().equalsIgnoreCase(text) || tt.tenHienThi.equalsIgnoreCase(text)) {
                return tt;
            }
        }
        return DA_DAT;
    }
}