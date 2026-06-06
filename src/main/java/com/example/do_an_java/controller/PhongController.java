package com.example.do_an_java.controller;

import com.example.do_an_java.entity.Phong;
import com.example.do_an_java.repository.LoaiPhongRepository;
import com.example.do_an_java.repository.PhongRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/phong")
public class PhongController {
    private final PhongRepository phongRepository;
    private final LoaiPhongRepository loaiPhongRepository;

    public PhongController(PhongRepository phongRepository, LoaiPhongRepository loaiPhongRepository) {
        this.phongRepository = phongRepository;
        this.loaiPhongRepository = loaiPhongRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", phongRepository.findAll());
        return "phong/list";
    }

    @GetMapping("/them")
    public String add(Model model) {
        model.addAttribute("item", new Phong());
        model.addAttribute("loaiPhongs", loaiPhongRepository.findAll());
        return "phong/form";
    }

    @GetMapping("/sua/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("item", phongRepository.findById(id).orElseThrow());
        model.addAttribute("loaiPhongs", loaiPhongRepository.findAll());
        return "phong/form";
    }

    @PostMapping("/luu")
    public String save(@ModelAttribute("item") Phong item) {
        phongRepository.save(item);
        return "redirect:/phong";
    }

    @GetMapping("/xoa/{id}")
    public String delete(@PathVariable Integer id) {
        phongRepository.deleteById(id);
        return "redirect:/phong";
    }
}
