package com.jcf.persistence.repository;

import com.jcf.orm.core.EntityMapper;
import com.jcf.orm.core.Session;
import com.jcf.persistence.model.Currency;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class InfoForAccountRepository extends GenericRepository<Currency, Long> {
    public InfoForAccountRepository(Session<Currency, Long> session) {
        super(session, Currency.class);
    }

    public Currency findAllCurrency() {
        Optional<Currency> findAll = session.findAllCurrency(new EntityMapper(Currency.class));
        if (!findAll.isPresent())
            throw new IllegalArgumentException("currencies not found");
        return findAll.get();
    }
}
