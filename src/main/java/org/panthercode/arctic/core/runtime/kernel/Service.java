package org.panthercode.arctic.core.runtime.kernel;

import org.panthercode.arctic.core.event.Handler;
import org.panthercode.arctic.core.helper.identity.Identifiable;
import org.panthercode.arctic.core.helper.version.Versionable;
import org.panthercode.arctic.core.settings.Context;

/**
 * Created by architect on 05.03.17.
 */
public interface Service<T> extends Identifiable, Versionable {

    boolean canActivate();

    boolean isActive();

    void activate();

    void activate(Context context);

    void deactivate();

    void process(T argument);

    <E> void process(T argument, Handler<E> handler);
}
