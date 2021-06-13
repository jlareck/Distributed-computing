package model;

import lombok.Data;

@Data
public class BookRequest {
    private long userId;
    private long bookId;
    private boolean isAccepted;

    public BookRequest(long userId, long bookId, boolean accepted) {
        userId = userId;
        bookId = bookId;
        isAccepted = accepted;
    }
}
