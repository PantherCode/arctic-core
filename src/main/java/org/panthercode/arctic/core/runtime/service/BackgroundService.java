package org.panthercode.arctic.core.runtime.service;

import org.panthercode.arctic.core.settings.Context;

/**
 * Created by architect on 02.04.17.
 */
public interface BackgroundService {
    /**
     * @return
     */
    boolean canActivate();

    /**
     * @return
     */
    boolean canDeactivate();

    /**
     * @return
     */
    boolean isActive();

    /**
     *
     */
    void activate();

    /**
     * @param context
     */
    void activate(Context context);

    /**
     *
     */
    void deactivate();
}
