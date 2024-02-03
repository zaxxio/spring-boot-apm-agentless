package org.wsd.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.wsd.app.dto.User;
import org.wsd.app.dto.UserDTO;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);

    User toEmployee(UserDTO userDTO);

    @Mapping(target = "id", ignore = true)
    UserDTO createUserDTOWithoutId(User user);
}