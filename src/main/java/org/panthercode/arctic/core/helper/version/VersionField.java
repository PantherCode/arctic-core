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

/**
 * Enumeration to represent the parts of the version number.
 */
public enum VersionField {
    /**
     * major part of version number
     */
    MAJOR("major"),

    /**
     * minor part of version number
     */
    MINOR("minor"),

    /**
     * build part of version number
     */
    BUILD("build"),

    /**
     * revision part of version number
     */
    REVISION("revision");

    /**
     * string value of part
     */
    private final String value;

    /**
     * constructor
     *
     * @param value string value of field
     */
    VersionField(String value) {
        this.value = value;
    }

    /**
     * Returns a string representing the specific part of version number.
     *
     * @return Returns a string representing the specific part of version number.
     */
    @Override
    public String toString() {
        return this.value;
    }
}
