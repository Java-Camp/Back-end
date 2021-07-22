package com.jcf.persistence.repository;

import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface CrudRepository<E, ID> {
    E save(E entity);

    Optional<E> findById(ID id);

    ResponseEntity delete(ID id);
}
