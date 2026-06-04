package com.example.do_an_java.controller;

import com.example.do_an_java.entity.CtDatPhong;
import com.example.do_an_java.repository.CtDatPhongRepository;
import com.example.do_an_java.repository.KhachHangRepository;
import com.example.do_an_java.repository.NhanVienRepository;
import com.example.do_an_java.repository.PhongRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dat-phong")
public class DatPhongController {
    private final CtDatPhongRepository ctDatPhongRepository;
    private final KhachHangRepository khachHangRepository;
    private final PhongRepository phongRepository;
    private final NhanVienRepository nhanVienRepository;

    public DatPhongController(CtDatPhongRepository ctDatPhongRepository,
                              KhachHangRepository khachHangRepository,
                              PhongRepository phongRepository,
                              NhanVienRepository nhanVienRepository) {
        this.ctDatPhongRepository = ctDatPhongRepository;
        this.khachHangRepository = khachHangRepository;
        this.phongRepository = phongRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page, Model model, HttpServletRequest request) {
        PaginationUtil.paginate(model, ctDatPhongRepository.findAll(), page, request);
        return "dat-phong/list";
    }

    @GetMapping("/them")
    public String add(Model model) {
        model.addAttribute("item", new CtDatPhong());
        addSelectData(model);
        return "dat-phong/form";
    }

    @GetMapping("/sua/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("item", ctDatPhongRepository.findById(id).orElseThrow());
        addSelectData(model);
        return "dat-phong/form";
    }

    @PostMapping("/luu")
    public String save(@ModelAttribute("item") CtDatPhong item) {
        ctDatPhongRepository.save(item);
        return "redirect:/dat-phong";
    }

    @GetMapping("/xoa/{id}")
    public String delete(@PathVariable Integer id) {
        ctDatPhongRepository.deleteById(id);
        return "redirect:/dat-phong";
    }

    private void addSelectData(Model model) {
        model.addAttribute("khachHangs", khachHangRepository.findAll());
        model.addAttribute("phongs", phongRepository.findAll());
        model.addAttribute("nhanViens", nhanVienRepository.findAll());
    }
}
