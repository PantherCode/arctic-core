package org.panthercode.arctic.core.concurrent.helper;

import org.panthercode.arctic.core.helper.identity.Identifiable;
import org.panthercode.arctic.core.helper.identity.Identity;
import org.panthercode.arctic.core.helper.identity.IdentityInfo;
import org.quartz.Job;

/**
 * Created by architect on 01.03.17.
 */
@IdentityInfo(name = "IdentifiableJob")
public abstract class IdentifiableJob implements Job, Identifiable {

    private final Identity identity;

    public IdentifiableJob() {
        this.identity = Identity.fromAnnotation(this.getClass());
    }

    @Override
    public Identity identity() {
        return this.identity;
    }
}
