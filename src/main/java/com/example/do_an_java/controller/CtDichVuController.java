package com.example.do_an_java.controller;

import com.example.do_an_java.entity.CtDichVu;
import com.example.do_an_java.entity.CtDichVuId;
import com.example.do_an_java.repository.CtDatPhongRepository;
import com.example.do_an_java.repository.CtDichVuRepository;
import com.example.do_an_java.repository.DichVuRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // ==================== CHỨC NĂNG 7: THÊM DỊCH VỤ VÀO ĐƠN ĐẶT PHÒNG ====================

    @GetMapping("/them-vao-don")
    public String themVaoDon(@RequestParam Integer maCtDatPhong, Model model) {
        model.addAttribute("maCtDatPhong", maCtDatPhong);
        model.addAttribute("item", new CtDichVu());
        model.addAttribute("dichVus", dichVuRepository.findAll());
        model.addAttribute("datPhong", ctDatPhongRepository.findById(maCtDatPhong).orElse(null));
        return "ct-dich-vu/them-vao-don";
    }

    @PostMapping("/luu-vao-don")
    public String luuVaoDon(@ModelAttribute CtDichVu item,
                            @RequestParam Integer maCtDatPhong,
                            RedirectAttributes redirectAttributes) {

        // Kiểm tra dữ liệu đầu vào
        if (item.getDichVu() == null || item.getDichVu().getMaDichVu() == null) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng chọn dịch vụ!");
            return "redirect:/ct-dich-vu/them-vao-don?maCtDatPhong=" + maCtDatPhong;
        }

        if (item.getSoLuong() == null || item.getSoLuong() <= 0) {
            redirectAttributes.addFlashAttribute("error", "Số lượng phải lớn hơn 0!");
            return "redirect:/ct-dich-vu/them-vao-don?maCtDatPhong=" + maCtDatPhong;
        }

        // Lấy đối tượng đặt phòng
        var datPhong = ctDatPhongRepository.findById(maCtDatPhong)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đặt phòng"));

        // Lấy đối tượng dịch vụ
        var dichVu = dichVuRepository.findById(item.getDichVu().getMaDichVu())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy dịch vụ"));

        // Gán vào item
        item.setCtDatPhong(datPhong);
        item.setDichVu(dichVu);

        // Tạo khóa chính
        item.setId(new CtDichVuId(
                dichVu.getMaDichVu(),
                maCtDatPhong
        ));

        // Lưu (tổng tiền sẽ được tính tự động trong entity)
        ctDichVuRepository.save(item);

        redirectAttributes.addFlashAttribute("success",
                "Đã thêm dịch vụ '" + dichVu.getTenDichVu() + "' x " + item.getSoLuong() + " thành công!");
        return "redirect:/dat-phong/chi-tiet/" + maCtDatPhong;
    }

    // ==================== HÀM HỖ TRỢ ====================

    private void addSelectData(Model model) {
        model.addAttribute("dichVus", dichVuRepository.findAll());
        model.addAttribute("datPhongs", ctDatPhongRepository.findAll());
    }
}