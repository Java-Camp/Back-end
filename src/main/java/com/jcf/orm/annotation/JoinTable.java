package com.jcf.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinTable {
    String name() default "";

    String catalog() default "";

    String schema() default "";

    JoinColumn[] joinColumns() default {};

    JoinColumn[] inverseJoinColumns() default {};

    ForeignKey foreignKey() default @ForeignKey(ConstraintMode.PROVIDER_DEFAULT);

    ForeignKey inverseForeignKey() default @ForeignKey(ConstraintMode.PROVIDER_DEFAULT);

    UniqueConstraint[] uniqueConstraints() default {};

    Index[] indexes() default {};
}
