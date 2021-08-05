package com.jcf.orm.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface ForeignKey {
    String name() default "";

    ConstraintMode value() default ConstraintMode.CONSTRAINT;

    String foreignKeyDefinition() default "";
}