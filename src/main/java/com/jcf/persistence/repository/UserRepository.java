package com.jcf.persistence.repository;

import com.jcf.orm.core.EntityMapper;
import com.jcf.orm.core.Session;
import com.jcf.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository extends GenericRepository<User, Long> {


    public UserRepository(Session<User, Long> session) {
        super(session, User.class);
    }

    public User findByEmail(String email) {
        Optional<User> byEmail = session.findByEmail(email, new EntityMapper(User.class));
        if (!byEmail.isPresent())
            throw new IllegalArgumentException("user not found");
        return byEmail.get();
    }
}