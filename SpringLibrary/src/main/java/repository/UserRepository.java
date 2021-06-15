package repository;

import models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPassword(String password);
    Optional<User> findById(long id);
    List<User> findByLogin(String login);
}
