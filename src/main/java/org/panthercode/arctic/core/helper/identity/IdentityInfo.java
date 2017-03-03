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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to set the identity of an class. It's used to generate the identity object automatically.
 * <p>
 * Example: <tt>@IdentityInfo(name = "exampleName", group= "exampleGroup)</tt>
 *
 * @author PantherCode
 * @see Identifiable
 * @see Identity
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface IdentityInfo {
    /**
     * Returns the name of object.
     *
     * @return Returns the name of object.
     */
    String name() default "unknown";

    /**
     * Returns the group name the object belongs to.
     *
     * @return Returns the group name the object belongs to.
     */
    String group() default "default";
}