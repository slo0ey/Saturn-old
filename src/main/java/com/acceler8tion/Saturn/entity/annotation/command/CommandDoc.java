package com.acceler8tion.Saturn.entity.annotation.command;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static  java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
public @interface CommandDoc {
    String alias();
    String preview() default "-";
    String desc() default "-";
}
