package backend.realestate.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Thông tin không được để trống")
    private String name;

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String idAsString() {
        return id != null ? "" + id : null;
    }

    public Category(Long id, @NotBlank(message = "Thông tin không được để trống") String name) {
        this.id = id;
        this.name = name;
    }
}
