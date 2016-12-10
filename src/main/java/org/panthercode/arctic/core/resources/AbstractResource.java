package org.panthercode.arctic.core.resources;

import org.panthercode.arctic.core.helper.identity.Identifiable;
import org.panthercode.arctic.core.helper.identity.Identity;
import org.panthercode.arctic.core.helper.identity.IdentityInfo;
import org.panthercode.arctic.core.helper.version.Version;
import org.panthercode.arctic.core.helper.version.VersionInfo;
import org.panthercode.arctic.core.helper.version.Versionable;
import org.panthercode.arctic.core.settings.Configurable;
import org.panthercode.arctic.core.settings.Configuration;

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
@IdentityInfo(name = "Abstract Resource", group = "Resources")
@VersionInfo(major = 1, minor = 0, build = 0, revision = 0)
public abstract class AbstractResource implements Identifiable, Versionable, Configurable {

    private final Version version;

    private final Identity identity;

    protected Configuration configuration = null;

    public AbstractResource() {
        this(null);
    }

    public AbstractResource(Configuration configuration) {
        this.version = Version.fromAnnotation(this.getClass());
        this.identity = Identity.fromAnnotation(this.getClass());

        this.configure(configuration);
    }

    @Override
    public Version version() {
        return this.version.copy();
    }

    @Override
    public Identity identity() {
        return this.identity.copy();
    }

    @Override
    public synchronized void configure(Configuration configuration) {
        this.configuration = (configuration == null) ? new Configuration() : configuration;
    }

    @Override
    public Configuration configuration() {
        return this.configuration;
    }
}
