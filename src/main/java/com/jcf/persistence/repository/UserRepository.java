package com.jcf.persistence.repository;

import com.jcf.orm.core.EntityMapper;
import com.jcf.orm.core.Session;
import com.jcf.persistence.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends GenericRepository<User, Long> {

    private Session session;

    public UserRepository(Session<User, Long> session) {
        super(session, User.class);
    }

    public User findByEmail(String email) {
        return (User)session.findByEmail(email, new EntityMapper(User.class)).get();
    }
}