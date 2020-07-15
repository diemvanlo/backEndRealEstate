package backend.realestate.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Size(min = 3, max = 100, message = "Title phải lớn hơn 3 và bé hơn 100")
    @NotBlank(message = "Thông tin không được để trống")
    private String title;

    private Integer views;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments;

    public News() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public News(Integer id, @Size(min = 3, max = 100, message = "Title phải lớn hơn 3 và bé hơn 100") @NotBlank(message = "Thông tin không được để trống") String title, Integer views) {
        this.id = id;
        this.title = title;
        this.views = views;
    }
}
