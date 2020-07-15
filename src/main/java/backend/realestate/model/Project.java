package backend.realestate.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Size(min = 3, max = 100, message = "Tên dự án phải lớn hơn 3 và ít hơn 100")
    @NotBlank(message = "Thông tin không được bỏ trống")
    private String tenDuAn;

    @Size(max = 100, message = "Tên loại hình phải trong vòng 100 ký tự")
    @NotBlank(message = "Thông tin không được bỏ trống")
    private String loaiHinh;

    @Size(max = 200, message = "Địa chỉ phải trong vòng 200 ký tự")
    @NotBlank(message = "Thông tin không được bỏ trống")
    private String diaChi;

    @NotBlank(message = "Thông tin không được bỏ trống")
    private String dienTich;

    @NotBlank(message = "Thông tin không được bỏ trống")
    private String chiPhiDuAn;

    @DateTimeFormat(pattern = "MM-dd-yyyy")
    @NotBlank(message = "Thông tin không được bỏ trống")
    private Date ngayBatDau;

    @ManyToMany(mappedBy = "projects")
    private Collection<Partner> partners;

    @Size(max = 100)
    private String trangThai;

    @NotBlank
    private Double mapX;

    @NotBlank
    private Double mapY;

    @NotBlank
    private Double banKinh;

    public Project() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenDuAn() {
        return tenDuAn;
    }

    public void setTenDuAn(String tenDuAn) {
        this.tenDuAn = tenDuAn;
    }

    public String getLoaiHinh() {
        return loaiHinh;
    }

    public void setLoaiHinh(String loaiHinh) {
        this.loaiHinh = loaiHinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getDienTich() {
        return dienTich;
    }

    public void setDienTich(String dienTich) {
        this.dienTich = dienTich;
    }

    public String getChiPhiDuAn() {
        return chiPhiDuAn;
    }

    public void setChiPhiDuAn(String chiPhiDuAn) {
        this.chiPhiDuAn = chiPhiDuAn;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Collection<Partner> getPartners() {
        return partners;
    }

    public void setPartners(Collection<Partner> partners) {
        this.partners = partners;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Double getMapX() {
        return mapX;
    }

    public void setMapX(Double mapX) {
        this.mapX = mapX;
    }

    public Double getMapY() {
        return mapY;
    }

    public void setMapY(Double mapY) {
        this.mapY = mapY;
    }

    public Double getBanKinh() {
        return banKinh;
    }

    public void setBanKinh(Double banKinh) {
        this.banKinh = banKinh;
    }

    public Project(Integer id, @Size(min = 3, max = 100, message = "Tên dự án phải lớn hơn 3 và ít hơn 100") @NotBlank(message = "Thông tin không được bỏ trống") String tenDuAn, @Size(max = 100, message = "Tên loại hình phải trong vòng 100 ký tự") @NotBlank(message = "Thông tin không được bỏ trống") String loaiHinh, @Size(max = 200, message = "Địa chỉ phải trong vòng 200 ký tự") @NotBlank(message = "Thông tin không được bỏ trống") String diaChi, @NotBlank(message = "Thông tin không được bỏ trống") String dienTich, @NotBlank(message = "Thông tin không được bỏ trống") String chiPhiDuAn, @NotBlank(message = "Thông tin không được bỏ trống") Date ngayBatDau, Collection<Partner> partners, @Size(max = 100) String trangThai, @NotBlank Double mapX, @NotBlank Double mapY, @NotBlank Double banKinh) {
        this.id = id;
        this.tenDuAn = tenDuAn;
        this.loaiHinh = loaiHinh;
        this.diaChi = diaChi;
        this.dienTich = dienTich;
        this.chiPhiDuAn = chiPhiDuAn;
        this.ngayBatDau = ngayBatDau;
        this.partners = partners;
        this.trangThai = trangThai;
        this.mapX = mapX;
        this.mapY = mapY;
        this.banKinh = banKinh;
    }
}
