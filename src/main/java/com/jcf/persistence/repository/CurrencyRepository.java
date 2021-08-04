package com.jcf.persistence.repository;

import com.jcf.orm.core.EntityMapper;
import com.jcf.orm.core.Session;
import com.jcf.persistence.model.AccountType;
import com.jcf.persistence.model.Category;
import com.jcf.persistence.model.Currency;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CurrencyRepository extends GenericRepository<Currency, Long> {
    public CurrencyRepository(Session<Currency, Long> session) {
        super(session, Currency.class);
    }

    public List<Currency> findAllCurrency() {
        return session.findAll(new EntityMapper(Currency.class));
    }


}