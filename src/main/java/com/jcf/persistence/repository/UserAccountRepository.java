package com.jcf.persistence.repository;

import com.jcf.orm.core.EntityMapper;
import com.jcf.orm.core.Session;
import com.jcf.persistence.model.UserAccount;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserAccountRepository extends GenericRepository<UserAccount, Long> {

    public UserAccountRepository(Session<UserAccount, Long> session) {
        super(session, UserAccount.class);
    }

    public List<UserAccount> findByUnique(String name, Object value) {
        return session.findByUnique(name, value, new EntityMapper(UserAccount.class));
    }
}
