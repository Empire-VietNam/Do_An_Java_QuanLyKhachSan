package com.example.do_an_java.repository;

import com.example.do_an_java.entity.CtDichVu;
import com.example.do_an_java.entity.CtDichVuId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CtDichVuRepository extends JpaRepository<CtDichVu, CtDichVuId> {

    // Lấy danh sách dịch vụ của một đơn đặt phòng
    List<CtDichVu> findByCtDatPhong_MaCtDatPhong(Integer maCtDatPhong);
}