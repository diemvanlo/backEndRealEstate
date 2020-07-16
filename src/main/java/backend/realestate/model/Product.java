package backend.realestate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 3, max = 50, message = "Tên phải lớn hơn 3 và bé hơn 50")
    @NotBlank(message = "Thông tin không được bỏ trống")
    private String tenSanPham;

    @NotBlank(message = "Thông tin không được bỏ trống")
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Size(max = 200, message = "Địa chỉ phải trong vòng 200 ký tự")
    @NotBlank(message = "Thông tin không được bỏ trống")
    private String diaChi;

    @NotBlank(message = "Thông tin không được bỏ trống")
    private Double dienTich;

    @NotBlank(message = "Thông tin không được bỏ trống")
    private Double giaTien;

    @Size(max = 200, message = "Mô tả phải trong vòng 200 ký tự")
    @NotBlank(message = "Thông tin không được bỏ trống")
    private String moTa;

    @DateTimeFormat(pattern = "MM-dd-yyyy")
    @NotBlank(message = "Thông tin không được bỏ trống")
    private Date ngayTao;

    @NotBlank(message = "Thông tin không được bỏ trống")
    private String tienDo;

    @NotBlank(message = "Thông tin không được bỏ trống")
    private Integer trangThai;

    private Integer views;

    @OneToMany(mappedBy = "product",
            cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnoreProperties("images")
    private List<Image> images;

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Double getDienTich() {
        return dienTich;
    }

    public void setDienTich(Double dienTich) {
        this.dienTich = dienTich;
    }

    public Double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(Double giaTien) {
        this.giaTien = giaTien;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getTienDo() {
        return tienDo;
    }

    public void setTienDo(String tienDo) {
        this.tienDo = tienDo;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Product(Long id, @Size(min = 3, max = 50, message = "Tên phải lớn hơn 3 và bé hơn 50") @NotBlank(message = "Thông tin không được bỏ trống") String tenSanPham, @NotBlank(message = "Thông tin không được bỏ trống") Project project, @Size(max = 200, message = "Địa chỉ phải trong vòng 200 ký tự") @NotBlank(message = "Thông tin không được bỏ trống") String diaChi, @NotBlank(message = "Thông tin không được bỏ trống") Double dienTich, @NotBlank(message = "Thông tin không được bỏ trống") Double giaTien, @Size(max = 200, message = "Mô tả phải trong vòng 200 ký tự") @NotBlank(message = "Thông tin không được bỏ trống") String moTa, @NotBlank(message = "Thông tin không được bỏ trống") Date ngayTao, @NotBlank(message = "Thông tin không được bỏ trống") String tienDo, @NotBlank(message = "Thông tin không được bỏ trống") Integer trangThai, Integer views, List<Image> images) {
        this.id = id;
        this.tenSanPham = tenSanPham;
        this.project = project;
        this.diaChi = diaChi;
        this.dienTich = dienTich;
        this.giaTien = giaTien;
        this.moTa = moTa;
        this.ngayTao = ngayTao;
        this.tienDo = tienDo;
        this.trangThai = trangThai;
        this.views = views;
        this.images = images;
    }
}
