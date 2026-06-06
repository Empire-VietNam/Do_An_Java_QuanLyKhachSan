package com.example.do_an_java.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "DICHVU")
public class DichVu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDichVu")
    private Integer maDichVu;

    @Column(name = "TenDichVu", length = 50)
    private String tenDichVu;

    @Column(name = "GiaDichVu")
    private Integer giaDichVu;

    public Integer getMaDichVu() { return maDichVu; }
    public void setMaDichVu(Integer maDichVu) { this.maDichVu = maDichVu; }

    public String getTenDichVu() { return tenDichVu; }
    public void setTenDichVu(String tenDichVu) { this.tenDichVu = tenDichVu; }

    public Integer getGiaDichVu() { return giaDichVu; }
    public void setGiaDichVu(Integer giaDichVu) { this.giaDichVu = giaDichVu; }
}
