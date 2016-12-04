/*
 * Copyright 2016 PantherCode
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.panthercode.arctic.core.processing.modules.impl;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.panthercode.arctic.core.helper.identity.IdentityInfo;
import org.panthercode.arctic.core.helper.version.VersionInfo;
import org.panthercode.arctic.core.processing.exceptions.ProcessException;
import org.panthercode.arctic.core.processing.modules.Module;
import org.panthercode.arctic.core.settings.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//TODO: update documentation

/**
 * The bundle class is used to hold and proceed a list of modules. Every module is stored in an internal list.
 */
@IdentityInfo(name = "Standard Bundle", group = "Bundle Module")
@VersionInfo(major = 1)
public abstract class Bundle extends ModuleImpl {

    /**
     * list of modules in bundle
     */
    private List<Module> modules = null;

    /**
     * Standard Constructor
     *
     * @throws NullPointerException Is thrown if identity is null.
     */
    public Bundle()
            throws NullPointerException {
        this(new Context());
    }

    /**
     * Constructor
     *
     * @param context context the module is associated with.
     * @throws NullPointerException Is thrown if identity is null.
     */
    public Bundle(final Context context)
            throws NullPointerException {
        super(context);

        modules = new ArrayList<>();
    }

    public synchronized void before()
            throws ProcessException {
    }

    public synchronized void after()
            throws ProcessException {

    }

    /**
     * Copy Constructor
     *
     * @param bundle object to copy
     * @throws UnsupportedOperationException Is thrown if a module in bundle doesn't support cloning.
     * @throws NullPointerException          Is thrown if the bundle contains a null element or parameter is null.
     */
    public Bundle(final Bundle bundle)
            throws UnsupportedOperationException, NullPointerException {
        super(bundle);

        this.modules = new ArrayList<>(bundle.size());

        for (Module module : bundle.modules()) {
            if (module != null) {
                this.modules.add(module.copy());
            }
        }
    }

    /**
     * Set new context the object is associated with. It also sets the context of child element. You can only set a new
     * context to this object if process state isn't "Running" or "Waiting".
     *
     * @param context new context
     */
    @Override
    public synchronized boolean setContext(final Context context) {
        if (super.setContext(context)) {
            boolean flag = true;

            for (Module module : this.modules) {
                if (!module.setContext(context)) {
                    flag = false;
                }
            }

            return flag;
        }

        return false;
    }

    /**
     * Adds a new module to bundle.
     *
     * @param module new module to add
     */
    public synchronized boolean deploy(final Module module) {
        if (module != null) {
            module.setContext(this.getContext());
            return this.modules.add(module);
        }

        return false;
    }

    /**
     * @param modules
     * @return
     */
    public synchronized boolean deploy(final List<Module> modules) {
        if (modules != null) {
            boolean flag = true;

            for (Module module : modules) {
                if (module != null) {
                    if (!this.deploy(module)) {
                        flag = false;
                    }
                }
            }

            return flag;
        }

        return false;
    }

    /**
     * Adds a nwe module to bundle at a given position.
     *
     * @param index  new index added module
     * @param module new module to add
     */
    public synchronized void deploy(final int index, final Module module) {
        if (module != null) {
            module.setContext((this.getContext()));
            this.modules.add(index, module);
        }
    }

    /**
     * Delete a module from bundle.
     *
     * @param module module to delete
     */
    public synchronized boolean undeploy(final Module module) {
        return module != null && this.modules.remove(module);

    }

    /**
     * Delete a module from bunde by its identity id.
     *
     * @param moduleId identity id of module to delete
     */
    public synchronized boolean undeploy(final long moduleId) {
        return this.undeploy(this.module(moduleId));
    }

    /**
     * Returns the elements of bundle as list. The returned list is a direct instance to original list. Be careful if
     * you manipulate this list.
     *
     * @return Returns the elements of bundle as list.
     */
    public List<Module> modules() {
        return this.modules;
    }

    /**
     * Returns the actual number of elements in bundle.
     *
     * @return Returns the actual number of elements in bundle.
     */
    public int size() {
        return this.modules.size();
    }

    /**
     * Returns a module by its identity id.
     *
     * @param moduleId identity id of module
     * @return Returns the corresponding module to the given id if bundle contains the element; Otherwise <tt>null</tt>.
     */
    public Module module(final long moduleId) {
        for (Module module : this.modules) {
            if (module.identity().id() == moduleId) {
                return module;
            }
        }

        return null;
    }

    /**
     * Remove all modules from bundle.
     */
    public synchronized void clear() {
        this.modules.clear();
    }

    /**
     * Checks whether the bundle contains the given module or not.
     *
     * @param module module to check
     * @return Returns <tt>true</tt> if bundle contains the module; Otherwies <tt>false</tt>.
     */
    public boolean contains(final Module module) {
        return this.modules.contains(module);
    }

    /**
     * Checks by identity id if the bundle contains the searched module or not.
     *
     * @param moduleId identity id of module
     * @return Returns <tt>true</tt> if bundle contains the module; Otherwise <tt>false</tt>.
     */
    public boolean contains(final long moduleId) {
        return this.module(moduleId) != null;
    }

    /**
     * Returns an list iterator of the bundle.
     *
     * @return Returns an list iterator of the bundle.
     */
    public Iterator<Module> iterator() {
        return this.modules().iterator();
    }

    /**
     * Returns a hash code value of this object.
     *
     * @return Returns a hash code value of this object.
     */
    @Override
    public int hashCode() {
        return Math.abs(new HashCodeBuilder()
                .append(super.hashCode())
                .append(this.modules.hashCode())
                .toHashCode());
    }

    /**
     * Checks if this object is equals to another one.
     *
     * @param obj other object for comparison
     * @return Returns <code>true</code> if both objects are equal; Otherwise <tt>false</tt>.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Bundle)) {
            return false;
        }

        Bundle bundle = (Bundle) obj;

        return super.equals(bundle) && this.modules.equals(bundle.modules());
    }
}
