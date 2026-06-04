DROP DATABASE IF EXISTS QUANLYKHACHSAN;
CREATE DATABASE QUANLYKHACHSAN CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE QUANLYKHACHSAN;

CREATE TABLE CTDANHGIA
(
    ID INT AUTO_INCREMENT PRIMARY KEY,
    NoiDung VARCHAR(255),
    NgayDanhGia DATE,
    MaPhong INT
);

CREATE TABLE CHUCVU
(
    MaChucVu INT AUTO_INCREMENT PRIMARY KEY,
    TenChucVu VARCHAR(100)
);

CREATE TABLE NHANVIEN
(
    MaNhanVien INT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(20),
    `Password` VARCHAR(30),
    TenNhanVien VARCHAR(100),
    NgaySinh DATE,
    CMND INT,
    NgayVaoLam DATE,
    MaChucVu INT
);

CREATE TABLE BANGPHANCONG
(
    MaPhanCong INT AUTO_INCREMENT PRIMARY KEY,
    NgayPhanCong DATE,
    LoaiCongViec VARCHAR(50),
    MaNhanVien INT
);

CREATE TABLE KHACHHANG
(
    MaKhachHang INT PRIMARY KEY,
    HoTen VARCHAR(255),
    Email VARCHAR(30),
    DiaChi VARCHAR(50),
    DienThoai VARCHAR(20),
    CMND VARCHAR(20)
);

CREATE TABLE HOADON
(
    MaHoaDon INT AUTO_INCREMENT PRIMARY KEY,
    NgayThuTien DATE,
    SoTienThu INT,
    MaCTDatPhong INT,
    MaNhanVien INT
);

CREATE TABLE CTDICHVU
(
    MaCTDichVu INT,
    MaDichVu INT,
    SoLuong INT,
    MaCTDatPhong INT,
    TongTienDichVu INT,
    CONSTRAINT PK_CTDICHVU PRIMARY KEY (MaDichVu, MaCTDatPhong)
);

CREATE TABLE DICHVU
(
    MaDichVu INT AUTO_INCREMENT PRIMARY KEY,
    TenDichVu VARCHAR(50),
    GiaDichVu INT
);

CREATE TABLE LOAIPHONG
(
    MaLoaiPhong INT AUTO_INCREMENT PRIMARY KEY,
    TenLoaiPhong VARCHAR(20),
    GiaPhong INT,
    MoTa VARCHAR(255),
    DaXoa BOOLEAN DEFAULT FALSE
);

CREATE TABLE PHONG
(
    MaPhong INT PRIMARY KEY,
    MaLoaiPhong INT,
    TrangThai VARCHAR(30) DEFAULT 'Trống'
);

CREATE TABLE CTDATPHONG
(
    MaCTDatPhong INT PRIMARY KEY,
    NgayThucHien DATE,
    MaKhachHang INT,
    NgayNhan DATE,
    NgayTra DATE,
    MaPhong INT,
    MaNhanVien INT
);

-- Dữ liệu mẫu
INSERT INTO CHUCVU(TenChucVu) VALUES ('Quan Ly');
INSERT INTO CHUCVU(TenChucVu) VALUES ('Le tan');
INSERT INTO CHUCVU(TenChucVu) VALUES ('Bao ve');
INSERT INTO CHUCVU(TenChucVu) VALUES ('Tap vu');

INSERT INTO NHANVIEN(Username, `Password`, TenNhanVien, NgaySinh, CMND, NgayVaoLam, MaChucVu)
VALUES ('hieu', '1', 'Le Minh Hieu', '1998-11-15', 225786369, '2019-05-05', 1);
INSERT INTO NHANVIEN(Username, `Password`, TenNhanVien, NgaySinh, CMND, NgayVaoLam, MaChucVu)
VALUES ('duc', '1', 'Nguyen Phuc Duc', '1998-08-09', 225764728, '2019-05-18', 2);
INSERT INTO NHANVIEN(Username, `Password`, TenNhanVien, NgaySinh, CMND, NgayVaoLam, MaChucVu)
VALUES ('hai', '1', 'Tran Ngoc Hai', '1998-03-20', 225712598, '2019-06-11', 2);
INSERT INTO NHANVIEN(Username, `Password`, TenNhanVien, NgaySinh, CMND, NgayVaoLam, MaChucVu)
VALUES ('duy', '1', 'Duy Zoan', '1998-06-07', 225896128, '2019-07-02', 2);

