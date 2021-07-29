package com.jcf.service;

import com.jcf.persistence.model.Role;
import com.jcf.persistence.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService implements GenericService<Role, Long> {

    private final RoleRepository roleRepository;

    @Override
    public Role save(Role entity) {
        return null;
    }

    @Override
    public Role getById(Long id) {
        return roleRepository.findById(id).get();
    }

    @Override
    public List<Role> getAll() {
        return null;
    }
}
