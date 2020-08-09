package com.acceler8tion.Saturn.entity.annotation.command;

import com.acceler8tion.Saturn.util.PermissionLevel;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static  java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
public @interface CommandProperties {
    PermissionLevel level() default PermissionLevel.VERIFIED;
    String[] access() default {};
}
