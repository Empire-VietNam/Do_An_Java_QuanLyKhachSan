package com.example.do_an_java.controller;

import com.example.do_an_java.entity.CtDichVu;
import com.example.do_an_java.entity.CtDichVuId;
import com.example.do_an_java.repository.CtDatPhongRepository;
import com.example.do_an_java.repository.CtDichVuRepository;
import com.example.do_an_java.repository.DichVuRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ct-dich-vu")
public class CtDichVuController {
    private final CtDichVuRepository ctDichVuRepository;
    private final DichVuRepository dichVuRepository;
    private final CtDatPhongRepository ctDatPhongRepository;

    public CtDichVuController(CtDichVuRepository ctDichVuRepository,
                              DichVuRepository dichVuRepository,
                              CtDatPhongRepository ctDatPhongRepository) {
        this.ctDichVuRepository = ctDichVuRepository;
        this.dichVuRepository = dichVuRepository;
        this.ctDatPhongRepository = ctDatPhongRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", ctDichVuRepository.findAll());
        return "ct-dich-vu/list";
    }

    @GetMapping("/them")
    public String add(Model model) {
        model.addAttribute("item", new CtDichVu());
        addSelectData(model);
        return "ct-dich-vu/form";
    }

    @PostMapping("/luu")
    public String save(@ModelAttribute("item") CtDichVu item) {
        if (item.getDichVu() != null && item.getCtDatPhong() != null) {
            item.setId(new CtDichVuId(
                    item.getDichVu().getMaDichVu(),
                    item.getCtDatPhong().getMaCtDatPhong()
            ));
        }
        ctDichVuRepository.save(item);
        return "redirect:/ct-dich-vu";
    }

    @GetMapping("/xoa")
    public String delete(@RequestParam Integer maDichVu, @RequestParam Integer maCtDatPhong) {
        ctDichVuRepository.deleteById(new CtDichVuId(maDichVu, maCtDatPhong));
        return "redirect:/ct-dich-vu";
    }

    private void addSelectData(Model model) {
        model.addAttribute("dichVus", dichVuRepository.findAll());
        model.addAttribute("datPhongs", ctDatPhongRepository.findAll());
    }
}
