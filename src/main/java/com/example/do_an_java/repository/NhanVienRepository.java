package com.example.do_an_java.repository;

import com.example.do_an_java.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {

    Optional<NhanVien> findByUsernameAndPassword(String username, String password);

    List<NhanVien> findByTenNhanVienContainingIgnoreCase(String keyword);

    boolean existsByUsername(String username);
}
