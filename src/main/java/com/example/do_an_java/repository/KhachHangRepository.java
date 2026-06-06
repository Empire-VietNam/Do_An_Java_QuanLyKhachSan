package com.example.do_an_java.repository;

import com.example.do_an_java.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {

    List<KhachHang> findByHoTenContainingIgnoreCase(String keyword);

    List<KhachHang> findByCmndContaining(String cmnd);

    List<KhachHang> findByDienThoaiContaining(String dienThoai);

    @Query("SELECT k FROM KhachHang k WHERE " +
            "LOWER(k.hoTen) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "k.cmnd LIKE CONCAT('%', :keyword, '%') OR " +
            "k.dienThoai LIKE CONCAT('%', :keyword, '%')")
    List<KhachHang> timKiemTheoNhieuTruong(@Param("keyword") String keyword);
}