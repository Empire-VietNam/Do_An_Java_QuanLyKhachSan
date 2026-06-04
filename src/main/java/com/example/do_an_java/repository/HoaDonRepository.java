package com.example.do_an_java.repository;

import com.example.do_an_java.entity.HoaDon;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
    @Query("select coalesce(sum(h.soTienThu), 0) from HoaDon h")
    Long tinhTongDoanhThu();

}
