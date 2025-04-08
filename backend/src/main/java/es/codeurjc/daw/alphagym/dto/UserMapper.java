package es.codeurjc.daw.alphagym.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.codeurjc.daw.alphagym.model.User;
import java.util.List;

@Mapper(componentModel = "spring") // Use "Spring" to be detected by Spring Boot
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    UserDTO toUserDTO(User user);

    User toUser(UserDTO userDTO);

    default List<UserDTO> toUserDTOs(List<User> users) {
        return users.stream().map(this::toUserDTO).toList();
    }
    
}