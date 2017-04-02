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
package org.panthercode.arctic.core.helper.identity;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.panthercode.arctic.core.reflect.ReflectionUtils;

import java.util.Random;

/**
 * The identity class is used to manage objects in a much better way. The class offers the possibility to representing
 * an object by a name. It's also possible to group more the one object in the same set of instances that belongs
 * together.
 * <p>
 * Each object has an identifier which represents in a (pseudo) unique manner. But it's no guaranteed at all. For
 * generating a new identifier a toHash code consisting of name and group name is calculated and multiplied with a random
 * number. Therefore the result of <tt>new Identity("name","group").equals(new Identity("name","group"))</tt>
 * returns always false.
 * <p>
 * To create an new instance of the identity class use the generate() function:
 * <tt>Identity identity = Identity.generate(...);</tt>
 *
 * @author PantherCode
 * @see Identifiable
 * @see IdentityInfo
 * @since 1.0
 */
public final class Identity {

    /**
     * unique identifier of object
     */
    private long id;

    /**
     * name of object
     */
    private String name;

    /**
     * name of group the object belongs to
     */
    private String group;

    /**
     * private constructor
     *
     * @param id    unique identifier
     * @param name  name of object
     * @param group name of group the object belongs to
     */
    private Identity(long id, String name, String group) {
        this.setId(id);
        this.setName(name);
        this.setGroup(group);
    }

    /**
     * Returns the identifier of the object.
     *
     * @return Returns the identifier of the object.
     */
    public long id() {
        return this.id;
    }

    /**
     * Set a new identifier. If the new identifier is less than zero the absolute value is used. This function is
     * <tt>synchronized</tt>.
     *
     * @param id new identifier
     */
    private synchronized void setId(final long id) {
        this.id = Math.abs(id);
    }

    /**
     * Returns the name of the object.
     *
     * @return Returns the name of the object.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set a new name if the object can be modified. If the value is null the name is set to 'unknown'.
     * This function is <tt>synchronized</tt>.
     *
     * @param name new object name
     */
    public synchronized void setName(final String name) {
        this.name = (name == null) ? "unknown" : name;
    }

    /**
     * Returns the group name the object belongs to.
     *
     * @return Returns the group name the object belongs to.
     */
    public String getGroup() {
        return this.group;
    }

    /**
     * Set a new group name if the object can be modified. If the value is null the group name is set to 'default'.
     * This function is <tt>synchronized</tt>.
     *
     * @param group new group name
     */
    public synchronized void setGroup(final String group) {
        this.group = (group == null) ? "default" : group;
    }

    /**
     * Returns a string representation of the object. The representation contains the id, name and group name.
     *
     * @return Returns a string representation of the object.
     */
    @Override
    public String toString() {
        return "id = " + this.id + ", " + this.asShortString();
    }

    /**
     * Returns a string representation of the object. The representation contains the name and group name.
     *
     * @return Returns a string representation of the object.
     */
    public String asShortString() {
        return "name = " + this.name + ", group = " + this.group;
    }

    /**
     * Creates and returns a copy of this object. The identifier of the copied object will be generated again.
     * If you try <tt>identity.equals(identity.copy())</tt> the result always is <tt>false</tt>. This is done
     * to prevent objects with different body but same id.
     *
     * @return Return a copy of this object.
     */
    public Identity copy() {
        return new Identity(this.id, this.name, this.group);
    }

    /**
     * Checks if an object offers an IdentityInfo annotation.
     *
     * @param clazz object to check
     * @return Returns <tt>true</tt> if the object offers an IdentityInfo annotation; Otherwise <tt>false</tt>.
     */
    public static <T> boolean isAnnotated(final Class<T> clazz) {
        return ReflectionUtils.isAnnotated(clazz, IdentityInfo.class);
    }

    /**
     * Create a new identity from the annotation information given by an object. If the annotation is empty or the
     * object has no annotation at all a default identity object is returned.
     *
     * @return Returns an identity made of annotation information.
     */
    public static <T> Identity fromAnnotation(final Class<T> clazz) {
        if (clazz != null) {
            IdentityInfo info = clazz.getAnnotation(IdentityInfo.class);

            if (info != null) {
                return Identity.generate(info.name(), info.group());
            }
        }

        return Identity.generate();
    }

    /**
     * Returns a toHash code value of this object.
     *
     * @return Returns a toHash code value of this object.
     */
    @Override
    public int hashCode() {
        return Math.abs(new HashCodeBuilder()
                .append(this.id)
                .append(this.name)
                .append(this.group)
                .toHashCode());
    }

    /**
     * Compares two identity objects and check if both have same name and same group.
     *
     * @param identity identity to check
     * @return Returns <tt>true</tt> if both objects have same name and same group.
     */
    public boolean match(Identity identity) {
        if (identity == this) {
            return true;
        }

        if (identity == null) {
            return false;
        }

        return this.getName().equals(identity.getName()) &&
                this.getGroup().equals(identity.getGroup());
    }

    /**
     * Checks if this object is equals to another one. Two identity objects are equal if they have the same name and
     * the same group name. The identifier is ignored.
     *
     * @param obj other object to check
     * @return Returns <tt>true</tt> if two identity objects have the same name and group name.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Identity)) {
            return false;
        }

        Identity identity = (Identity) obj;

        return this.id() == identity.id() &&
                this.getName().equals(identity.getName()) &&
                this.getGroup().equals(identity.getGroup());
    }

    /**
     * Generates a new identity object. The name is set to 'unknown' and group name to 'default'.
     *
     * @return Returns a new identity object.
     */
    public static Identity generate() {
        return Identity.generate(null);
    }

    /**
     * Generates a new identity object. The group name is set to 'default'.
     *
     * @param name new object name
     * @return Returns a new identity object.
     */
    public static Identity generate(final String name) {
        return Identity.generate(name, null);
    }

    /**
     * Generates a new identity object with given object and group name.
     * This function is <tt>synchronized</tt>.
     *
     * @param name  new object name
     * @param group new group name
     * @return Returns a new identity object.
     */
    public static Identity generate(final String name, final String group) {
        Random seed = new Random(System.currentTimeMillis());

        HashCodeBuilder builder = new HashCodeBuilder();

        long id = builder.append(name)
                .append(group).toHashCode();
        id = Math.abs(id * seed.nextLong());

        return new Identity(id, name, group);
    }
}
