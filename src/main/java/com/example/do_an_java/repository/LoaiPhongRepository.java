package com.example.do_an_java.repository;

import com.example.do_an_java.entity.LoaiPhong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoaiPhongRepository extends JpaRepository<LoaiPhong, Integer> {
    List<LoaiPhong> findByDaXoaFalseOrDaXoaIsNull();
}
