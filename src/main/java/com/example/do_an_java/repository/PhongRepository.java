package com.example.do_an_java.repository;

import com.example.do_an_java.entity.Phong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhongRepository extends JpaRepository<Phong, Integer> {
    List<Phong> findByLoaiPhong_MaLoaiPhong(Integer maLoaiPhong);

    long countByLoaiPhong_MaLoaiPhong(Integer maLoaiPhong);
}
