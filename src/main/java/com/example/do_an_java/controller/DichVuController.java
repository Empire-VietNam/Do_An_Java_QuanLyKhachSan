package com.example.do_an_java.controller;

import com.example.do_an_java.entity.DichVu;
import com.example.do_an_java.repository.DichVuRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dich-vu")
public class DichVuController {
    private final DichVuRepository dichVuRepository;

    public DichVuController(DichVuRepository dichVuRepository) {
        this.dichVuRepository = dichVuRepository;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "") String keyword, Model model) {
        model.addAttribute("keyword", keyword);
        model.addAttribute("items", keyword.isBlank()
                ? dichVuRepository.findAll()
                : dichVuRepository.findByTenDichVuContainingIgnoreCase(keyword));
        return "dich-vu/list";
    }

    @GetMapping("/them")
    public String add(Model model) {
        model.addAttribute("item", new DichVu());
        return "dich-vu/form";
    }

    @GetMapping("/sua/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("item", dichVuRepository.findById(id).orElseThrow());
        return "dich-vu/form";
    }

    @PostMapping("/luu")
    public String save(@ModelAttribute("item") DichVu item) {
        dichVuRepository.save(item);
        return "redirect:/dich-vu";
    }

    @GetMapping("/xoa/{id}")
    public String delete(@PathVariable Integer id) {
        dichVuRepository.deleteById(id);
        return "redirect:/dich-vu";
    }
}
