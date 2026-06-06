package com.example.do_an_java.controller;

import com.example.do_an_java.entity.CtDatPhong;
import com.example.do_an_java.entity.CtDichVu;
import com.example.do_an_java.entity.KhachHang;
import com.example.do_an_java.entity.TrangThaiDatPhong;
import com.example.do_an_java.repository.CtDatPhongRepository;
import com.example.do_an_java.repository.CtDichVuRepository;
import com.example.do_an_java.repository.KhachHangRepository;
import com.example.do_an_java.repository.NhanVienRepository;
import com.example.do_an_java.repository.PhongRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/dat-phong")
public class DatPhongController {

    private final CtDatPhongRepository ctDatPhongRepository;
    private final KhachHangRepository khachHangRepository;
    private final PhongRepository phongRepository;
    private final NhanVienRepository nhanVienRepository;
    private final CtDichVuRepository ctDichVuRepository;

    // Constructor - THÊM ctDichVuRepository
    public DatPhongController(CtDatPhongRepository ctDatPhongRepository,
                              KhachHangRepository khachHangRepository,
                              PhongRepository phongRepository,
                              NhanVienRepository nhanVienRepository,
                              CtDichVuRepository ctDichVuRepository) {
        this.ctDatPhongRepository = ctDatPhongRepository;
        this.khachHangRepository = khachHangRepository;
        this.phongRepository = phongRepository;
        this.nhanVienRepository = nhanVienRepository;
        this.ctDichVuRepository = ctDichVuRepository;
    }

    // ==================== 1. DANH SÁCH (có lọc theo trạng thái) ====================
    @GetMapping
    public String list(@RequestParam(defaultValue = "") String trangThai, Model model) {
        List<CtDatPhong> danhSach;
        if (!trangThai.isBlank()) {
            try {
                TrangThaiDatPhong tt = TrangThaiDatPhong.valueOf(trangThai);
                danhSach = ctDatPhongRepository.findByTrangThai(tt);
            } catch (IllegalArgumentException e) {
                danhSach = ctDatPhongRepository.findAll();
            }
        } else {
            danhSach = ctDatPhongRepository.findAll();
        }

        model.addAttribute("items", danhSach);
        model.addAttribute("trangThaiHienTai", trangThai);
        model.addAttribute("cacTrangThai", TrangThaiDatPhong.values());
        return "dat-phong/list";
    }

    // ==================== 2. CHỌN KHÁCH HÀNG (chức năng 2) ====================
    @GetMapping("/chon-khach")
    public String chonKhach(Model model) {
        model.addAttribute("khachHangs", khachHangRepository.findAll());
        model.addAttribute("khachMoi", new KhachHang());
        return "dat-phong/chon-khach";
    }

    // Tìm kiếm khách hàng (chức năng 1)
    @GetMapping("/tim-khach")
    public String timKhach(@RequestParam String keyword, Model model) {
        List<KhachHang> ketQua;
        if (keyword != null && !keyword.isBlank()) {
            ketQua = khachHangRepository.timKiemTheoNhieuTruong(keyword);
        } else {
            ketQua = khachHangRepository.findAll();
        }
        model.addAttribute("khachHangs", ketQua);
        model.addAttribute("khachMoi", new KhachHang());
        model.addAttribute("keyword", keyword);
        return "dat-phong/chon-khach";
    }

    // Chọn khách cũ để tạo đơn
    @GetMapping("/tao-don/{maKhachHang}")
    public String taoDonVoiKhachCu(@PathVariable Integer maKhachHang, Model model) {
        KhachHang khachHang = khachHangRepository.findById(maKhachHang)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        CtDatPhong datPhong = new CtDatPhong();
        datPhong.setKhachHang(khachHang);
        datPhong.setNgayThucHien(LocalDate.now());
        datPhong.setTrangThai(TrangThaiDatPhong.DA_DAT);

        model.addAttribute("item", datPhong);
        addSelectData(model);
        model.addAttribute("khachChon", khachHang);
        return "dat-phong/form";
    }

