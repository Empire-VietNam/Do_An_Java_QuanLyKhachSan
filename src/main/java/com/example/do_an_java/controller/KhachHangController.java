package com.example.do_an_java.controller;

import com.example.do_an_java.entity.KhachHang;
import com.example.do_an_java.repository.KhachHangRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/khach-hang")
public class KhachHangController {
    private final KhachHangRepository khachHangRepository;

    public KhachHangController(KhachHangRepository khachHangRepository) {
        this.khachHangRepository = khachHangRepository;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "") String keyword, Model model) {
        model.addAttribute("keyword", keyword);
        model.addAttribute("items", keyword.isBlank()
                ? khachHangRepository.findAll()
                : khachHangRepository.findByHoTenContainingIgnoreCase(keyword));
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
}
