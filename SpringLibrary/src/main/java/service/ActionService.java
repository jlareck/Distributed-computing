package service;

import models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ActionService {
    UserService userService;
    BookService bookService;
    ReadingService readingService;
    BookRequestService bookRequestService;

    @Autowired
    public ActionService(UserService userService, BookService bookService, ReadingService readingService,
                         BookRequestService bookRequestService) {
        this.userService = userService;
        this.bookService = bookService;
        this.readingService = readingService;
        this.bookRequestService = bookRequestService;
    }

    public void takeBook(long userId, int bookId) {
        Optional<User> user = userService.findById(userId);
        Optional<Book> book = bookService.findById(bookId);
        if (book.isPresent()) {
            int amount = book.get().getStock();
            book.get().setStock(amount - 1);
            if (user.isPresent()) {
                Reading reading = new Reading();
                reading.setBook(book.get());
                reading.setUser(user.get());
                readingService.saveReading(reading);
            }
        }
    }

    public void returnBook(long userId, int bookId) {
        Optional<User> user = userService.findById(userId);
        Optional<Book> book = bookService.findById(bookId);
        if (book.isPresent()) {
            int amount = book.get().getStock();
            book.get().setStock(amount + 1);
            if (user.isPresent()) {
                var reading = readingService.findByBookAndUser(book.get(), user.get());
                readingService.deleteReading(reading);
            }
        }
    }
    public void bookRequest(long userId, int bookId) {
        Optional<User> user = userService.findById(userId);
        Optional<Book> book = bookService.findById(bookId);
        if (book.isPresent()) {
            if (user.isPresent()) {
                BookRequest request = new BookRequest();
                request.setBook(book.get());
                request.setUser(user.get());
                request.setAccepted(false);
                bookRequestService.save(request);
            }
        }
    }

    public void cancelBookRequest(long userId, int bookId) {
        Optional<User> user = userService.findById(userId);
        Optional<Book> book = bookService.findById(bookId);
        if (book.isPresent()) {
            if (user.isPresent()) {
                BookRequest request = new BookRequest();
                request.setBook(book.get());
                request.setUser(user.get());
                request.setAccepted(false);
                bookRequestService.delete(request);
            }
        }
    }

}
