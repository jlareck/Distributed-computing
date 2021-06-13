package model;

import lombok.Data;

@Data
public class BookRequest {
    private long userId;
    private long bookId;
    private boolean isAccepted;

    public BookRequest(long id_user, long id_book, boolean accepted) {
        userId = id_user;
        bookId = id_book;
        isAccepted = accepted;
    }
}
