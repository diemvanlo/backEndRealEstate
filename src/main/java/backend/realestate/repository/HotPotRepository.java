package backend.realestate.repository;

import backend.realestate.model.HotPot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotPotRepository extends JpaRepository<HotPot, Long> {
}
