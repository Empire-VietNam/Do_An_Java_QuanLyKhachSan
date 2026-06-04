package com.example.do_an_java.repository;

import com.example.do_an_java.entity.BangPhanCong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BangPhanCongRepository extends JpaRepository<BangPhanCong, Integer> {
}