    // Thêm khách hàng mới (chức năng 2 - phần thêm mới)
    @PostMapping("/them-khach-moi")
    public String themKhachMoi(@ModelAttribute KhachHang khachMoi,
                               RedirectAttributes redirectAttributes) {
        // Tạo mã khách hàng tạm thời (dùng timestamp)
        int maMoi = (int) (System.currentTimeMillis() % 100000);
        khachMoi.setMaKhachHang(maMoi);
        khachHangRepository.save(khachMoi);

        redirectAttributes.addFlashAttribute("success",
                "Đã thêm khách hàng mới: " + khachMoi.getHoTen());
        return "redirect:/dat-phong/tao-don/" + maMoi;
    }

    // ==================== 3. THÊM / SỬA ĐƠN ĐẶT PHÒNG ====================
    @GetMapping("/them")
    public String add(Model model) {
        CtDatPhong datPhong = new CtDatPhong();
        datPhong.setNgayThucHien(LocalDate.now());
        datPhong.setTrangThai(TrangThaiDatPhong.DA_DAT);
        model.addAttribute("item", datPhong);
        addSelectData(model);
        model.addAttribute("khachHangs", khachHangRepository.findAll());
        model.addAttribute("nhanViens", nhanVienRepository.findAll());
        return "dat-phong/form-cu";
    }

