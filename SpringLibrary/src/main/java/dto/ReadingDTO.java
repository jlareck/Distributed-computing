package dto;
import lombok.Data;
import models.*;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReadingDTO {
    private long id;
    private User user;
    private Book book;
    private boolean accepted;
}
