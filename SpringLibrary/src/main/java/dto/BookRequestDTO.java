package dto;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.*;

@Data
@NoArgsConstructor
public class BookRequestDTO {
    private long id;
    private User user;
    private Book book;
    private boolean isAccepted;
}
