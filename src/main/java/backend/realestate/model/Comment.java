package backend.realestate.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer soSao;

    @NotBlank
    private String linhVuc;

    @ManyToOne
    private News news;

    public Comment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSoSao() {
        return soSao;
    }

    public void setSoSao(Integer soSao) {
        this.soSao = soSao;
    }

    public String getLinhVuc() {
        return linhVuc;
    }

    public void setLinhVuc(String linhVuc) {
        this.linhVuc = linhVuc;
    }

    public Comment(Integer id, Integer soSao, @NotBlank String linhVuc, News news) {
        this.id = id;
        this.soSao = soSao;
        this.linhVuc = linhVuc;
        this.news = news;
    }
}
