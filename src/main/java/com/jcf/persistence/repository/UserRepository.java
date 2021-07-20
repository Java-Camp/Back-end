package com.jcf.persistence.repository;

import com.jcf.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Optional;

@Repository
public class UserRepository implements CrudRepository<User, Long> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User save(User entity) {
        Assert.notNull(entity, "Entity must be a set");
        String Query;
        if (Objects.isNull(entity.getId())) {
            Query = "INSERT INTO \"user\" (first_name, last_name, email, password, id) VALUES (?, ?, ?, ?, ?)";
        }
        else{
            Query = "UPDATE \"user\" SET first_name = ?, last_name = ?, email = ?, password = ?  where id = ?";
        }
        jdbcTemplate.update(Query, entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getPassword(), entity.getId());
        return entity;
    }

    @Override
    public Optional<User> findById(Long id) {
        if (Objects.isNull(id)) return Optional.empty();
        // todo: logic
        return Optional.empty();
    }
}
