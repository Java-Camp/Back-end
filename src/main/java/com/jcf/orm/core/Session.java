package com.jcf.orm.core;

import java.util.List;
import java.util.Optional;

public interface Session<E, ID> {

    E saveOrUpdate(E entity, EntityMapper<E> rowMapper);

    Optional<E> findById(ID id, EntityMapper<E> rowMapper);

    Optional<E> findByEmail(String email,  EntityMapper<E> entityMapper);

    void delete(ID id, EntityMapper<E> entityMapper);



    List<E>findAll(EntityMapper<E> eEntityMapper);

    E findByUnique(String name, Object value ,EntityMapper<E> entityMapper);
}
