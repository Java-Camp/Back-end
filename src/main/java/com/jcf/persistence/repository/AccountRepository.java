package com.jcf.persistence.repository;

import com.jcf.orm.core.Session;
import com.jcf.persistence.model.Account;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository extends GenericRepository<Account, Long> {

    public AccountRepository(Session<Account, Long> session) {
        super(session, Account.class);
    }

}
