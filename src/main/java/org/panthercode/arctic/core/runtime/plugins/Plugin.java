package org.panthercode.arctic.core.runtime.plugins;

import org.panthercode.arctic.core.helper.identity.Identifiable;
import org.panthercode.arctic.core.helper.version.Versionable;

/**
 * Created by architect on 02.03.17.
 */
public interface Plugin extends Identifiable, Versionable {

    boolean canActivate();

    boolean isActivated();

    void activate() throws RuntimeException;

    void deactivate() throws RuntimeException;

    <T> T process(Class<T> returnType, Object... args) throws RuntimeException;

    <T> T getBehindObject(Class<T> clazz);
}
