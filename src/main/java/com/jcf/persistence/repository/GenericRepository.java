package com.jcf.persistence.repository;

import com.jcf.orm.core.EntityMapper;
import com.jcf.orm.core.Session;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public abstract class GenericRepository<E, ID> implements CrudRepository<E, ID> {

    private final Session<E, ID> session;

    private final Class<E> entityClass;

    @Autowired
    public GenericRepository(Session<E, ID> session, Class<E> entityClass) {
        this.session = session;
        this.entityClass = entityClass;
    }

    @Override
    public E save(E entity) {
        return session.save(entity, new EntityMapper<>(entityClass));
    }

    @Override
    public Optional<E> findById(ID id) {
        return session.findById(id, new EntityMapper<>(entityClass));
    }
}
