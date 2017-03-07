package org.panthercode.arctic.core.runtime.kernel;

import org.panthercode.arctic.core.helper.identity.Identifiable;
import org.panthercode.arctic.core.helper.version.Versionable;

/**
 * Created by architect on 05.03.17.
 */
public interface Service extends Identifiable, Versionable {

    void start();

    void shutdown();

    boolean isStarted();

    Object process(Object... args);
}
