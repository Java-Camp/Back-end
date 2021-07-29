package com.jcf.mapper;

import com.jcf.persistence.model.Role;
import com.jcf.persistence.model.User;
import com.jcf.persistence.model.dto.RoleDTO;
import com.jcf.persistence.model.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    private final ModelMapper modelMapper;

    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RoleDTO convertToDto(Role entity){
        return modelMapper.map(entity, RoleDTO.class);
    }

    public Role convertToEntity(RoleDTO dto){
        return modelMapper.map(dto, Role.class);
    }
}
