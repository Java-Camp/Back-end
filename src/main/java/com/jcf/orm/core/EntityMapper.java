package com.jcf.orm.core;

import com.jcf.orm.annotation.Column;
import com.jcf.orm.annotation.Id;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class EntityMapper<E> implements RowMapper<E> {

    @Getter
    private Class<E> entityClass;
    private List<Field> entityFields;
    private List<Method> entityMethods;
    private List<Annotation> annotations;

    public EntityMapper(Class<E> entityClass) {
        this.entityClass = entityClass;
        this.entityFields = Arrays.asList(entityClass.getDeclaredFields());
        this.entityMethods = Arrays.asList(entityClass.getDeclaredMethods());
        this.annotations = Arrays.asList(entityClass.getDeclaredAnnotations());
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
            if (Id.class.getTypeName().equals(entityFields.get(0).getAnnotation(Id.class).annotationType().getTypeName())) {
                return Optional.of(field);
            }
        }
        return Optional.empty();
    }

    private List<Field> getColumnFields() {
        return entityFields.stream()
                .filter(field -> field.isAnnotationPresent(Column.class))
                .collect(Collectors.toList());
    }

    private String getColumnName(Field field) {
        String name = field.getAnnotation(Column.class).name();
        if (!name.isEmpty())
            return name;
        return field.getName();
    }
}
