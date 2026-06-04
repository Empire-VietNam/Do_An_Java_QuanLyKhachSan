package com.example.do_an_java.controller;

import com.example.do_an_java.entity.HoaDon;
import com.example.do_an_java.repository.CtDatPhongRepository;
import com.example.do_an_java.repository.HoaDonRepository;
import com.example.do_an_java.repository.NhanVienRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hoa-don")
public class HoaDonController {
    private final HoaDonRepository hoaDonRepository;
    private final CtDatPhongRepository ctDatPhongRepository;
    private final NhanVienRepository nhanVienRepository;

    public HoaDonController(HoaDonRepository hoaDonRepository,
                            CtDatPhongRepository ctDatPhongRepository,
                            NhanVienRepository nhanVienRepository) {
        this.hoaDonRepository = hoaDonRepository;
        this.ctDatPhongRepository = ctDatPhongRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", hoaDonRepository.findAll());
        return "hoa-don/list";
    }

    @GetMapping("/them")
    public String add(Model model) {
        model.addAttribute("item", new HoaDon());
        addSelectData(model);
        return "hoa-don/form";
    }

    @GetMapping("/sua/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("item", hoaDonRepository.findById(id).orElseThrow());
        addSelectData(model);
        return "hoa-don/form";
    }

    @PostMapping("/luu")
    public String save(@ModelAttribute("item") HoaDon item) {
        hoaDonRepository.save(item);
        return "redirect:/hoa-don";
    }

    @GetMapping("/xoa/{id}")
    public String delete(@PathVariable Integer id) {
        hoaDonRepository.deleteById(id);
        return "redirect:/hoa-don";
    }

    private void addSelectData(Model model) {
        model.addAttribute("datPhongs", ctDatPhongRepository.findAll());
        model.addAttribute("nhanViens", nhanVienRepository.findAll());
    }
}
