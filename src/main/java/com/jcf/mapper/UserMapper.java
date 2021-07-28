package com.jcf.mapper;

import com.jcf.persistence.model.User;
import com.jcf.persistence.model.dto.UserDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    User toDto (UserDTO userDTO);

    @Mapping(target = "userDTO.password", ignore = true)
    UserDTO toEntity (User user);
}
