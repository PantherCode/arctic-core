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
package org.panthercode.arctic.core.settings;

import org.panthercode.arctic.core.settings.configuration.Configuration;

/**
 * Convert the configuration class to singleton object. This class is used if it's enough to handle configuration items
 * in one instance. Otherwise if it's more elegant to divide item in separate instances than use the
 * <tt>ConfigurationManager</tt> class.
 */
public class Settings extends Configuration {

    /**
     * static instance
     */
    private static Settings instance;

    /**
     * private constructor
     */
    private Settings(){}

    /**
     * Returns the settings object instance.
     *
     * @return Returns the settings object instance.
     */
    public static Settings instance(){
        if(instance  == null){
            instance = new Settings();
        }

        return instance;
    }
}
