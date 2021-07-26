package com.jcf.persistence.repository;

import com.jcf.orm.core.Session;
import com.jcf.persistence.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository extends GenericRepository<User, Long> {

    private Session session;

    public UserRepository(Session<User, Long> session) {
        super(session, User.class);
    }

    public Optional<User> findByEmail(String email) {
        return session.;
    }
}