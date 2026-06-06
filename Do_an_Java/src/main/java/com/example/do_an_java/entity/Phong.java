package com.example.do_an_java.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "PHONG")
public class Phong {
    @Id
    @Column(name = "MaPhong")
    private Integer maPhong;

    @ManyToOne
    @JoinColumn(name = "MaLoaiPhong")
    private LoaiPhong loaiPhong;

    public Integer getMaPhong() { return maPhong; }
    public void setMaPhong(Integer maPhong) { this.maPhong = maPhong; }

    public LoaiPhong getLoaiPhong() { return loaiPhong; }
    public void setLoaiPhong(LoaiPhong loaiPhong) { this.loaiPhong = loaiPhong; }

    @Column(name = "TrangThai", length = 30)
    private String trangThai;

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