INSERT INTO LOAIPHONG(TenLoaiPhong, GiaPhong, MoTa) VALUES ('Bình Dân', 300000, 'Một giường đôi. Máy lạnh');
INSERT INTO LOAIPHONG(TenLoaiPhong, GiaPhong, MoTa) VALUES ('Thương Gia', 20000000, 'Một giường đôi. Máy lạnh. View ngắm biển. Tivi. Có phòng làm việc');
INSERT INTO LOAIPHONG(TenLoaiPhong, GiaPhong, MoTa) VALUES ('Vip', 4000000, 'Một giường đôi. Máy lạnh. View ngắm biển. Tivi');

INSERT INTO PHONG(MaPhong, MaLoaiPhong) VALUES (101, 1);
INSERT INTO PHONG(MaPhong, MaLoaiPhong) VALUES (102, 1);
INSERT INTO PHONG(MaPhong, MaLoaiPhong) VALUES (103, 1);
INSERT INTO PHONG(MaPhong, MaLoaiPhong) VALUES (104, 1);
INSERT INTO PHONG(MaPhong, MaLoaiPhong) VALUES (105, 1);
INSERT INTO PHONG(MaPhong, MaLoaiPhong) VALUES (201, 2);
INSERT INTO PHONG(MaPhong, MaLoaiPhong) VALUES (202, 2);
INSERT INTO PHONG(MaPhong, MaLoaiPhong) VALUES (203, 2);
INSERT INTO PHONG(MaPhong, MaLoaiPhong) VALUES (204, 2);
INSERT INTO PHONG(MaPhong, MaLoaiPhong) VALUES (205, 2);
INSERT INTO PHONG(MaPhong, MaLoaiPhong) VALUES (301, 3);
INSERT INTO PHONG(MaPhong, MaLoaiPhong) VALUES (302, 3);
INSERT INTO PHONG(MaPhong, MaLoaiPhong) VALUES (303, 3);
INSERT INTO PHONG(MaPhong, MaLoaiPhong) VALUES (304, 3);
INSERT INTO PHONG(MaPhong, MaLoaiPhong) VALUES (305, 3);

INSERT INTO DICHVU(TenDichVu, GiaDichVu) VALUES ('An Sang', 30000);
INSERT INTO DICHVU(TenDichVu, GiaDichVu) VALUES ('An Trua', 35000);
INSERT INTO DICHVU(TenDichVu, GiaDichVu) VALUES ('An Toi', 35000);
INSERT INTO DICHVU(TenDichVu, GiaDichVu) VALUES ('Massage', 600000);
INSERT INTO DICHVU(TenDichVu, GiaDichVu) VALUES ('Du thuyen', 1000000);

INSERT INTO KHACHHANG(MaKhachHang, HoTen, Email, DiaChi, DienThoai, CMND)
VALUES (1, 'Le Van Teo', 'teo@gmail.com', 'Ha Noi', '123123132', '258963147');
INSERT INTO KHACHHANG(MaKhachHang, HoTen, Email, DiaChi, DienThoai, CMND)
VALUES (2, 'Le Van Tí', 'ti@gmail.com', 'Ho Chi Minh', '123253132', '258963147');
INSERT INTO KHACHHANG(MaKhachHang, HoTen, Email, DiaChi, DienThoai, CMND)
VALUES (3, 'Le Van Kheo', 'khoe@gmail.com', 'Da Nang', '123169132', '258963147');
INSERT INTO KHACHHANG(MaKhachHang, HoTen, Email, DiaChi, DienThoai, CMND)
VALUES (4, 'Nguyen Phuc Duc', 'duc@gmail.com', 'Da Nang', '123169132', '258963147');
INSERT INTO KHACHHANG(MaKhachHang, HoTen, Email, DiaChi, DienThoai, CMND)
VALUES (5, 'Le Minh Hieu', 'hieu@gmail.com', 'Long An', '253169132', '258925647');

