package com.jcf.persistence.repository;

import com.jcf.persistence.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

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
        Assert.notNull(entity, "Entity must be a set");
        if (Objects.isNull(entity.getId())) {
            entityManager.persist(entity);
        } else {
            return entityManager.merge(entity);
        }
        return entity;
    }

    @Override
    public Optional<User> findById(Long id) {
        if (Objects.isNull(id)) return Optional.empty();
        return Optional.ofNullable(entityManager.find(User.class, id));
    }
}
