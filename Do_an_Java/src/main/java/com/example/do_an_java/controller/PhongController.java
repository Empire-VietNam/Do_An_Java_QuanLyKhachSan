package com.example.do_an_java.controller;

import com.example.do_an_java.entity.Phong;
import com.example.do_an_java.repository.CtDatPhongRepository;
import com.example.do_an_java.repository.LoaiPhongRepository;
import com.example.do_an_java.repository.PhongRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/phong")
public class PhongController {
    private final PhongRepository phongRepository;
    private final LoaiPhongRepository loaiPhongRepository;
    private final CtDatPhongRepository ctDatPhongRepository;

    public PhongController(PhongRepository phongRepository,
                           LoaiPhongRepository loaiPhongRepository,
                           CtDatPhongRepository ctDatPhongRepository) {
        this.phongRepository = phongRepository;
        this.loaiPhongRepository = loaiPhongRepository;
        this.ctDatPhongRepository = ctDatPhongRepository;
    }

    @GetMapping
    public String list(@RequestParam(required = false) Integer maPhong,
                       @RequestParam(required = false) Integer maLoaiPhong,
                       @RequestParam(required = false) Integer giaTu,
                       @RequestParam(required = false) Integer giaDen,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayNhan,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayTra,
                       @RequestParam(defaultValue = "0") int page,
                       Model model,
                       HttpServletRequest request) {
        List<Phong> allRooms = phongRepository.findAll();
        List<Phong> items;

        if (maPhong != null) {
            items = phongRepository.findById(maPhong).map(List::of).orElse(List.of());
            if (maLoaiPhong != null) {
                items = items.stream()
                        .filter(phong -> phong.getLoaiPhong() != null
                                && maLoaiPhong.equals(phong.getLoaiPhong().getMaLoaiPhong()))
                        .toList();
            }
        } else if (maLoaiPhong != null) {
            items = phongRepository.findByLoaiPhong_MaLoaiPhong(maLoaiPhong);
        } else {
            items = phongRepository.findAll();
        }

        if (giaTu != null) {
            items = items.stream()
                    .filter(phong -> phong.getLoaiPhong() != null
                            && phong.getLoaiPhong().getGiaPhong() != null
                            && phong.getLoaiPhong().getGiaPhong() >= giaTu)
                    .toList();
        }

        if (giaDen != null) {
            items = items.stream()
                    .filter(phong -> phong.getLoaiPhong() != null
                            && phong.getLoaiPhong().getGiaPhong() != null
                            && phong.getLoaiPhong().getGiaPhong() <= giaDen)
                    .toList();
        }

        if (ngayNhan != null && ngayTra != null) {
            if (ngayTra.isAfter(ngayNhan)) {
                List<Integer> maPhongBiDat = ctDatPhongRepository.findMaPhongBiDatTrongKhoang(ngayNhan, ngayTra);
                items = items.stream()
                        .filter(phong -> !maPhongBiDat.contains(phong.getMaPhong()))
                        .filter(phong -> phong.getTrangThai() == null || !"Bảo trì".equals(phong.getTrangThai()))
                        .toList();
            } else {
                model.addAttribute("dateError", "Ngày trả phải sau ngày nhận");
            }
        }

        PaginationUtil.paginate(model, items, page, request);
        model.addAttribute("maPhong", maPhong);
        model.addAttribute("maLoaiPhong", maLoaiPhong);
        model.addAttribute("giaTu", giaTu);
        model.addAttribute("giaDen", giaDen);
        model.addAttribute("ngayNhan", ngayNhan);
        model.addAttribute("ngayTra", ngayTra);
        model.addAttribute("loaiPhongs", loaiPhongRepository.findByDaXoaFalseOrDaXoaIsNull());
        addRoomStats(model, allRooms);
        return "phong/list";
    }

    @GetMapping("/them")
    public String add(Model model) {
        Phong item = new Phong();
        item.setTrangThai("Trống");

        model.addAttribute("item", item);
        model.addAttribute("loaiPhongs", loaiPhongRepository.findByDaXoaFalseOrDaXoaIsNull());
        return "phong/form";
    }

    @GetMapping("/sua/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("item", phongRepository.findById(id).orElseThrow());
        model.addAttribute("loaiPhongs", loaiPhongRepository.findByDaXoaFalseOrDaXoaIsNull());
        return "phong/form";
    }

    @PostMapping("/luu")
    public String save(@ModelAttribute("item") Phong item) {
        if (item.getTrangThai() == null || item.getTrangThai().isBlank()) {
            item.setTrangThai("Trống");
        }

        phongRepository.save(item);
        return "redirect:/phong";
    }

    @GetMapping("/xoa/{id}")
    public String delete(@PathVariable Integer id) {
        phongRepository.deleteById(id);
        return "redirect:/phong";
    }

    private void addRoomStats(Model model, List<Phong> rooms) {
        model.addAttribute("soPhongTrong", rooms.stream()
                .filter(phong -> isStatus(phong, "Trống") || phong.getTrangThai() == null || phong.getTrangThai().isBlank())
                .count());
        model.addAttribute("soPhongDangThue", rooms.stream()
                .filter(phong -> isStatus(phong, "Đang thuê"))
                .count());
        model.addAttribute("soPhongBaoTri", rooms.stream()
                .filter(phong -> isStatus(phong, "Bảo trì"))
                .count());
    }

    private boolean isStatus(Phong phong, String status) {
        return status.equals(phong.getTrangThai());
    }
}
