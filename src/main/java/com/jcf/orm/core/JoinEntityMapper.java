package com.jcf.orm.core;

import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JoinEntityMapper<E> implements RowMapper<E> {

    @Getter
    private final Class<E> entityClass;

    public JoinEntityMapper(Class<E> entityClass) {
        this.entityClass = entityClass;
    }


    @SneakyThrows
    @Override
    public E mapRow(ResultSet rs, int rowNum) throws SQLException {
        E entity = entityClass.getConstructor().newInstance();

        return null;
    }
}
