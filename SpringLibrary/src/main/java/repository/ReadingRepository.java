package repository;

import models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReadingRepository extends JpaRepository<Reading, Long> {
    List<Reading> findByUser(User user);
    List<Reading> findByBook(Book book);
}
