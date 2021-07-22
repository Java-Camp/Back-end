package com.jcf.persistence.repository;

import com.jcf.orm.core.Session;
import com.jcf.persistence.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends GenericRepository<User, Long> {

    public UserRepository(Session<User, Long> session) {
        super(session, User.class);
    }
}