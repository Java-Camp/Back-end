package com.jcf.orm.core;

import com.jcf.orm.annotation.Entity;
import com.jcf.orm.annotation.Table;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.sql.*;

import java.time.Instant;
import java.util.List;
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

    @Override
    public E saveOrUpdate(E entity, EntityMapper<E> entityMapper) {
        // checking
        Assert.notNull(entity, "Entity must be a set");
        if (!entityMapper.getEntityClass().isAnnotationPresent(Entity.class))
            throw new IllegalArgumentException(entityMapper.getEntityClass().getSimpleName() + " is not specified as @Entity");

        Long id = entityMapper.getId(entity);
        List<Object> fields = entityMapper.getFields(entity);
        List <String> columnNames = entityMapper.getAllColumnNames();
        List<String> uniques = entityMapper.getAllUniques(entity);
        List<Object> uniquesFields = entityMapper.getAllUniquesFields(entity);

        if(uniques.isEmpty()){
            uniques.add("id");
            uniquesFields.add(null);
        }
        log.info("uniques SIZE = " + uniques.size() + "\nuniques name SIZE = " + uniquesFields.size());


        if(fields.size() != columnNames.size() || uniques.size() != uniquesFields.size()) // fields and columnNames have to have the same size
            throw new RuntimeException( "Number of fields (" + fields.size()
                    + ") and number of all column Names (" + columnNames.size()
                    + ") is not equal or all column unique Names (" + uniques.size()
                    + ") and number of all fields (" + uniquesFields.size() + ") is not equal");

        StringBuilder Query = new StringBuilder("MERGE INTO \"" + getTableName(entityMapper) + "\" I USING (SELECT " + id + " as id FROM DUAL) S "
                + "ON (S.id = I.id) "
                + "WHEN MATCHED THEN "
                + "UPDATE SET ");

        for (String columnName : columnNames) Query.append(columnName).append(" = ?, ");

        Query.setLength(Query.length()-2); // cut the ", "
        Query.append(" WHEN NOT MATCHED THEN INSERT (");

        // write every element's name and it's value
        for(String name: columnNames) Query.append(name).append(", ");

        Query.setLength(Query.length()-2);
        Query.append(") VALUES (");

        for (Object field : fields) Query.append("?, ");

        Query.setLength(Query.length()-2);
        Query.append(")");

        log.info("Finished creating a Query:\n" + Query);

/*        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(final Connection conn) throws SQLException {
                final PreparedStatement preparedStatement =
                        conn.prepareStatement(Query.toString());
                Object o;
                for(int i = 0; i < fields.size()*2; i++) {
                    o = fields.get(i % fields.size());
                    if(o instanceof Instant)
                        o = Timestamp.from((Instant) o);
                    preparedStatement.setObject(i + 1, o);
                    log.info((i+1) + ") Added new Object: " + o);
                }
                return preparedStatement;
            }
        });*/
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String id_column = "ID";
        jdbcTemplate.update(con -> {
                    final PreparedStatement preparedStatement =
                            con.prepareStatement(Query.toString(), new String[]{id_column});
                    Object o;
                    for(int i = 0; i < fields.size()*2; i++) {
                        o = fields.get(i % fields.size());
                        if(o instanceof Instant)
                            o = Timestamp.from((Instant) o);
                        preparedStatement.setObject(i + 1, o);
                        log.info((i+1) + ") Added new Object: " + o);
                    }
                    return preparedStatement;
                }, keyHolder);
        log.info("User was added to table");
        return entity;
    }

/*    @SneakyThrows
    private E getEntityWithID(Connection connection, EntityMapper<E> entityMapper){
        String sql = "select ISEQ$$_104124.currval from DUAL";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        Long id = null;
        if(rs.next())
            id = rs.getLong(1);
        return findById((ID) id, entityMapper).get();
    }*/

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
    public E findByUnique(String name, Object value , EntityMapper<E> entityMapper) {
        String Query = "SELECT * FROM \"" + getTableName(entityMapper) + "\" e WHERE e." + name + " = ?";
        log.info(Query);
        Optional<E> answer = jdbcTemplate.query(Query, entityMapper, value)
                .stream()
                .findAny();
        if (answer.isEmpty())
            throw new IllegalArgumentException("entity not found not found");
        return answer.get();
    }
}