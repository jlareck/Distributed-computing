package repository;

import models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRequestRepository extends JpaRepository<BookRequest, Long> {
    List<BookRequest> findByBook(Book book);
    List<BookRequest> findByUser(User user);
}
