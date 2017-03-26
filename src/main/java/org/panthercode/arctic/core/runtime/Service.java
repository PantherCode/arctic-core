package org.panthercode.arctic.core.runtime;

import org.panthercode.arctic.core.helper.identity.Identifiable;
import org.panthercode.arctic.core.helper.version.Versionable;
import org.panthercode.arctic.core.runtime.ServiceMessage;
import org.panthercode.arctic.core.settings.Context;

/**
 * Created by architect on 05.03.17.
 */
public interface Service<T extends ServiceMessage> extends Identifiable, Versionable {
    /**
     *
     * @return
     */
    boolean canActivate();

    /**
     *
     * @return
     */
    boolean canDeactivate();

    /**
     *
     * @return
     */
    boolean isActive();

    /**
     *
     */
    void activate();

    /**
     *
     * @param context
     */
    void activate(Context context);

    /**
     *
     */
    void deactivate();

    /**
     *
     * @param message
     */
    void process(T message);
}
