package backend.realestate.repository;

import backend.realestate.model.Category;
import backend.realestate.model.CategoryCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryCustomerRepository extends JpaRepository<CategoryCustomer, Long> {
}
