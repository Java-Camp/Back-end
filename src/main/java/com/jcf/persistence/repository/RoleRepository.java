package com.jcf.persistence.repository;

import com.jcf.orm.core.Session;
import com.jcf.persistence.model.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepository extends GenericRepository<Role, Long> {

    public RoleRepository(Session<Role, Long> session) {
        super(session, Role.class);
    }
}
