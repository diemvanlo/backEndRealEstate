package backend.realestate.repository;

import backend.realestate.model.Category;
import backend.realestate.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> getAllByProject_Id(Long projectId);

    @Override
    Page<Product> findAll(Pageable pageable);
}
