package com.jcf.orm.core;

import com.jcf.orm.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;


@Accessors(chain = true)
public class EntityMapper<E> implements RowMapper<E> {

    @Getter
    private final Class<E> entityClass;
    private List<Field> entityFields;
    private static boolean isRef = true;

    @Setter
    private JdbcTemplate jdbcTemplate;

    public EntityMapper(Class<E> entityClass) {
        this.entityClass = entityClass;
        this.entityFields = Arrays.asList(entityClass.getDeclaredFields());
    }

    @SneakyThrows
    @Override
    public E mapRow(ResultSet resultSet, int i) {
        E entity = entityClass.getConstructor().newInstance();
        Field field1 = getIdField(entityClass).get();
        field1.setAccessible(true);
        field1.set(entity, resultSet.getLong("id")); // id
        field1.setAccessible(false);

        getColumnFields()
                .forEach(field -> {
                    try {
                        field.setAccessible(true);
                        field.set(entity, resultSet.getObject(getColumnName(field)));
                        field.setAccessible(false);
                    } catch (IllegalAccessException | SQLException exception) {
                        exception.printStackTrace();
                    }
                });

        getMapFields().forEach(field -> {
            isRef = false;
            try {
                field.setAccessible(true);
                field.set(entity, setRefList(field, resultSet.getLong("id")));
                field.setAccessible(true);
                isRef = true;
            } catch (IllegalAccessException | SQLException e) {
                e.printStackTrace();
            }
        });

        if (isRef) {
            getRefFields().forEach(field -> {
                try {
                    field.setAccessible(true);
                    field.set(entity, setReference(field, resultSet.getLong(getRefName(field))).get());
                    field.setAccessible(true);
                } catch (IllegalAccessException | SQLException e) {
                    e.printStackTrace();
                }
            });
        }

        return entity;
    }


    private Optional<Field> getIdField(Class entityClass) {
        for (Field field : entityFields) {

            if (Id.class.getTypeName().equals(entityFields.get(0).getAnnotation(Id.class).annotationType().getTypeName()))
                return Optional.of(field);

        }
        return Optional.empty();
    }

    public List<Field> getColumnFields() { // Output the List of Fields with "Column" annotation
        return entityFields.stream()
                .filter(field -> field.isAnnotationPresent(Column.class))
                .collect(Collectors.toList());
    }

    private List<Field> getRefFields() {
        return entityFields.stream()
                .filter(field -> field.isAnnotationPresent(Reference.class))
                .collect(Collectors.toList());
    }

    private List<Field> getMapFields() {
        return entityFields.stream()
                .filter(field -> field.isAnnotationPresent(MappedBy.class))
                .collect(Collectors.toList());
    }


    @SneakyThrows
    private Optional<E> setReference(Field field, Long id) {
        final String[] fetchColumns = field.getAnnotation(Reference.class).fetchColumns();
        final String key = field.getAnnotation(Reference.class).name();
        int fetchColumnsLength = Arrays.toString(fetchColumns).length() - 1;
        return (Optional<E>) jdbcTemplate.query("SELECT u." + Arrays.toString(fetchColumns).substring(1, fetchColumnsLength) + " FROM \"" + getTableName(entityClass) +
                "\" e RIGHT JOIN \"" + getTableName((Class<E>) field.getType()) +
                "\" u ON " + key + " = " + " u.id WHERE " + key + " = " + id, new EntityMapper<>(field.getType()).setJdbcTemplate(jdbcTemplate))
                .stream()
                .findAny();
    }

    private List<E> setRefList(Field field, Long id) {
        final String foreignKeyName = getForeignKeyName(field);
        final Class<E> mappedClass = field.getAnnotation(MappedBy.class).entityClass();
        return jdbcTemplate.query("SELECT u.* FROM \"" + getTableName(entityClass) +
                "\" e RIGHT JOIN \"" + getTableName(mappedClass) +
                "\" u ON e.id = " + foreignKeyName +
                " WHERE " + foreignKeyName + " = " + id, new EntityMapper<>(mappedClass).setJdbcTemplate(jdbcTemplate));
    }

    @SneakyThrows
    private String getForeignKeyName(Field field) {
        final String mappedBy = field.getAnnotation(MappedBy.class).mappedBy();
        return field.getAnnotation(MappedBy.class)
                .entityClass()
                .getDeclaredField(mappedBy)
                .getAnnotation(Reference.class)
                .name();
    }

    //    SELECT * FROM "user" e RIGHT JOIN  ROLE u on e.ID = u.USER_ID
//    WHERE USER_ID = 2
    private String getTableName(Class<E> entityClass) {
        return entityClass.getAnnotation(Table.class).name();
    }

    private String getColumnName(Field field) { // Output the name
        String name = field.getAnnotation(Column.class).name();

        if (!name.isEmpty())
            return name;

        return field.getName();
    }

    private String getRefName(Field field) {
        return field.getAnnotation(Reference.class).name();
    }


    @SneakyThrows
    public Long getId(E entity) { // Output id
        Field privateField = entityClass.getDeclaredField("id");
        privateField.setAccessible(true);
        Long fieldValue = (Long) privateField.get(entity);
        privateField.setAccessible(false);
        return fieldValue;
    }

    @SneakyThrows
    public List<Object> getFields(E entity) { // Output array with fields
        List<Object> answer = new ArrayList<>();
        List<Field> fields = getColumnFields();
        for (Field field : fields) {
            field.setAccessible(true);
            answer.add(field.get(entity));
            field.setAccessible(false);
        }
        return answer;
    }

    public List<String> getAllColumnNames() { // Output array with names
        List<String> List = new ArrayList<>();
        List<Field> fields = getColumnFields();

        for (Field field : fields)
            List.add(getColumnName(field));

        return List;
    }

}
