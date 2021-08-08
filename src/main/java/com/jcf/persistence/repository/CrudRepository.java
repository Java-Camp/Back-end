package com.jcf.persistence.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<E, ID> {
    E saveOrUpdate(E entity);

    Optional<E> findById(ID id);

    void delete(ID id);

    E findByUnique(String name, Object value);

    List<E> findAll();
}