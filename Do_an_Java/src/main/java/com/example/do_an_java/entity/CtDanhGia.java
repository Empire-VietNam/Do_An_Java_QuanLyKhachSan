package com.example.do_an_java.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "CTDANHGIA")
public class CtDanhGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NoiDung", length = 255)
    private String noiDung;

    @Column(name = "NgayDanhGia")
    private LocalDate ngayDanhGia;

    @ManyToOne
    @JoinColumn(name = "MaPhong")
    private Phong phong;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNoiDung() { return noiDung; }
    public void setNoiDung(String noiDung) { this.noiDung = noiDung; }

    public LocalDate getNgayDanhGia() { return ngayDanhGia; }
    public void setNgayDanhGia(LocalDate ngayDanhGia) { this.ngayDanhGia = ngayDanhGia; }

    public Phong getPhong() { return phong; }
    public void setPhong(Phong phong) { this.phong = phong; }
}
