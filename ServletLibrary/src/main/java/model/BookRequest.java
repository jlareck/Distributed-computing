package model;

import lombok.Data;

@Data
public class BookRequest {
    private int userId;
    private int bookId;
    private boolean isAccepted;
}
