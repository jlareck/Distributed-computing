package converter;

import lombok.NoArgsConstructor;
import models.*;
import dto.*;

@NoArgsConstructor
public class BookConverter {

    static public BookDTO toDTO(Book entity) {
        BookDTO dto = new BookDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setStock(entity.getStock());
        return dto;
    }
    
    static public Book toEntity(BookDTO dto) {
        Book entity = new Book();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setStock(dto.getStock());
        return entity;
    }

}
