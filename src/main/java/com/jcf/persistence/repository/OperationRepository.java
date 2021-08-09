package com.jcf.persistence.repository;

import com.jcf.orm.core.Session;
import com.jcf.persistence.model.Operation;
import org.springframework.stereotype.Repository;

@Repository
public class OperationRepository extends GenericRepository<Operation, Long>{
    public OperationRepository(Session<Operation, Long> session) {
        super(session, Operation.class);
    }
}
