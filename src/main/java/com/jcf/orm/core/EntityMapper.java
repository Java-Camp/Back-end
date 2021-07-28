package com.jcf.orm.core;

import com.jcf.orm.annotation.*;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class EntityMapper<E> implements RowMapper<E> {

    @Getter
    private final Class<E> entityClass;
    private final List<Field> entityFields;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public EntityMapper(Class<E> entityClass) {
        this.entityClass = entityClass;
        this.entityFields = Arrays.asList(entityClass.getDeclaredFields());
    }

    @SneakyThrows
    @Override
    public E mapRow(ResultSet resultSet, int i) {
        E entity = entityClass.getConstructor().newInstance();
        Field field1 = getIdField().get();
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

        return entity;
    }

    private Optional<Field> getIdField() {
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

//    private List<Field> getMappedFields(){
//        return entityFields.stream()
//                .filter(field -> field.isAnnotationPresent(MappedBy.class))
//                .collect(Collectors.toList());
//    }
//
//

    private void setReference() {
        final List<Field> fields = entityFields.stream()
                .filter(field -> field.isAnnotationPresent(Reference.class))
                .collect(Collectors.toList());
        if (fields.isEmpty())
            return;

//        for (Field field : fields) {
//            jdbcTemplate.query("SELECT * FROM " + getTableName((Class<E>) field.getType()) +
//                    "WHERE " + field.getAnnotation(Reference.class).name() + " = ",{} );
//
//        }
    }

    private String getTableName(Class<E> entityClass) {
        return entityClass.getAnnotation(Table.class).name();
    }

    private String getColumnName(Field field) { // Output the name
        String name = field.getAnnotation(Column.class).name();

        if (!name.isEmpty())
            return name;

        return field.getName();
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
