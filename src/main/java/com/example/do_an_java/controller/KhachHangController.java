package com.example.do_an_java.controller;

import com.example.do_an_java.entity.KhachHang;
import com.example.do_an_java.repository.CtDatPhongRepository;
import com.example.do_an_java.repository.KhachHangRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/khach-hang")
public class KhachHangController {
    private final KhachHangRepository khachHangRepository;
    private final CtDatPhongRepository ctDatPhongRepository;

    public KhachHangController(KhachHangRepository khachHangRepository,
                               CtDatPhongRepository ctDatPhongRepository) {
        this.khachHangRepository = khachHangRepository;
        this.ctDatPhongRepository = ctDatPhongRepository;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "") String keyword,
                       @RequestParam(defaultValue = "hoTen") String searchType,
                       Model model) {
        java.util.List<KhachHang> ketQua;

        if (keyword.isBlank()) {
            ketQua = khachHangRepository.findAll();
        } else {
            switch (searchType) {
                case "cmnd":
                    ketQua = khachHangRepository.findByCmndContaining(keyword);
                    break;
                case "dienThoai":
                    ketQua = khachHangRepository.findByDienThoaiContaining(keyword);
                    break;
                default:
                    ketQua = khachHangRepository.findByHoTenContainingIgnoreCase(keyword);
                    break;
            }
        }

        model.addAttribute("items", ketQua);
        model.addAttribute("keyword", keyword);
        model.addAttribute("searchType", searchType);
        return "khach-hang/list";
    }

    @GetMapping("/them")
    public String add(Model model) {
        model.addAttribute("item", new KhachHang());
        return "khach-hang/form";
    }

    @GetMapping("/sua/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("item", khachHangRepository.findById(id).orElseThrow());
        return "khach-hang/form";
    }

    @PostMapping("/luu")
    public String save(@ModelAttribute("item") KhachHang item) {
        khachHangRepository.save(item);
        return "redirect:/khach-hang";
    }

    @GetMapping("/xoa/{id}")
    public String delete(@PathVariable Integer id) {
        khachHangRepository.deleteById(id);
        return "redirect:/khach-hang";
    }

    // Chức năng 6 - Xem lịch sử đặt phòng của khách hàng
    @GetMapping("/lich-su/{maKhachHang}")
    public String xemLichSuDatPhong(@PathVariable Integer maKhachHang, Model model) {
        KhachHang khachHang = khachHangRepository.findById(maKhachHang)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));
        java.util.List<com.example.do_an_java.entity.CtDatPhong> lichSu =
                ctDatPhongRepository.findByKhachHang_MaKhachHang(maKhachHang);

        model.addAttribute("khachHang", khachHang);
        model.addAttribute("lichSu", lichSu);
        return "khach-hang/lich-su";
    }
}