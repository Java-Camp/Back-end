package com.jcf.persistence.repository;

import com.jcf.orm.core.EntityMapper;
import com.jcf.orm.core.Session;
import com.jcf.persistence.model.AccountType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TypeOfAccountRepository extends GenericRepository<AccountType, Long> {
    public TypeOfAccountRepository(Session<AccountType, Long> session) {
        super(session, AccountType.class);
    }

    public List<AccountType> findAllTypeOfAccount() {
        return (List<AccountType>) session.findAll(new EntityMapper(AccountType.class));
    }
}
