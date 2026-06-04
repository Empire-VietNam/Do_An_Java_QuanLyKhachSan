package com.example.do_an_java.controller;

import com.example.do_an_java.entity.CtDanhGia;
import com.example.do_an_java.repository.CtDanhGiaRepository;
import com.example.do_an_java.repository.PhongRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/danh-gia")
public class DanhGiaController {
    private final CtDanhGiaRepository ctDanhGiaRepository;
    private final PhongRepository phongRepository;

    public DanhGiaController(CtDanhGiaRepository ctDanhGiaRepository,
                             PhongRepository phongRepository) {
        this.ctDanhGiaRepository = ctDanhGiaRepository;
        this.phongRepository = phongRepository;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page, Model model, HttpServletRequest request) {
        PaginationUtil.paginate(model, ctDanhGiaRepository.findAll(), page, request);
        return "danh-gia/list";
    }

    @GetMapping("/them")
    public String add(Model model) {
        model.addAttribute("item", new CtDanhGia());
        model.addAttribute("phongs", phongRepository.findAll());
        return "danh-gia/form";
    }

    @GetMapping("/sua/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("item", ctDanhGiaRepository.findById(id).orElseThrow());
        model.addAttribute("phongs", phongRepository.findAll());
        return "danh-gia/form";
    }

    @PostMapping("/luu")
    public String save(@ModelAttribute("item") CtDanhGia item) {
        ctDanhGiaRepository.save(item);
        return "redirect:/danh-gia";
    }

    @GetMapping("/xoa/{id}")
    public String delete(@PathVariable Integer id) {
        ctDanhGiaRepository.deleteById(id);
        return "redirect:/danh-gia";
    }
}
