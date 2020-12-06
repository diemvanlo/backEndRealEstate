package backend.realestate.repository;

import backend.realestate.model.Category;
import backend.realestate.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("SELECT count(*) FROM News")
    Long getItemCount();

    //    @Query("SELECT count(*) FROM news n ")
    @Query(value = "select count(n.id), n.createdDate from News n group by n.createdDate")
    List<Object[]> countAllByCreatedDate();
}
