package com.jcf.orm.core;

import com.jcf.orm.annotation.Entity;
import com.jcf.orm.annotation.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
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
    public E save(E entity, EntityMapper<E> entityMapper) {
        Assert.notNull(entity, "Entity must be a set");
        if (!entityMapper.getEntityClass().isAnnotationPresent(Entity.class))
            throw new IllegalArgumentException(entityMapper.getEntityClass().getSimpleName() + " is not specified as @Entity");
        Long id = entityMapper.getId(entity);
        List <Object> arrayList = entityMapper.getFields(entity);
        List <String> columnNames = entityMapper.getAllColumnNames();

        if(arrayList.size() != columnNames.size())
            throw new RuntimeException( "Number of fields (" + arrayList.size() + ") and number of all column Names (" + columnNames.size() +") is no equal");

        String Query = "MERGE INTO \""+ getTableName(entityMapper) +"\" I "
                + "USING (SELECT " + id + " as id FROM DUAL) S "
                + "ON (S.id = I.id) "
                + "WHEN MATCHED THEN "
                + "UPDATE SET ";
        for(int i = 0; i < arrayList.size(); i++){
            Query += columnNames.get(i) + " = \'" + arrayList.get(i) + "\'";
            if(i + 1 < arrayList.size())
                Query += ", ";
        }

        Query += " WHEN NOT MATCHED THEN "
                + "INSERT (";

        for(int i = 0; i < arrayList.size(); i++){
            Query += columnNames.get(i);
            if(i+1 < arrayList.size())
                Query += ", ";
        }

        Query += ") VALUES (";

        for(int i = 0; i < arrayList.size(); i++){
            Query = Query + "\'" + arrayList.get(i) + "\'";
            if(i+1 < arrayList.size())
                Query += ", ";
        }
        Query += ")";

        jdbcTemplate.update(Query);
        return entity;
    }


    @Override
    public Optional<E> findById(ID id, EntityMapper<E> entityMapper) {
        return jdbcTemplate.query("SELECT * FROM \"" + getTableName(entityMapper) + "\" e WHERE e.id = ?", entityMapper, id)
                .stream()
                .findAny();
    }

    @Override
    public ResponseEntity delete(ID id, EntityMapper<E> entityMapper) {
        if(findById(id, entityMapper).isEmpty())
            ResponseEntity.status(404).body("No such id");
        String Query = "DELETE FROM \"" + getTableName(entityMapper) + "\" e WHERE e.id = ?";
        jdbcTemplate.update(Query, id);
        return ResponseEntity.status(200).body("Entity was deleted");
    }
}