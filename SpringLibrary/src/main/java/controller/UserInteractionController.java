package controller;

import lombok.AllArgsConstructor;
import models.*;
import dto.*;
import service.*;
import converter.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class UserInteractionController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookRequestService bookRequestService;
    @Autowired
    private ActionService actionService;


    @RolesAllowed({ "admin", "user" })
    @GetMapping(value = "/list_books")
    public ResponseEntity<List<BookDTO>> getAllBooks(@RequestHeader String authorisation) {
        return ResponseEntity.ok(bookService.getBooks());
    }

    @RolesAllowed({ "user" })
    @PostMapping(value = "/book_request")
    public ResponseEntity<List<BookRequestDTO>> bookRequest(@RequestHeader String authorisation, int bookId) {
        User user = UserConverter.toEntity(userService.getUserDto(authorisation));
        actionService.bookRequest(user.getId(), bookId);
        return ResponseEntity.ok(bookRequestService.findByUser(user));
    }

    @RolesAllowed({ "user" })
    @PostMapping(value = "/cancel_book_request")
    public ResponseEntity<List<BookRequestDTO>> cancelBookRequest(@RequestHeader String authorisation, int bookId) {
        User user = UserConverter.toEntity(userService.getUserDto(authorisation));
        actionService.cancelBookRequest(user.getId(), bookId);
        return ResponseEntity.ok(bookRequestService.findByUser(user));
    }

    @RolesAllowed({ "admin", "user" })
    @GetMapping(value = "/list_requests")
    public ResponseEntity<List<BookRequestDTO>> getAllRequests(@RequestHeader String authorisation) {
        User user = UserConverter.toEntity(userService.getUserDto(authorisation));
        return ResponseEntity.ok(bookRequestService.findByUser(user));
    }

    @RolesAllowed({ "user" })
    @PostMapping(value = "/take_book")
    public ResponseEntity<List<BookRequestDTO>> takeBook(@RequestHeader String authorisation, int bookId) {
        User user = UserConverter.toEntity(userService.getUserDto(authorisation));
        actionService.takeBook(user.getId(), bookId);
        return ResponseEntity.ok(bookRequestService.findByUser(user));
    }

    @RolesAllowed({ "user" })
    @PostMapping(value = "/return_book")
    public ResponseEntity<List<BookRequestDTO>> returnBook(@RequestHeader String authorisation, int bookId) {
        User user = UserConverter.toEntity(userService.getUserDto(authorisation));
        actionService.returnBook(user.getId(), bookId);
        return ResponseEntity.ok(bookRequestService.findByUser(user));
    }


}
