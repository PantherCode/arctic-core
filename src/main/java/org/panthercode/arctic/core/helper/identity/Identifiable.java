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

/**
 * Interface to make classes better identifiable.
 *
 * @author PantherCode
 * @see Identity
 * @see IdentityInfo
 * @since 1.0
 */
public interface Identifiable {

    /**
     * Returns the identity of an object.
     *
     * @return Returns the identity of an object.
     */
    Identity identity();
}
