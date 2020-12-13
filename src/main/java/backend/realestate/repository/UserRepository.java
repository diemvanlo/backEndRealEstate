package backend.realestate.repository;

import backend.realestate.model.News;
import backend.realestate.model.Role;
import backend.realestate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    List<User> getAllByRoles(Role role);

    @Query("SELECT c FROM User c WHERE c.email = ?1")
    public User findByEmail(String email);

    public User findByResetPasswordToken(String token);
}
