package backend.realestate.repository;

import backend.realestate.model.Project;
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

    @Query("SELECT COUNT(*) FROM Product ")
    Long getItemCount();

    @Query(value = "SELECT COUNT(n.id) AS count, n.createdDate AS date FROM Product n GROUP BY n.createdDate")
    List<Map<String, Object>> countAllByCreatedDate();

    @Query("SELECT new map(p.project.tenDuAn AS projectName,COUNT(p.tenSanPham) as count) FROM Product p GROUP BY p.project.tenDuAn")
    Object [] countProductByProject();
}


