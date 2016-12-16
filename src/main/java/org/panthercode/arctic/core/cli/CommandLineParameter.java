package org.panthercode.arctic.core.cli;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CommandLineParameter {
    String name() default "";

    char shortName() default ' ';

    String description() default "";

    boolean hasValue() default false;

    String defaultValue() default "";

    Class<?> type() default String.class;
}
