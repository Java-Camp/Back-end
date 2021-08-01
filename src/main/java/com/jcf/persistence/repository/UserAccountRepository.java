package com.jcf.persistence.repository;

import com.jcf.orm.core.Session;
import com.jcf.persistence.model.UserAccount;
import org.springframework.stereotype.Repository;

@Repository
public class UserAccountRepository extends GenericRepository<UserAccount, Long> {

    public UserAccountRepository(Session<UserAccount, Long> session) {
        super(session, UserAccount.class);
    }
}
