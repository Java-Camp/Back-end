package com.jcf.orm.core;

import com.jcf.orm.annotation.Entity;
import com.jcf.orm.annotation.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class SessionImpl<E, ID> implements Session<E, ID> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SessionImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String getTableName(EntityMapper<E> entityMapper){
        return entityMapper.getEntityClass().getAnnotation(Table.class).name();
    }

    @Override
    public E saveOrUpdate(E entity, EntityMapper<E> entityMapper) {
        // checking
        Assert.notNull(entity, "Entity must be a set");
        if (!entityMapper.getEntityClass().isAnnotationPresent(Entity.class))
            throw new IllegalArgumentException(entityMapper.getEntityClass().getSimpleName() + " is not specified as @Entity");

        Long id = entityMapper.getId(entity);
        List <Object> fields = entityMapper.getFields(entity);
        List <String> columnNames = entityMapper.getAllColumnNames();

        if(fields.size() != columnNames.size()) // fields and columnNames have to have the same size
            throw new RuntimeException( "Number of fields (" + fields.size() + ") and number of all column Names (" + columnNames.size() +") is no equal");

        StringBuilder Query = new StringBuilder("MERGE INTO \"" + getTableName(entityMapper) + "\" I "
                + "USING (SELECT " + id + " as id FROM DUAL) S "
                + "ON (S.id = I.id) "
                + "WHEN MATCHED THEN "
                + "UPDATE SET ");

        // write every element's name and it's value
        for(int i = 0; i < fields.size(); i++)
            Query.append(columnNames.get(i)).append(" = ?, ");

        Query.setLength(Query.length()-2); // cut the ", "
        Query.append(" WHEN NOT MATCHED THEN INSERT (");

        // write every element's name and it's value
        for(String name: columnNames)
            Query.append(name).append(", ");

        Query.setLength(Query.length()-2);
        Query.append(") VALUES (");

        for (int i = 0; i < fields.size(); i++)
            Query.append("?, ");

        Query.setLength(Query.length()-2);
        Query.append(")");

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(final Connection conn) throws SQLException {
                final PreparedStatement preparedStatement = conn.prepareStatement(Query.toString());
                for(int i = 0; i < fields.size()*2; i++)
                    preparedStatement.setObject(i+1, fields.get(i%fields.size()));

                return preparedStatement;
            }
        });

        return entity;
    }

    @Override
    public Optional<E> findById(ID id, EntityMapper<E> entityMapper) {
        return jdbcTemplate.query("SELECT * FROM \"" + getTableName(entityMapper) + "\" e WHERE e.id = ?", entityMapper, id)
                .stream()
                .findAny();
    }

    @Override
    public ResponseEntity delete(ID id, EntityMapper<E> entityMapper) { // todo change to void after Service
        if(findById(id, entityMapper).isEmpty())
            return ResponseEntity.status(404).body("No entity with such id");

        String Query = "DELETE FROM \"" + getTableName(entityMapper) + "\" e WHERE e.id = ?";
        jdbcTemplate.update(Query, id);
        return ResponseEntity.status(200).body("Entity was deleted");
    }
}