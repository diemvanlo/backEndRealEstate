package backend.realestate.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Ảnh không được bỏ trống")
    @Lob
    private String image;

    @NotBlank(message = "Thông tin không được bỏ trống")
    private Integer idSanPham;

    @NotBlank(message = "Thông tin không được bỏ trống")
    private String dinhDang;

    @ManyToOne
    private Product product;

    public Image() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(Integer idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getDinhDang() {
        return dinhDang;
    }

    public void setDinhDang(String dinhDang) {
        this.dinhDang = dinhDang;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Image(Integer id, @NotBlank(message = "Ảnh không được bỏ trống") String image, @NotBlank(message = "Thông tin không được bỏ trống") Integer idSanPham, @NotBlank(message = "Thông tin không được bỏ trống") String dinhDang, Product product) {
        this.id = id;
        this.image = image;
        this.idSanPham = idSanPham;
        this.dinhDang = dinhDang;
        this.product = product;
    }
}
