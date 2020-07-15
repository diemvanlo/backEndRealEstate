package backend.realestate.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "category_customer")
public class CategoryCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Thông tin không được bỏ trống")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "category_categorycustomer",
        joinColumns = @JoinColumn(name = "idCategoryCustomer"),
        inverseJoinColumns = @JoinColumn(name = "idCategory"))
    private List<Category> categories;

    @NotBlank(message = "Thông tin không được bỏ trống")
    private Integer IdKhachKhanh;

    private Integer soLuotXem;

    public CategoryCustomer() {
    }

    public Integer getIdKhachKhanh() {
        return IdKhachKhanh;
    }

    public void setIdKhachKhanh(Integer idKhachKhanh) {
        IdKhachKhanh = idKhachKhanh;
    }

    public Integer getSoLuotXem() {
        return soLuotXem;
    }

    public void setSoLuotXem(Integer soLuotXem) {
        this.soLuotXem = soLuotXem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public CategoryCustomer(Integer id, @NotBlank(message = "Thông tin không được bỏ trống") List<Category> categories, @NotBlank(message = "Thông tin không được bỏ trống") Integer idKhachKhanh, Integer soLuotXem) {
        this.id = id;
        this.categories = categories;
        IdKhachKhanh = idKhachKhanh;
        this.soLuotXem = soLuotXem;
    }
}
