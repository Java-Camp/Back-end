package com.jcf.persistence.repository;

import com.jcf.orm.core.EntityMapper;
import com.jcf.orm.core.Session;
import com.jcf.persistence.model.AccountType;
import com.jcf.persistence.model.Category;
import com.jcf.persistence.model.Currency;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InfoForAccountRepository extends GenericRepository<Currency, Long> {
    public InfoForAccountRepository(Session<Currency, Long> session) {
        super(session, Currency.class);
    }

    public List<Currency> findAllCurrency() {
        return (List<Currency>) session.findAll(new EntityMapper(Currency.class));
    }

    public List<AccountType> findAllTypeOfAccount() {
        return (List<AccountType>) session.findAll(new EntityMapper(AccountType.class));
    }
    public List<Category> findAllCategory() {
        return (List<Category>) session.findAll(new EntityMapper(Category.class));
    }
}
