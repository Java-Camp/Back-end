package com.jcf.persistence.repository;



import com.jcf.orm.core.EntityMapper;
import com.jcf.orm.core.Session;
import com.jcf.persistence.model.Operation;
import com.jcf.persistence.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class OperationRepository extends  GenericRepository <Operation, Long>{


    public OperationRepository(Session<Operation, Long> session) {

        super(session, Operation.class);
    }





}
