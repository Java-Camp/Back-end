package com.jcf.orm.core;

import com.jcf.orm.annotation.Entity;
import com.jcf.orm.annotation.Table;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
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
        for(int i = 0; i < fields.size(); i++) {
            if(fields.get(i) instanceof Date){
                Query.append(columnNames.get(i)).append(" = timestamp ?, ");
            } else{
                Query.append(columnNames.get(i)).append(" = ?, ");
            }
        }

        Query.setLength(Query.length()-2); // cut the ", "
        Query.append(" WHEN NOT MATCHED THEN INSERT (");

        // write every element's name and it's value
        for(String name: columnNames)
            Query.append(name).append(", ");

        Query.setLength(Query.length()-2);
        Query.append(") VALUES (");

        for (Object field : fields) {
            if (field instanceof Date) {
                Query.append("timestamp ?, ");
            } else {
                Query.append("?, ");
            }
        }

        Query.setLength(Query.length()-2);
        Query.append(")");

        log.info("Finished creating a Query:\n" + Query.toString());

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(final Connection conn) throws SQLException {
                final SimpleDateFormat sdfNew =
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                final PreparedStatement preparedStatement =
                        conn.prepareStatement(Query.toString());
                Object o;
                for(int i = 0; i < fields.size()*2; i++) {
                    o = fields.get(i % fields.size());
                    if(o instanceof Date) {
                        o = sdfNew.format(o);
                    }
                    log.info((i+1) + ") Added new Object: " + o);
                    preparedStatement.setObject(i + 1, o);
                }
                log.info("User was added to table");
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
    public void delete(ID id, EntityMapper<E> entityMapper) {
        String Query = "DELETE FROM \"" + getTableName(entityMapper) + "\" e WHERE e.id = ?";
        jdbcTemplate.update(Query, id);
    }

    @Override
    public Optional<E> findByEmail(String email, EntityMapper<E> entityMapper) {
        return jdbcTemplate.query("SELECT * FROM \"" + getTableName(entityMapper) + "\" e WHERE e.email = ?", entityMapper, email)
                .stream()
                .findAny();
    }

    @Override
    public Optional<E> findAllCurrency(EntityMapper<E> entityMapper) {
        return jdbcTemplate.query("SELECT name FROM" + getTableName(entityMapper),entityMapper)
                .stream().findAny();
    }
}