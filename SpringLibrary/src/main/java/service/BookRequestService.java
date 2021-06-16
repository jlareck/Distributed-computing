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
public class BookRequestService {
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

    public void save(BookRequest bookRequest) {
        repo.save(bookRequest);
    }

    public void delete(BookRequest bookRequest) {
        repo.delete(bookRequest);
    }
}
