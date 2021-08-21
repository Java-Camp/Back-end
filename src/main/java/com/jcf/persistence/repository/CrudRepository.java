package com.jcf.persistence.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<E, ID> {
    E saveOrUpdate(E entity);

    Optional<E> findById(ID id);

    void delete(ID id);

    List<E> findAll();

    List<E> findByUnique(String name, Object value);

/*    @Query("SELECT SUM(:values) FROM #{#entityName} where :column = :columnValue")
    Optional<Object> count(@Param("values") String values,
                      @Param("column") String column,
                      @Param("columnValue") Object columnValue);*/

}