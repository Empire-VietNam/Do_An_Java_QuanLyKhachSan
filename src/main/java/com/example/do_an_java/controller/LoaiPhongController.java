package com.example.do_an_java.controller;

import com.example.do_an_java.entity.LoaiPhong;
import com.example.do_an_java.repository.LoaiPhongRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/loai-phong")
public class LoaiPhongController {
    private final LoaiPhongRepository loaiPhongRepository;

    public LoaiPhongController(LoaiPhongRepository loaiPhongRepository) {
        this.loaiPhongRepository = loaiPhongRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", loaiPhongRepository.findAll());
        return "loai-phong/list";
    }

    @GetMapping("/them")
    public String add(Model model) {
        model.addAttribute("item", new LoaiPhong());
        return "loai-phong/form";
    }

    @GetMapping("/sua/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("item", loaiPhongRepository.findById(id).orElseThrow());
        return "loai-phong/form";
    }

    @PostMapping("/luu")
    public String save(@ModelAttribute("item") LoaiPhong item) {
        loaiPhongRepository.save(item);
        return "redirect:/loai-phong";
    }

    @GetMapping("/xoa/{id}")
    public String delete(@PathVariable Integer id) {
        loaiPhongRepository.deleteById(id);
        return "redirect:/loai-phong";
    }
}
