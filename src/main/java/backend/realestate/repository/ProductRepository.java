package backend.realestate.repository;

import backend.realestate.model.Category;
import backend.realestate.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> getAllByProject_Id(Long projectId);

    @Query("SELECT id FROM Product ")
    List<Long> getAllID();

    List<Product> findAllByCreatedDateAfterOrderByCreatedDateDesc(Date createdDate);

    @Override
    Page<Product> findAll(Pageable pageable);

    @Query("SELECT count(*) FROM Product ")
    Long getItemCount();
//    @Query(value = "select count(n.id), n.createdDate from Product n group by n.createdDate")
    @Query(value = "select  count(n.id) as count , n.createdDate as date from Product n group by n.createdDate")
    List<Map<String, Object>> countAllByCreatedDate();
}
