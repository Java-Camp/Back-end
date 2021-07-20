package com.jcf.persistence.repository;

import com.jcf.persistence.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserRepository implements CrudRepository<User, Long> {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public User save(User entity) {
        return null; // Oleh implement me
    }

    @Override
    public Optional<User> findById(Long id) {
        if (Objects.isNull(id)) return Optional.empty();
        return Optional.ofNullable(entityManager.find(User.class, id));
    }
}
