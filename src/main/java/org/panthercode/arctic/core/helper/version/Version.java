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
package org.panthercode.arctic.core.helper.version;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.helper.Freezable;
import org.panthercode.arctic.core.helper.version.annotation.VersionInfo;

/**
 * This class is used to control and manage objects by version. The scheme follows the major.minor[.build[.revision]]
 * pattern.
 */
public class Version implements Freezable {

    /**
     * major number of version
     */
    private int major = 0;

    /**
     * minor number of version
     */
    private int minor = 0;

    /**
     * build number of version
     */
    private int build = 0;

    /**
     * revision number of version
     */
    private int revision = 0;

    /**
     * flag to allow modifications
     */
    private boolean canModify = true;

    /**
     * standard constructor
     */
    public Version() {
        this(0, 0);
    }

    /**
     * constructor
     *
     * @param major major number of version
     * @param minor minor number of version
     */
    public Version(int major, int minor) {
        this(major, minor, 0, 0);
    }

    /**
     * constructor
     *
     * @param major    major number of version
     * @param minor    minor number of version
     * @param build    build number of version
     * @param revision revision number of version
     */
    public Version(int major, int minor, int build, int revision) {
        this.set(major, minor, build, revision);
    }

    /**
     * constructor
     *
     * @param info version class annotation
     */
    public Version(VersionInfo info) {
        ArgumentUtils.assertNotNull(info, "version information");

        this.set(info.major(), info.minor(), info.revision(), info.build());
    }

    /**
     * copy constructor
     *
     * @param version version object to copy
     */
    public Version(Version version) {
        ArgumentUtils.assertNotNull(version, "version");

        this.set(version.majorNumber(), version.minorNumber(), version.revisionNumber(), version.buildNumber());
    }

    /**
     * Increment the major number. This function is <code>synchronized</code>.
     */
    public synchronized void upgrade() {
        if (this.canModify) {
            this.major++;
        }
    }

    /**
     * Increment the minor number. This function is <code>synchronized</code>.
     */
    public synchronized void update() {
        if (this.canModify) {
            this.minor++;
        }
    }

    /**
     * Increment the build number. This function is <code>synchronized</code>.
     */
    public synchronized void build() {
        if (this.canModify) {
            this.build++;
        }
    }

    /**
     * Increment the revision number. This function is <code>synchronized</code>.
     */
    public synchronized void review() {
        if (this.canModify) {
            this.revision++;
        }
    }

    /**
     * Returns the major number of version.
     *
     * @return Returns the major number of version.
     */
    public int majorNumber() {
        return this.major;
    }

    /**
     * Returns the minor number of version.
     *
     * @return Returns the minor number of version.
     */
    public int minorNumber() {
        return this.minor;
    }

    /**
     * Returns the build number of version.
     *
     * @return Returns the build number of version.
     */
    public int buildNumber() {
        return this.build;
    }

    /**
     * Returns the revision number of version.
     *
     * @return Returns the major number of version.
     */
    public int revisionNumber() {
        return this.revision;
    }

    /**
     * Set all fields to zero. After reset the version has the value <tt>0.0.0.0</tt>. This function is
     * <code>synchronized</code>.
     */
    public synchronized void reset() {
        if (this.canModify) {
            this.major = this.minor = this.build = this.revision = 0;
        }
    }

    /**
     * Set the value of a specific field. This function is <code>synchronized</code>.
     *
     * @param field version field to set
     * @param value new value
     * @throws IllegalArgumentException Is throw if the value is less than zero.
     */
    public synchronized void set(final VersionField field, final int value)
            throws IllegalArgumentException {
        if (this.canModify) {
            ArgumentUtils.assertGreaterOrEqualsZero(value, field.toString());

            switch (field) {
                case MAJOR:
                    this.major = value;
                    break;
                case MINOR:
                    this.minor = value;
                    break;
                case BUILD:
                    this.build = value;
                    break;
                case REVISION:
                    this.revision = value;
                    break;
            }
        }
    }