    @GetMapping("/sua/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        CtDatPhong datPhong = ctDatPhongRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đặt phòng"));
        model.addAttribute("item", datPhong);
        addSelectData(model);
        model.addAttribute("khachChon", datPhong.getKhachHang());
        return "dat-phong/form";
    }

    @PostMapping("/luu")
    public String save(@ModelAttribute("item") CtDatPhong item,
                       RedirectAttributes redirectAttributes) {

        //  Chức năng 3: Kiểm tra ngày nhận phải nhỏ hơn ngày trả
        if (item.getNgayNhan() != null && item.getNgayTra() != null) {
            if (item.getNgayNhan().isAfter(item.getNgayTra()) ||
                    item.getNgayNhan().isEqual(item.getNgayTra())) {
                redirectAttributes.addFlashAttribute("error",
                        "Ngày nhận phải nhỏ hơn ngày trả!");
                return "redirect:/dat-phong/them";
            }
        }

        // Chức năng 4: Kiểm tra phòng trống
        if (!kiemTraPhongTrong(item)) {
            redirectAttributes.addFlashAttribute("error",
                    "Phòng đã được đặt trong khoảng thời gian này! Vui lòng chọn phòng khác hoặc đổi ngày.");
            return "redirect:/dat-phong/sua/" + (item.getMaCtDatPhong() != null ? item.getMaCtDatPhong() : "");
        }

        // Set trạng thái mặc định nếu chưa có
        if (item.getTrangThai() == null) {
            item.setTrangThai(TrangThaiDatPhong.DA_DAT);
        }

        // Set ngày thực hiện nếu chưa có
        if (item.getNgayThucHien() == null) {
            item.setNgayThucHien(LocalDate.now());
        }

        ctDatPhongRepository.save(item);
        redirectAttributes.addFlashAttribute("success", "Lưu đặt phòng thành công!");
        return "redirect:/dat-phong";
    }

    // Kiểm tra phòng trống (chức năng 4)
    private boolean kiemTraPhongTrong(CtDatPhong datPhong) {
        if (datPhong.getPhong() == null) return true;
        if (datPhong.getNgayNhan() == null || datPhong.getNgayTra() == null) return true;

        List<CtDatPhong> trungLich = ctDatPhongRepository.kiemTraPhongTrong(
                datPhong.getPhong().getMaPhong(),
                datPhong.getNgayNhan(),
                datPhong.getNgayTra()
        );

        // Nếu đang update thì loại trừ chính nó
        if (datPhong.getMaCtDatPhong() != null) {
            trungLich.removeIf(dp -> dp.getMaCtDatPhong().equals(datPhong.getMaCtDatPhong()));
        }

        return trungLich.isEmpty();
    }

    // ==================== 4. CẬP NHẬT TRẠNG THÁI (chức năng 5) ====================
    @GetMapping("/cap-nhat-trang-thai/{id}")
    public String capNhatTrangThai(@PathVariable Integer id,
                                   @RequestParam TrangThaiDatPhong trangThai,
                                   RedirectAttributes redirectAttributes) {
        CtDatPhong datPhong = ctDatPhongRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đặt phòng"));
        datPhong.setTrangThai(trangThai);
        ctDatPhongRepository.save(datPhong);

        redirectAttributes.addFlashAttribute("success",
                "Đã cập nhật trạng thái thành: " + trangThai.getTenHienThi());
        return "redirect:/dat-phong";
    }

    // ==================== 5. XÓA ĐẶT PHÒNG ====================
    @GetMapping("/xoa/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        ctDatPhongRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Đã xóa đặt phòng!");
        return "redirect:/dat-phong";
    }

    // ==================== 6. CHI TIẾT ĐƠN ĐẶT PHÒNG (có tính tiền) ====================
    @GetMapping("/chi-tiet/{id}")
    public String chiTiet(@PathVariable Integer id, Model model) {
        CtDatPhong datPhong = ctDatPhongRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đặt phòng"));

        List<CtDichVu> dichVuDaDung = ctDichVuRepository.findByCtDatPhong_MaCtDatPhong(id);

        // Tính số ngày ở
        long soNgay = ChronoUnit.DAYS.between(
                datPhong.getNgayNhan(),
                datPhong.getNgayTra()
        );

        // Tính tiền phòng
        long tienPhong = soNgay * datPhong.getPhong().getLoaiPhong().getGiaPhong();

        // Tính tổng tiền dịch vụ
        int tongTienDichVu = dichVuDaDung.stream()
                .mapToInt(CtDichVu::getTongTienDichVu)
                .sum();

        long tongCong = tienPhong + tongTienDichVu;

        model.addAttribute("datPhong", datPhong);
        model.addAttribute("dichVuDaDung", dichVuDaDung);
        model.addAttribute("soNgay", soNgay);           // 🔥 THÊM DÒNG NÀY
        model.addAttribute("tienPhong", tienPhong);
        model.addAttribute("tongTienDichVu", tongTienDichVu);
        model.addAttribute("tongCong", tongCong);

        return "dat-phong/chi-tiet";
    }
    // ==================== HÀM HỖ TRỢ ====================
    private void addSelectData(Model model) {
        model.addAttribute("phongs", phongRepository.findAll());
        model.addAttribute("nhanViens", nhanVienRepository.findAll());
        model.addAttribute("cacTrangThai", TrangThaiDatPhong.values());
    }
    // API thêm khách hàng qua AJAX
    @PostMapping("/them-khach-moi-ajax")
    @ResponseBody
    public Map<String, Object> themKhachMoiAjax(@RequestBody Map<String, String> khachData) {
        Map<String, Object> result = new HashMap<>();
        try {
            int maMoi = (int) (System.currentTimeMillis() % 100000);
            KhachHang khachMoi = new KhachHang();
            khachMoi.setMaKhachHang(maMoi);
            khachMoi.setHoTen(khachData.get("hoTen"));
            khachMoi.setDienThoai(khachData.get("dienThoai"));
            khachMoi.setCmnd(khachData.get("cmnd"));
            khachMoi.setEmail(khachData.get("email"));
            khachMoi.setDiaChi(khachData.get("diaChi"));

            khachHangRepository.save(khachMoi);

            result.put("success", true);
            result.put("khachHang", khachMoi);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}