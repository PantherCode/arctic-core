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
package org.panthercode.arctic.core.repository.impl;

import org.panthercode.arctic.core.helper.identity.Identifiable;
import org.panthercode.arctic.core.helper.identity.Identity;
import org.panthercode.arctic.core.helper.identity.annotation.IdentityInfo;
import org.panthercode.arctic.core.helper.version.Version;
import org.panthercode.arctic.core.helper.version.Versionable;
import org.panthercode.arctic.core.helper.version.annotation.VersionInfo;
import org.panthercode.arctic.core.repository.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//TODO: Warning if repository store already an element with same identiy name/ group and version
//TODO: Update Documentation
//TODO: update synchronisation

/**
 * Concrete implementation of Interface 'Repository' to store objects.
 */
@IdentityInfo(name = "Standard Repository", group = "Repository")
@VersionInfo(major = 1)
public class RepositoryImpl<T extends Identifiable & Versionable> implements Repository<T> {
    /**
     * The identity the object is associated with.
     */
    private final Identity identity;

    /**
     *
     */
    private final Version version;

    /**
     * map to store objects
     */
    private Map<String, Map<Version, T>> map = new HashMap<>();

    /**
     *
     */
    public RepositoryImpl() {
        if (Identity.isAnnotated(this)) {
            this.identity = Identity.fromAnnotation(this);
        } else {
            throw new NullPointerException("The value of identity is null.");
        }

        if (Version.isAnnotated(this)) {
            this.version = Version.fromAnnotation(this);
        } else {
            this.version = new Version();
        }
    }

    /**
     * @return
     */
    @Override
    public Identity identity() {
        return this.identity.copy();
    }

    /**
     * @return
     */
    @Override
    public Version version() {
        return this.version;
    }

    /**
     * Adds a new element to repository.
     *
     * @param element element to add
     */
    @Override
    public synchronized void store(T element) {
        if (element != null) {
            String key = element.identity().asShortString();

            Map<Version, T> versionMap = (this.contains(element.identity())) ? this.map.get(key) : new HashMap<>();

            versionMap.put(element.version(), element);

            this.map.put(key, versionMap);
        }
    }

    @Override
    public void delete(T element) {
        if (element != null) {
            this.delete(element.identity(), element.version());
        }
    }

    /**
     * Removes an element from repository.
     *
     * @param
     */
    @Override
    public synchronized void delete(Identity identity, Version version) {
        if (this.contains(identity, version)) {
            Map<Version, T> versionMap = this.map.get(identity.asShortString());

            versionMap.remove(version);

            this.map.put(identity.asShortString(), versionMap);
        } else if (this.contains(identity) && version == null) {
            this.map.remove(identity.asShortString());
        }
    }

    /**
     * Checks whether the repository contains an element with given name or not.
     *
     * @param identity
     * @return Returns <tt>true</tt> if repository contains the element; Otherwise <tt>false</tt>.
     */
    @Override
    public boolean contains(Identity identity) {
        return identity != null &&
                this.map.containsKey(identity.asShortString());
    }

    /**
     * @param identity
     * @param version
     * @return
     */
    @Override
    public boolean contains(Identity identity, Version version) {
        return identity != null &&
                this.map.containsKey(identity.asShortString()) &&
                this.map.get(identity.asShortString()).containsKey(version);
    }

    /**
     * Returns an element from repository.
     *
     * @param identity
     * @return Returns the element if repository contains the object; Otherwise <tt>null</tt>.
     */
    @Override
    public Map<Version, T> get(Identity identity) {
        if (this.contains(identity)) {
            return this.map.get(identity.asShortString());
        }

        return null;
    }

    /**
     * @param identity
     * @param version
     * @return
     */
    public T get(Identity identity, Version version) {
        if (this.contains(identity, version)) {
            return this.map.get(identity.asShortString()).get(version);
        }

        return null;
    }

    /**
     * Removes all elements from repository.
     */
    @Override
    public void clear() {
        this.map.clear();
    }

    /**
     * Returns a collection with all elements stored in this object.
     *
     * @return Returns a collection with all elements stored in this object.
     */
    @Override
    public Collection<Map<Version, T>> elements() {
        return new ArrayList<>(this.map.values());
    }

    /**
     * Returns a map with all elements stored in this object.
     *
     * @return Returns a collection with all elements stored in this object.
     */
    @Override
    public Map<String, Map<Version, T>> asMap() {
        return new HashMap<>(this.map);
    }
}
