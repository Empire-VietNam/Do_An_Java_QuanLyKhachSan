package com.example.do_an_java.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "CTDATPHONG")
public class CtDatPhong {
    @Id
    @Column(name = "MaCTDatPhong")
    private Integer maCtDatPhong;

    @Column(name = "NgayThucHien")
    private LocalDate ngayThucHien;

    @ManyToOne
    @JoinColumn(name = "MaKhachHang")
    private KhachHang khachHang;

    @Column(name = "NgayNhan")
    private LocalDate ngayNhan;

    @Column(name = "NgayTra")
    private LocalDate ngayTra;

    @ManyToOne
    @JoinColumn(name = "MaPhong")
    private Phong phong;

    @ManyToOne
    @JoinColumn(name = "MaNhanVien")
    private NhanVien nhanVien;

    public Integer getMaCtDatPhong() { return maCtDatPhong; }
    public void setMaCtDatPhong(Integer maCtDatPhong) { this.maCtDatPhong = maCtDatPhong; }

    public LocalDate getNgayThucHien() { return ngayThucHien; }
    public void setNgayThucHien(LocalDate ngayThucHien) { this.ngayThucHien = ngayThucHien; }

    public KhachHang getKhachHang() { return khachHang; }
    public void setKhachHang(KhachHang khachHang) { this.khachHang = khachHang; }

    public LocalDate getNgayNhan() { return ngayNhan; }
    public void setNgayNhan(LocalDate ngayNhan) { this.ngayNhan = ngayNhan; }

    public LocalDate getNgayTra() { return ngayTra; }
    public void setNgayTra(LocalDate ngayTra) { this.ngayTra = ngayTra; }

    public Phong getPhong() { return phong; }
    public void setPhong(Phong phong) { this.phong = phong; }

    public NhanVien getNhanVien() { return nhanVien; }
    public void setNhanVien(NhanVien nhanVien) { this.nhanVien = nhanVien; }
}
