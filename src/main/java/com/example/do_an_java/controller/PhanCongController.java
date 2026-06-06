package com.example.do_an_java.controller;

import com.example.do_an_java.entity.BangPhanCong;
import com.example.do_an_java.repository.BangPhanCongRepository;
import com.example.do_an_java.repository.NhanVienRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/phan-cong")
public class PhanCongController {
    private final BangPhanCongRepository bangPhanCongRepository;
    private final NhanVienRepository nhanVienRepository;

    public PhanCongController(BangPhanCongRepository bangPhanCongRepository,
                              NhanVienRepository nhanVienRepository) {
        this.bangPhanCongRepository = bangPhanCongRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page, Model model, HttpServletRequest request) {
        PaginationUtil.paginate(model, bangPhanCongRepository.findAll(), page, request);
        return "phan-cong/list";
    }

    @GetMapping("/them")
    public String add(Model model) {
        model.addAttribute("item", new BangPhanCong());
        model.addAttribute("nhanViens", nhanVienRepository.findAll());
        return "phan-cong/form";
    }

    @GetMapping("/sua/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("item", bangPhanCongRepository.findById(id).orElseThrow());
        model.addAttribute("nhanViens", nhanVienRepository.findAll());
        return "phan-cong/form";
    }

    @PostMapping("/luu")
    public String save(@ModelAttribute("item") BangPhanCong item) {
        bangPhanCongRepository.save(item);
        return "redirect:/phan-cong";
    }

    @GetMapping("/xoa/{id}")
    public String delete(@PathVariable Integer id) {
        bangPhanCongRepository.deleteById(id);
        return "redirect:/phan-cong";
    }
}
