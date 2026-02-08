package com.darkredgm.querymc.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DbColumn {
    String value() default "";

    int length() default 255;          // VARCHAR(255)
    int precision() default 10;        // DECIMAL(10,2)
    int scale() default 2;             // DECIMAL(10,2)
    String[] enumValues() default {};  // ENUM('active','inactive')
    boolean nullable() default true;
}
