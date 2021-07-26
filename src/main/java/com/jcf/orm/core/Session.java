package com.jcf.orm.core;

import java.util.Optional;

public interface Session<E, ID> {

    E save(E entity, EntityMapper<E> rowMapper);

    Optional<E> findById(ID id, EntityMapper<E> rowMapper);
}
