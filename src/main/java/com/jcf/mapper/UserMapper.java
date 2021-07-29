package com.jcf.mapper;

import com.jcf.persistence.model.User;
import com.jcf.persistence.model.dto.AccountDTO;
import com.jcf.persistence.model.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;


    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDTO convertToDto(User entity){
        return modelMapper.map(entity, UserDTO.class);
    }

    public User convertToEntity(UserDTO dto){
        return modelMapper.map(dto, User.class);
    }

}
