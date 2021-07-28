package com.jcf.persistence.repository;

import com.jcf.orm.core.Session;
import com.jcf.persistence.model.DateTest;
import org.springframework.stereotype.Repository;

@Repository
public class DateTestRepository extends GenericRepository<DateTest, Long> {
    public DateTestRepository(Session<DateTest, Long> session) {
        super(session, DateTest.class);
    }
}
