package com.example.do_an_java.repository;

import com.example.do_an_java.entity.CtDatPhong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CtDatPhongRepository extends JpaRepository<CtDatPhong, Integer> {
    @Query("""
            SELECT DISTINCT c.phong.maPhong
            FROM CtDatPhong c
            WHERE c.phong IS NOT NULL
              AND c.ngayNhan < :ngayTra
              AND c.ngayTra > :ngayNhan
            """)
    List<Integer> findMaPhongBiDatTrongKhoang(@Param("ngayNhan") LocalDate ngayNhan,
                                              @Param("ngayTra") LocalDate ngayTra);
}