    /**
     * Set new values to version. This function is <code>synchronized</code>.
     *
     * @param major    new major number
     * @param minor    new minor number
     * @param build    new build number
     * @param revision new revision number
     * @throws IllegalArgumentException Is thrown if a value is less than zero.
     */
    public synchronized void set(final int major, final int minor, final int build, final int revision)
            throws IllegalArgumentException {
        if (this.canModify) {
            ArgumentUtils.assertGreaterOrEqualsZero(major, "major");
            ArgumentUtils.assertGreaterOrEqualsZero(minor, "minor");
            ArgumentUtils.assertGreaterOrEqualsZero(build, "build");
            ArgumentUtils.assertGreaterOrEqualsZero(revision, "revision");

            this.major = major;
            this.minor = minor;
            this.build = build;
            this.revision = revision;
        }
    }

    /**
     * Checks if an object offers a VersionInfo annotation.
     *
     * @param obj object to check
     * @return Returns <code>true</code> if the object offers an VersionInfo annotation; Otherwise <code>false</code>.
     */
    public synchronized static boolean isAnnotated(final Object obj) {
        return obj != null && obj.getClass().getAnnotation(VersionInfo.class) != null;

    }

    /**
     * Create a new version from the annotation information given by the object. If the annotation is empty or the
     * object has no annotation at all a default identity object is returned.
     *
     * @param object object with annotation
     * @return Returns a new version object made of annotation information.
     */
    public synchronized static Version fromAnnotation(final Object object) {
        if (object != null) {
            VersionInfo info = object.getClass().getAnnotation(VersionInfo.class);

            if (info != null) {
                return new Version(info);
            }
        }

        return new Version();
    }

    /**
     * Parse a string to an version object. The function splits the string at each "." character and converts the parts
     * to numbers. If the string contains other characters than numeric ones an exception is thrown. If the string has
     * more than 4 parts the rest is ignored. This function is <code>synchronized</code>.
     *
     * @param version version as string
     * @return Returns a new version object.
     * @throws NumberFormatException Is thrown if the string contains not only numeric characters.
     */
    public synchronized static Version parse(final String version)
            throws NumberFormatException {
        if (version == null) {
            return new Version();
        }

        int[] fields = {0, 0, 0, 0};

        String[] parts = version.split("\\.");

        int length = parts.length > 4 ? 4 : parts.length;

        for (int i = 0; i < length; i++) {
            fields[i] = Integer.parseInt(parts[i]);
        }

        return new Version(fields[0], fields[1], fields[2], fields[3]);
    }

    /**
     * Returns a string representing the version object. The function always returns the major and minor number, also in
     * case one value or both are zero. If the build or revision number is zero the value is ignored, expect the build
     * number is zero and revision number is greater zero the full version is print out.
     * <p>
     * Example: <tt>1.0.0.0</tt> returns <tt>1.0</tt>, <tt>1.0.1.0</tt> returns <tt>1.0.1</tt> and
     * <tt>1.0.0.1</tt> returns <tt>1.0.0.1</tt>
     *
     * @return Returns a string representing the version object.
     */
    @Override
    public String toString() {
        String str = this.major + "." + this.minor;

        str += (this.build == 0) ?
                ((this.revision == 0) ? "" : "." + this.revision) :
                "." + this.revision + "." + this.build;

        return str;
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
     * Checks if this object is equals to another one. Two version objects are equals if the have the same field values.
     *
     * @param obj other object to check
     * @return Returns <code>true</code> if both objects have the same values; Otherwise <code>false</code>.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Version)) {
            return false;
        }

        Version version = (Version) obj;
        return (this.major == version.majorNumber() && this.minor == version.minorNumber() &&
                this.revision == version.revisionNumber() && this.build == version.buildNumber());
    }

    /**
     * Returns the hash code value of this object.
     *
     * @return Returns the hash code value of this object.
     */
    @Override
    public int hashCode() {
        return Math.abs(new HashCodeBuilder()
                .append(this.major)
                .append(this.minor)
                .append(this.revision)
                .append(this.build)
                .toHashCode());
    }
}
