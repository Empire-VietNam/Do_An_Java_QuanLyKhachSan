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
}
