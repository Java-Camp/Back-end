package com.jcf.mapper;

import com.jcf.persistence.model.Role;
import com.jcf.persistence.model.dto.RoleDTO;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {

    Role toDto (RoleDTO roleDTO);

    RoleDTO toEntity (Role role);
}
