package com.example.do_an_java.controller;

import com.example.do_an_java.entity.ChucVu;
import com.example.do_an_java.entity.NhanVien;
import com.example.do_an_java.repository.ChucVuRepository;
import com.example.do_an_java.repository.NhanVienRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/nhan-vien")
public class NhanVienController {

    private final NhanVienRepository nhanVienRepository;
    private final ChucVuRepository chucVuRepository;

    public NhanVienController(NhanVienRepository nhanVienRepository, ChucVuRepository chucVuRepository) {
        this.nhanVienRepository = nhanVienRepository;
        this.chucVuRepository = chucVuRepository;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "") String keyword, Model model) {
        model.addAttribute("keyword", keyword);
        model.addAttribute("items", keyword == null || keyword.isBlank()
                ? nhanVienRepository.findAll()
                : nhanVienRepository.findByTenNhanVienContainingIgnoreCase(keyword));
        return "nhan-vien/list";
    }

    @GetMapping("/them")
    public String add(Model model) {
        model.addAttribute("item", new NhanVien());
        model.addAttribute("chucVus", chucVuRepository.findAll());
        return "nhan-vien/form";
    }

    @GetMapping("/sua/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        NhanVien nhanVien = nhanVienRepository.findById(id).orElseThrow();
        model.addAttribute("item", nhanVien);
        model.addAttribute("chucVus", chucVuRepository.findAll());
        return "nhan-vien/form";
    }

    @PostMapping("/luu")
    public String save(@ModelAttribute("item") NhanVien item,
                       @RequestParam(value = "maChucVu", required = false) Integer maChucVu) {
        if (maChucVu != null) {
            ChucVu chucVu = chucVuRepository.findById(maChucVu).orElse(null);
            item.setChucVu(chucVu);
        } else {
            item.setChucVu(null);
        }
        nhanVienRepository.save(item);
        return "redirect:/nhan-vien";
    }

    @GetMapping("/xoa/{id}")
    public String delete(@PathVariable Integer id) {
        nhanVienRepository.deleteById(id);
        return "redirect:/nhan-vien";
    }
}
