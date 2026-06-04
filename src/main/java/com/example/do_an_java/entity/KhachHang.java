package com.example.do_an_java.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "KHACHHANG")
public class KhachHang {
    @Id
    @Column(name = "MaKhachHang")
    private Integer maKhachHang;

    @Column(name = "HoTen", length = 255)
    private String hoTen;

    @Column(name = "Email", length = 30)
    private String email;

    @Column(name = "DiaChi", length = 50)
    private String diaChi;

    @Column(name = "DienThoai", length = 20)
    private String dienThoai;

    @Column(name = "CMND", length = 20)
    private String cmnd;

    public Integer getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(Integer maKhachHang) { this.maKhachHang = maKhachHang; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getDienThoai() { return dienThoai; }
    public void setDienThoai(String dienThoai) { this.dienThoai = dienThoai; }

    public String getCmnd() { return cmnd; }
    public void setCmnd(String cmnd) { this.cmnd = cmnd; }
}
