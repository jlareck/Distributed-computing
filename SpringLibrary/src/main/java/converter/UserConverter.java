package converter;

import lombok.NoArgsConstructor;
import models.*;
import dto.*;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UserConverter {
    static public UserDTO toDTO(User entity) {
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setLastname(entity.getLastname());
        dto.setLogin(entity.getLogin());
        dto.setPassword(entity.getPassword());
        dto.setAdmin(entity.isAdmin());
        dto.setRole(entity.getRole());
        return dto;
    }

    static public User toEntity(UserDTO dto){
        User entity = new User();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setLastname(dto.getLastname());
        entity.setLogin(dto.getLogin());
        entity.setPassword(dto.getPassword());
        entity.setAdmin(dto.isAdmin());
        return entity;
    }

}
