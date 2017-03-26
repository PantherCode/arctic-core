package org.panthercode.arctic.core.runtime;

import org.panthercode.arctic.core.settings.Context;

/**
 * Created by architect on 05.03.17.
 */
public interface Kernel {
    void start();

    void start(Context context);

    void shutdown();
}
