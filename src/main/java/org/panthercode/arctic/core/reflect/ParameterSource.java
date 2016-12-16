package org.panthercode.arctic.core.reflect;

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
@Target(ElementType.PARAMETER)
public @interface ParameterSource {
    String name() default "";

    String defaultValue() default "";

    Class<?> type() default Object.class;
}