INSERT INTO CTDATPHONG(MaCTDatPhong, NgayThucHien, MaKhachHang, NgayNhan, NgayTra, MaPhong, MaNhanVien)
VALUES (1, '1998-11-15', 1, '2019-05-05', '2019-05-07', 101, 2);
INSERT INTO CTDATPHONG(MaCTDatPhong, NgayThucHien, MaKhachHang, NgayNhan, NgayTra, MaPhong, MaNhanVien)
VALUES (2, '1998-11-15', 2, '2019-05-06', '2019-05-08', 102, 2);
INSERT INTO CTDATPHONG(MaCTDatPhong, NgayThucHien, MaKhachHang, NgayNhan, NgayTra, MaPhong, MaNhanVien)
VALUES (3, '1998-11-15', 3, '2019-05-08', '2019-05-10', 201, 3);
INSERT INTO CTDATPHONG(MaCTDatPhong, NgayThucHien, MaKhachHang, NgayNhan, NgayTra, MaPhong, MaNhanVien)
VALUES (4, '1998-11-15', 4, '2019-05-08', '2019-05-10', 103, 3);
INSERT INTO CTDATPHONG(MaCTDatPhong, NgayThucHien, MaKhachHang, NgayNhan, NgayTra, MaPhong, MaNhanVien)
VALUES (5, '1998-11-15', 5, '2019-06-11', '2019-06-13', 201, 3);

INSERT INTO HOADON(NgayThuTien, SoTienThu, MaCTDatPhong, MaNhanVien)
VALUES ('2019-05-09', 150000, 2, 2);
INSERT INTO HOADON(NgayThuTien, SoTienThu, MaCTDatPhong, MaNhanVien)
VALUES ('2019-05-10', 200000, 2, 3);
INSERT INTO HOADON(NgayThuTien, SoTienThu, MaCTDatPhong, MaNhanVien)
VALUES ('2019-05-11', 3000000, 3, 4);
INSERT INTO HOADON(NgayThuTien, SoTienThu, MaCTDatPhong, MaNhanVien)
VALUES ('2019-05-11', 350000, 3, 4);
INSERT INTO HOADON(NgayThuTien, SoTienThu, MaCTDatPhong, MaNhanVien)
VALUES ('2019-06-12', 350000, 5, 2);

-- =========================================================
-- CAC THAY DOI BO SUNG TRONG QUA TRINH LAM CHUC NANG PHONG
-- Neu da import database truoc khi sua file SQL nay, chay cac lenh duoi day
-- trong MySQL Workbench de cap nhat database hien tai.
-- =========================================================

-- 1. Them trang thai phong: Trong, Da dat, Dang thue, Bao tri
ALTER TABLE PHONG
ADD COLUMN TrangThai VARCHAR(30) DEFAULT 'Trống';
--
UPDATE PHONG
SET TrangThai = 'Trống'
WHERE TrangThai IS NULL OR TrangThai = '';

-- 2. Them cot xoa mem cho loai phong
ALTER TABLE LOAIPHONG
ADD COLUMN DaXoa BOOLEAN DEFAULT FALSE;
--
UPDATE LOAIPHONG
SET DaXoa = FALSE
WHERE DaXoa IS NULL;

-- 3. Cap nhat ten loai phong va mo ta sang tieng Viet co dau
UPDATE LOAIPHONG
SET TenLoaiPhong = 'Bình Dân',
     MoTa = 'Một giường đôi. Máy lạnh'
WHERE MaLoaiPhong = 1;
--
UPDATE LOAIPHONG
SET TenLoaiPhong = 'Thương Gia',
     MoTa = 'Một giường đôi. Máy lạnh. View ngắm biển. Tivi. Có phòng làm việc'
WHERE MaLoaiPhong = 2;
--
UPDATE LOAIPHONG
 SET TenLoaiPhong = 'Vip',
     MoTa = 'Một giường đôi. Máy lạnh. View ngắm biển. Tivi'
 WHERE MaLoaiPhong = 3;
