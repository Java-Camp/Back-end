package com.jcf.mapper;

import com.jcf.persistence.model.dto.request.UserAddDto;
import com.jcf.persistence.model.dto.response.UserResponseDto;
import com.jcf.persistence.model.dto.request.UserUpdateDto;
import com.jcf.persistence.model.entity.User;

public class CustomUserMapper {

    public static User convertToEntity(UserAddDto dto){

        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        return user;
        
    }
    public static User convertToUpEntity(UserUpdateDto dto){

        User user = convertToEntity(dto);
        return user;

    }

    public static UserResponseDto convertToDto(User user){

        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());

        return dto;
    }
}
