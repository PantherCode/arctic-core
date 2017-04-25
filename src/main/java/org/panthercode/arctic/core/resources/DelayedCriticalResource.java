package org.panthercode.arctic.core.resources;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.concurrent.semaphore.Semaphore;
import org.panthercode.arctic.core.concurrent.semaphore.impl.DelaySemaphore;
import org.panthercode.arctic.core.helper.identity.IdentityInfo;
import org.panthercode.arctic.core.helper.version.VersionInfo;
import org.panthercode.arctic.core.processing.priority.Priority;
import org.panthercode.arctic.core.settings.Configuration;

/**
 * Created by architect on 26.04.17.
 */
@IdentityInfo(name = "Delayed Critical Resource", group = "Resources")
@VersionInfo(major = 1, minor = 0, build = 0, revision = 0)
public class DelayedCriticalResource extends AbstractCriticalResource {

    private DelaySemaphore delaySemaphore;

    public DelayedCriticalResource(Semaphore<Priority> semaphore,
                                   DelaySemaphore delaySemaphore) {
        this(semaphore, delaySemaphore, null);
    }

    /**
     * @param semaphore
     * @param configuration
     */
    public DelayedCriticalResource(Semaphore<Priority> semaphore,
                                   DelaySemaphore delaySemaphore,
                                   Configuration configuration) {
        super(semaphore, configuration);

        this.delaySemaphore = ArgumentUtils.checkNotNull(delaySemaphore, "delay semaphore");
    }

    @Override
    public void acquire(Priority priority) throws Exception {
        super.acquire(priority);

        this.delaySemaphore.acquire();
    }

    @Override
    public void release() {
        this.delaySemaphore.release();

        super.release();
    }

    @Override
    public Resource copy() {
        return new DelayedCriticalResource(this.semaphore, this.delaySemaphore, this.configuration);
    }
}
