package com.jcf.service;

import com.jcf.persistence.model.User;

import java.util.List;

public interface GenericService<E, ID> {

    E save(E entity);

    E getById(ID id);

    List<E> getAll();
}
