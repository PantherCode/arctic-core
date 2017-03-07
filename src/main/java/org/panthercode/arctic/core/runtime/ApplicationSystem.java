package org.panthercode.arctic.core.runtime;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.runtime.kernel.Kernel;

/**
 * Created by architect on 05.03.17.
 */
public class ApplicationSystem<T extends Kernel> {

    private final T kernel;

    public ApplicationSystem(T kernel) {
        this.kernel = ArgumentUtils.checkNotNull(kernel, "kernel");
    }

    public T kernel() {
        return this.kernel;
    }
}