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

    public List<Field> getColumnFields() {
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

    @SneakyThrows
    public Long getId(){
        E entity = entityClass.getConstructor().newInstance();
        return getIdField().get().getLong(entity);
    }

    @SneakyThrows
   public ArrayList<Object> getFields(){
        ArrayList<Object> arrayList = new ArrayList<>();
        for (Field field : entityFields) {

           if(!field.getAnnotation(Column.class).name().equals("id"))
               arrayList.add(field.get(entityClass));

        }
       return arrayList;
   }

   public ArrayList<String> getAllColumNames(){
        ArrayList<String> arrayList = new ArrayList<>();
        String name;

        for(Field field: entityFields){
            name = field.getAnnotation(Column.class).name();

            if(!name.equals("id"))
                arrayList.add(name);

        }
        return arrayList;
   }

}
