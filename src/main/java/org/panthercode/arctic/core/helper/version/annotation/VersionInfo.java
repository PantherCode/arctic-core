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
package org.panthercode.arctic.core.helper.version.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to set the version of an class. It's possible to generate the version object automatically by
 * using the <tt>Version.fromAnnotation()</tt> function.
 * <p>
 * Example: <tt>@VersionInfo(major = 1, minor = 2, build = 3, build = 4)</tt> stands for 1.2.3.4
 *
 * @author PantherCode
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface VersionInfo {
    /**
     * Returns the major number of version.
     *
     * @return Returns the major number of version.
     */
    int major() default 0;

    /**
     * Returns the minor number of version.
     *
     * @return Returns the minor number of version.
     */
    int minor() default 0;

    /**
     * Returns the build number of version.
     *
     * @return Returns the build number of version.
     */
    int build() default 0;

    /**
     * Returns the revision number of version.
     *
     * @return Returns the revision number of version.
     */
    int revision() default 0;
}
