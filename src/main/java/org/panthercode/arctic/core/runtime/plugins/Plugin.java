package org.panthercode.arctic.core.runtime.plugins;

import org.panthercode.arctic.core.helper.identity.Identifiable;
import org.panthercode.arctic.core.helper.identity.Identity;
import org.panthercode.arctic.core.helper.version.Version;
import org.panthercode.arctic.core.helper.version.Versionable;

/**
 * Created by architect on 02.03.17.
 */
public interface Plugin extends Identifiable, Versionable {

    Identity identity();

    Version version();

    boolean canActivate();

    boolean isActivated();

    void activate() throws Exception;

    void deactivate() throws Exception;

    Class<? extends Plugin>[] dependencies();

    Class<? extends Plugin>[] checkDependencies(PluginManager manager);

    Object behindObject();
}
