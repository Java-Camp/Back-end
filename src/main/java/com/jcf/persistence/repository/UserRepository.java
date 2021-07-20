package com.jcf.persistence.repository;

import com.jcf.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
        return null; // Oleh implement me
    }

    @Override
    public Optional<User> findById(Long id) {
        if (Objects.isNull(id)) return Optional.empty();
        // todo: logic
        return Optional.empty();
    }
}
