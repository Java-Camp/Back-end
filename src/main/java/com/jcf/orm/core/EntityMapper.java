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
import java.util.*;
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

    public List<Field> getColumnFields() { // Выводит список Field с аннотацией Column
        return entityFields.stream()
                .filter(field -> field.isAnnotationPresent(Column.class))
                .collect(Collectors.toList());
    }

    private String getColumnName(Field field) { // выводит название
        String name = field.getAnnotation(Column.class).name();
        if (!name.isEmpty())
            return name;
        return field.getName();
    }

    @SneakyThrows
    public Long getId(E entity){ // выводит id
        Field privateField = entityClass.getDeclaredField("id");
        privateField.setAccessible(true);
        Long fieldValue = (Long) privateField.get(entity);
        privateField.setAccessible(false);
        return fieldValue;
    }

    @SneakyThrows
   public List<Object> getFields(E entity){ // выводит массив со значениями
        List<Object> answer = new ArrayList<>();
        List<Field> fields = getColumnFields();
        for (Field field : fields) {
            field.setAccessible(true);
            answer.add(field.get(entity));
            field.setAccessible(false);
        }
       return answer;
   }

   public List<String> getAllColumnNames(){ // выводит массив названий
        List<String> List = new ArrayList<>();
        List<Field> fields = getColumnFields();
        for(Field field: fields)
            List.add(getColumnName(field));

        return List;
   }

}
