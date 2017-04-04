package org.panthercode.arctic.core.io;

import java.io.IOException;

/**
 * Created by architect on 04.04.17.
 */
public interface Importer<T> {
    T read() throws IOException;
}
