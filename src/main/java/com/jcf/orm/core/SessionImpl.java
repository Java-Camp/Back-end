package com.jcf.orm.core;

import com.jcf.orm.annotation.Entity;
import com.jcf.orm.annotation.Table;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.sql.*;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class SessionImpl<E, ID> implements Session<E,ID> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SessionImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String getTableName(EntityMapper<E> entityMapper){
        return entityMapper.getEntityClass().getAnnotation(Table.class).name();
    }
    Connection connection;

    @Override
    public E saveOrUpdate(E entity, EntityMapper<E> entityMapper) {
        // checking
        Assert.notNull(entity, "Entity must be a set");
        if (!entityMapper.getEntityClass().isAnnotationPresent(Entity.class))
            throw new IllegalArgumentException(entityMapper.getEntityClass().getSimpleName() + " is not specified as @Entity");

        Long id = entityMapper.getId(entity);
        List<Object> fields = entityMapper.getFields(entity);
        List <String> columnNames = entityMapper.getAllColumnNames();

        StringBuilder Query = new StringBuilder("MERGE INTO \"" + getTableName(entityMapper) + "\" I USING (SELECT " + id + " as id FROM DUAL) S "
                + "ON (S.id = I.id) "
                + "WHEN MATCHED THEN "
                + "UPDATE SET ");

        for (String columnName : columnNames) Query.append(columnName).append(" = ?, ");

        Query.setLength(Query.length()-2); // cut the ", "
        Query.append(" WHEN NOT MATCHED THEN INSERT (");

        columnNames.add("ID"); // ADD THE ID

        // write every element's name and it's value
        for(String name: columnNames) Query.append(name).append(", ");

        Query.setLength(Query.length()-2);
        Query.append(") VALUES (");

        for (Object ignored : columnNames) Query.append("?, ");

        Query.setLength(Query.length()-2);
        Query.append(")");

        columnNames.remove(columnNames.size() - 1); // return the size back

        if(fields.size() != columnNames.size()) // fields and columnNames have to have the same size
            throw new RuntimeException( "Number of fields (" + fields.size()
                    + ") and number of all column Names (" + columnNames.size());

        log.info("Finished creating a Query:\n" + Query);

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(final Connection conn) throws SQLException {
                final PreparedStatement preparedStatement =
                        conn.prepareStatement(Query.toString());
                Object o;
                connection = conn;
                Long entityID = id;
                if (Objects.isNull(id)) {
                    entityID = getIdBySequence();
                    entityMapper.setId(entity, entityID);
                }

                for(int i = 0; i < fields.size()*2; i++) {
                    o = fields.get(i % fields.size());
                    if(o instanceof Instant)
                        o = Timestamp.from((Instant) o);
                    preparedStatement.setObject(i + 1, o);
                    log.info((i+1) + ") Added new Object: " + o);
                }
                log.info((fields.size()*2+1) + ") Added new Object: " + entityID);
                preparedStatement.setLong(fields.size()*2+1, entityID);
                return preparedStatement;
            }
        });

        log.info("Entity was added to table");
        return entity;
    }

    private Long getIdBySequence(){
        Long id = null;
        try{
            String sql = "select MY_SEQ.nextval from DUAL";
            log.info(sql);
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                id = rs.getLong(1);
        }
        catch (Exception exception){
            exception.getStackTrace();
        }
        return id;
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
    public List<E> findAll(EntityMapper<E> entityMapper) {
        return jdbcTemplate.query("SELECT * FROM " + getTableName(entityMapper),entityMapper);
    }

    @Override
    public List<E> findByUnique(String name, Object value , EntityMapper<E> entityMapper) {
        // String Query = "SELECT ACCOUNT_ID FROM " + getTableName(entityMapper) + " e WHERE e." + name + " = " + value;
//        String Query = "SELECT * FROM \"" + getTableName(entityMapper) + "\" e WHERE e." + name + " = ?";
        String Query = "SELECT * FROM USER_ACCOUNT e WHERE e.USER_ID = ?";
        List <E> answer = jdbcTemplate.query(Query, entityMapper, value);
        System.out.println(answer);
        return answer;
    }
}