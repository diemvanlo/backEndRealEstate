package backend.realestate.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    private String image;

    @NotBlank(message = "Thông tin không được bỏ trống")
    private String dinhDang;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Image() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public Image(Long id, @NotBlank(message = "Ảnh không được bỏ trống") String image, @NotBlank(message = "Thông tin không được bỏ trống") Long idSanPham, @NotBlank(message = "Thông tin không được bỏ trống") String dinhDang, Product product) {
        this.id = id;
        this.image = image;
        this.dinhDang = dinhDang;
        this.product = product;
    }
}
