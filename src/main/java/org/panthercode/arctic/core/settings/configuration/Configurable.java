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
package org.panthercode.arctic.core.settings.configuration;

/**
 * Interface to make an object configurable.
 *
 * @author PantherCode
 */
public interface Configurable {

    /**
     * Set the configuration associated with the object.
     *
     * @param configuration new configuration associated with the object
     */
    void configure(Configuration configuration);

    /**
     * Returns the configuration associated with the object.
     *
     * @return Returns the configuration associated with the object.
     */
    Configuration configuration();
}
