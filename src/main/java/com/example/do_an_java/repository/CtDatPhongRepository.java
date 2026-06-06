package com.example.do_an_java.repository;

import com.example.do_an_java.entity.CtDatPhong;
import com.example.do_an_java.entity.TrangThaiDatPhong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CtDatPhongRepository extends JpaRepository<CtDatPhong, Integer> {

   // Lấy danh sách đặt phòng của một khách hàng
    List<CtDatPhong> findByKhachHang_MaKhachHang(Integer maKhachHang);

    //  Lọc theo trạng thái
    List<CtDatPhong> findByTrangThai(TrangThaiDatPhong trangThai);

    // Kiểm tra phòng trống trong khoảng ngày (không bao gồm đã hủy)
    @Query("SELECT dp FROM CtDatPhong dp WHERE dp.phong.maPhong = :maPhong " +
            "AND dp.trangThai != 'DA_HUY' " +
            "AND dp.ngayNhan < :ngayTra AND dp.ngayTra > :ngayNhan")
    List<CtDatPhong> kiemTraPhongTrong(
            @Param("maPhong") Integer maPhong,
            @Param("ngayNhan") LocalDate ngayNhan,
            @Param("ngayTra") LocalDate ngayTra
    );

    //  Đếm số đơn đặt phòng đang hoạt động (chưa trả, chưa hủy)
    @Query("SELECT COUNT(dp) FROM CtDatPhong dp WHERE dp.trangThai != 'DA_TRA' AND dp.trangThai != 'DA_HUY'")
    long countActiveBookings();
}