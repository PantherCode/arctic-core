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
import org.panthercode.arctic.core.helper.Freezable;
import org.panthercode.arctic.core.helper.identity.annotation.IdentityInfo;

import java.util.Random;

/**
 * The identity class is used to manage objects in a much better way. The class offers the possibility to representing
 * an object by a name. It's also possible to group more the one object in the same set of instances that belongs
 * together.
 * <p>
 * Each object has an identifier which represents in a (pseudo) unique manner. But it's no guaranteed at all. For
 * generating a new identifier a hash code consisting of name and group name is calculated and multiplied with a random
 * number. Therefore the result of <code>new Identity("name","group").equals(new Identity("name","group"))</code>
 * returns always false.
 * <p>
 * To create an new instance of the identity class use the generate() function:
 * <code>Identity identity = Identity.generate(...);</code>
 */
public final class Identity implements Freezable {

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
     * flag to allow modifications
     */
    private boolean canModify = true;

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
     * <code>synchronized</code>.
     *
     * @param id new identifier
     */
    private synchronized void setId(long id) {
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
     * This function is <code>synchronized</code>.
     *
     * @param name new object name
     * @throws RuntimeException Is thrown if the name is tried to modify, but it's not allowed.
     */
    public synchronized void setName(String name)
            throws RuntimeException {
        if (this.canModify) {
            this.name = (name == null) ? "unknown" : name;
        } else {
            throw new RuntimeException("The object is frozen and can't be modified.");
        }
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
     * This function is <code>synchronized</code>.
     *
     * @param group new group name
     * @throws RuntimeException Is thrown if the group name is tried to modify, but it's not allowed.
     */
    public synchronized void setGroup(String group)
            throws RuntimeException {
        if (this.canModify) {
            this.group = (group == null) ? "default" : group;
        } else {
            throw new RuntimeException("The object is frozen and can't be modified.");
        }
    }

    /**
     * Returns a string representation of the object. The representation contains the id, name and group name.
     *
     * @return Returns a string representation of the object.
     */
    @Override
    public String toString() {
        return "id = " + this.id + ", name = " + this.name + ", group = " + this.group;
    }

    /**
     * Creates and returns a copy of this object. The identifier of the copied object will be generated again.
     * If you try <code>identity.equals(identity.clone())</code> the result always is <code>false</code>. This is done
     * to prevent objects with different content but same id.
     *
     * @return Return a copy of this object.
     */
    @Override
    public Identity clone() {
        return Identity.generate(this.name, this.group);
    }

    /**
     * Freeze this object. The object can't be modified. This function is <code>synchronized</code>.
     */
    @Override
    public synchronized void freeze() {
        this.canModify = false;
    }

    /**
     * Unfreeze this object. The object can be modified. This function is <code>synchronized</code>.
     */
    @Override
    public synchronized void unfreeze() {
        this.canModify = true;
    }

    /**
     * Returns a flag representing the object can be modified or not.
     *
     * @return Returns a boolean flag representing the object can be modified or not.
     */
    @Override
    public boolean canModify() {
        return this.canModify;
    }

    /**
     * Checks if an object offers an IdentityInfo annotation.
     *
     * @param object object to check
     * @return Returns <code>true</code> if the object offers an IdentityInfo annotation; Otherwise <code>false</code>.
     */
    public static boolean isAnnotated(Object object) {
        return object != null && object.getClass().getAnnotation(IdentityInfo.class) != null;

    }

    /**
     * Create a new identity from the annotation information given by an object. If the annotation is empty or the
     * object has no annotation at all a default identity object is returned.
     *
     * @return Returns an identity made of annotation information.
     */
    public static Identity fromAnnotation(Object object) {
        if (object != null) {
            IdentityInfo info = object.getClass().getAnnotation(IdentityInfo.class);

            if (info != null) {
                return Identity.generate(info.name(), info.group());
            }
        }

        return Identity.generate();
    }

    /**
     * Returns a hash code value of this object.
     *
     * @return Returns a hash code value of this object.
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
     * Checks if this object is equals to another one. Two identity objects are equal if they have the same name and
     * the same group name. The identifier is ignored.
     *
     * @param obj other object to check
     * @return Returns <code>true</code> if two identity objects have the same name and group name.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Identity)) {
            return false;
        }

        Identity identity = (Identity) obj;
        return this.getName().equals(identity.getName())
                && this.getGroup().equals(identity.getGroup());
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
    public static Identity generate(String name) {
        return Identity.generate(name, null);
    }

    /**
     * Generates a new identity object with given object and group name.
     * This function is <code>synchronized</code>.
     *
     * @param name  new object name
     * @param group new group name
     * @return Returns a new identity object.
     */
    public static Identity generate(String name, String group) {
        Random seed = new Random(System.currentTimeMillis());

        HashCodeBuilder builder = new HashCodeBuilder();
        long id = builder.append(name)
                .append(group).toHashCode();
        id = Math.abs(id * seed.nextLong());

        return new Identity(id, name, group);
    }
}
