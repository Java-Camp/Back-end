package com.jcf.orm.core;

import com.jcf.orm.annotation.Column;
import com.jcf.orm.annotation.Id;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
@Slf4j
public class EntityMapper<E> implements RowMapper<E> {

    @Getter
    private final Class<E> entityClass;
    private final List<Field> entityFields;

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
                        if(resultSet.getObject(getColumnName(field)) instanceof oracle.sql.TIMESTAMP){
                            String dateTime = resultSet.getObject(getColumnName(field)).toString(); //'1980-05-20 02:00:00.0'
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                            Date parsedDate = dateFormat.parse(dateTime);
                            Instant instant = new Timestamp(parsedDate.getTime()).toInstant();
                            field.set(entity, instant);
                        }
                        else{
                            field.set(entity, resultSet.getObject(getColumnName(field)));
                        }
                        field.setAccessible(false);
                    } catch (IllegalAccessException | SQLException |ParseException exception) {
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

    public List<Field> getColumnFields() { // Output the List of Fields with annotations with "Column" annotation
        return entityFields.stream()
                .filter(field -> field.isAnnotationPresent(Column.class))
                .collect(Collectors.toList());
    }

    private String getColumnName(Field field) { // Output the name
        String name = field.getAnnotation(Column.class).name();

        if (!name.isEmpty())
            return name;

        return field.getName();
    }

    @SneakyThrows
    public Long getId(E entity){ // Output id
        Field privateField = entityClass.getDeclaredField("id");
        privateField.setAccessible(true);
        Long fieldValue = (Long) privateField.get(entity);
        privateField.setAccessible(false);
        return fieldValue;
    }

    @SneakyThrows
   public List<Object> getFields(E entity){ // Output array with fields
        List<Object> answer = new ArrayList<>();
        List<Field> fields = getColumnFields();
        for (Field field : fields) {
            field.setAccessible(true);
            answer.add(field.get(entity));
            field.setAccessible(false);
        }
       return answer;
   }

   public List<String> getAllColumnNames(){ // Output array with names
        List<String> List = new ArrayList<>();
        List<Field> fields = getColumnFields();

        for(Field field: fields)
            List.add(getColumnName(field));

        return List;
   }

   public List<String> getAllUniques(E entity){ // return all uniques column names
        List<String> columnNames = new ArrayList<>();;
        for (Field field : entity.getClass().getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column != null && column.unique())
                columnNames.add(column.name());
        }
        return columnNames;
    }

    @SneakyThrows
    public List<Object> getAllUniquesFields(E entity){ // return all uniques objects
        List<Object> objects = new ArrayList<>();
        List<String> uniques = getAllUniques(entity);
        Field privateField;
        for (String s : uniques) {
            privateField = entityClass.getDeclaredField(s);
            privateField.setAccessible(true);
            objects.add(privateField.get(entity));
            privateField.setAccessible(false);
        }
        return objects;
    }
}
