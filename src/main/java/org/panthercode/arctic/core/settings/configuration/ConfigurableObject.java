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
 * Abstract class that implements the <tt>Configurable</tt> interface.
 */
public abstract class ConfigurableObject implements Configurable {

    /**
     * configuration of the object
     */
    protected Configuration configuration = new Configuration();

    /**
     * Standard Constructor
     */
    public ConfigurableObject() {
    }

    /**
     * Constructor
     *
     * @param configuration new configuration
     */
    public ConfigurableObject(Configuration configuration){
        this.configure(configuration);
    }

    /**
     * Set the configuration associated with this object.
     *
     * @param configuration new configuration
     */
    public void configure(Configuration configuration){
        if(configuration != null) {
            this.configuration = configuration;
        }
    }

    /**
     * Returns the configuration associated with this object.
     *
     * @return Returns the configuration associated with this object.
     */
    public Configuration configuration(){
        return this.configuration;
    }
}
