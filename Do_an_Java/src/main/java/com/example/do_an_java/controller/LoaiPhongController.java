package com.example.do_an_java.controller;

import com.example.do_an_java.entity.LoaiPhong;
import com.example.do_an_java.repository.LoaiPhongRepository;
import com.example.do_an_java.repository.PhongRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/loai-phong")
public class LoaiPhongController {
    private final LoaiPhongRepository loaiPhongRepository;
    private final PhongRepository phongRepository;

    public LoaiPhongController(LoaiPhongRepository loaiPhongRepository, PhongRepository phongRepository) {
        this.loaiPhongRepository = loaiPhongRepository;
        this.phongRepository = phongRepository;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page, Model model, HttpServletRequest request) {
        PaginationUtil.paginate(model, loaiPhongRepository.findByDaXoaFalseOrDaXoaIsNull(), page, request);
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
        if (item.getDaXoa() == null) {
            item.setDaXoa(false);
        }

        loaiPhongRepository.save(item);
        return "redirect:/loai-phong";
    }

    @GetMapping("/xoa/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        long soPhongDangDung = phongRepository.countByLoaiPhong_MaLoaiPhong(id);
        if (soPhongDangDung > 0) {
            redirectAttributes.addFlashAttribute("error",
                    "Không thể xóa loại phòng này vì còn " + soPhongDangDung + " phòng đang sử dụng.");
            return "redirect:/loai-phong";
        }

        LoaiPhong loaiPhong = loaiPhongRepository.findById(id).orElseThrow();
        loaiPhong.setDaXoa(true);
        loaiPhongRepository.save(loaiPhong);
        redirectAttributes.addFlashAttribute("success", "Đã xóa loại phòng này.");
        return "redirect:/loai-phong";
    }
}
