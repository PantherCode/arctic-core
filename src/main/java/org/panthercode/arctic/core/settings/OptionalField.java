package org.panthercode.arctic.core.settings;

import java.lang.annotation.*;

/**
 * Created by architect on 11.04.17.
 */
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(OptionalFields.class)
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface OptionalField {
    String key() default "default";

    Class<?> type() default Object.class;

    String description() default "";
}
