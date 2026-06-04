package com.example.do_an_java.repository;

import com.example.do_an_java.entity.CtDichVu;
import com.example.do_an_java.entity.CtDichVuId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CtDichVuRepository extends JpaRepository<CtDichVu, CtDichVuId> {
}
