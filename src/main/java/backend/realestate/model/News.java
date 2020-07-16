package backend.realestate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 3, max = 100, message = "Title phải lớn hơn 3 và bé hơn 100")
    @NotBlank(message = "Thông tin không được để trống")
    private String title;

    private Integer views;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("comments")
    private List<Comment> comments;

    public News() {
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public News(Long id, @Size(min = 3, max = 100, message = "Title phải lớn hơn 3 và bé hơn 100") @NotBlank(message = "Thông tin không được để trống") String title, Integer views) {
        this.id = id;
        this.title = title;
        this.views = views;
    }
}
