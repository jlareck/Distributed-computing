package service;

import converter.*;
import dto.*;
import models.*;
import repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestBookService {
    @Autowired
    BookRequestRepository repo;

    public List<BookRequestDTO> findByUser(User user) {
        var entities = repo.findByUser(user);
        var dtos = new ArrayList<BookRequestDTO>();
        for (BookRequest entity: entities)
        {
            dtos.add(BookRequestConverter.toDTO(entity));
        }
        return dtos;
    }

    public List<BookRequest> findByBook(Book book) {
        return repo.findByBook(book);
    }

    public void save(BookRequest requestBook) {
        repo.save(requestBook);
    }

    public void delete(BookRequest requestBook) {
        repo.delete(requestBook);
    }
}
