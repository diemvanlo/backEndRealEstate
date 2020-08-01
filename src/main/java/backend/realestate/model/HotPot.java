package backend.realestate.model;

import javax.persistence.*;

@Entity
@Table(name = "hotpot")
public class HotPot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "rootImage_id")
    Image rootImage;

    @ManyToOne
    @JoinColumn(name = "linkedImage_id")
    Image linkedImage;

    private Double xIndex;
    private Double yIndex;
    private Double zIndex;
    private Double imgScale;

}
