package service;

import converter.*;
import dto.*;
import models.*;
import repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LogicService {
    UserService userService;
    BookService bookService;
    ReadingService readingService;
    RequestBookService requestBookService;

    @Autowired
    public LogicService(UserService userService, BookService bookService, ReadingService readingService,
            RequestBookService requestBookService) {
        this.userService = userService;
        this.bookService = bookService;
        this.readingService = readingService;
        this.requestBookService = requestBookService;
    }

// TODO: remove vars, rename variables
    public void takeBook(long id_user, int id_book) {
        var user = userService.findById(id_user);
        var book = bookService.findById(id_book);
        if (book.isPresent()) {
            int amount = book.get().getStock();
            book.get().setStock(amount - 1);
            if (user.isPresent()) {
                Reading new_reading = new Reading();
                new_reading.setBook(book.get());
                new_reading.setUser(user.get());
                readingService.saveReading(new_reading);
            }
        }
    }
    public void returnBook(long id_user, int id_book) {
        var user = userService.findById(id_user);
        var book = bookService.findById(id_book);
        if (book.isPresent()) {
            int amount = book.get().getStock();
            book.get().setStock(amount + 1);
            if (user.isPresent()) {
                var reading = readingService.findByBookAndUser(book.get(), user.get());
                readingService.deleteReading(reading);
            }
        }
    }
    public void requestBook(long id_user, int id_book) {
        var user = userService.findById(id_user);
        var book = bookService.findById(id_book);
        if (book.isPresent()) {
            if (user.isPresent()) {
                RequestBook new_request = new RequestBook();
                new_request.setBook(book.get());
                new_request.setUser(user.get());
                new_request.setAccepted(false);
                requestBookService.save(new_request);
            }
        }
    }

    public void unrequestBook(long id_user, int id_book) {
        var user = userService.findById(id_user);
        var book = bookService.findById(id_book);
        if (book.isPresent()) {
            if (user.isPresent()) {
                RequestBook new_request = new RequestBook();
                new_request.setBook(book.get());
                new_request.setUser(user.get());
                new_request.setAccepted(false);
                requestBookService.delete(new_request);
            }
        }
    }

}
