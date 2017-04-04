package org.panthercode.arctic.core.helper;

/**
 * Created by architect on 04.04.17.
 */
public interface Converter<A, B> {
    B convert(A value);

    A convertBack(B value);
}
