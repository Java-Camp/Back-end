package com.jcf.persistence.repository;

import java.util.Optional;

public interface CrudRepository<E, ID> {
    E save(E entity);

    Optional<E> findById(ID id);
}
