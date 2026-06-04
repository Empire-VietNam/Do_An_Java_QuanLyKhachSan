package com.example.do_an_java.repository;

import com.example.do_an_java.entity.CtDatPhong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CtDatPhongRepository extends JpaRepository<CtDatPhong, Integer> {
}
