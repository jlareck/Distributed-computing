package converter;

import lombok.NoArgsConstructor;
import models.*;
import dto.*;

@NoArgsConstructor
public class BookRequestConverter {

    public static BookRequestDTO toDTO(BookRequest entity) {
        BookRequestDTO dto = new BookRequestDTO();
        dto.setId(entity.getId());
        dto.setBook(entity.getBook());
        dto.setUser(entity.getUser());
        dto.setAccepted(entity.isAccepted());
        return dto;
    }

    public static BookRequest toEntity(BookRequestDTO dto) {
        BookRequest entity = new BookRequest();
        entity.setId(dto.getId());
        entity.setBook(dto.getBook());
        entity.setUser(dto.getUser());
        entity.setAccepted(dto.isAccepted());
        return entity;
    }

}
