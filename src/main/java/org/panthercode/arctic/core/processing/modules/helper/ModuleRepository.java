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
package org.panthercode.arctic.core.processing.modules.helper;

import org.panthercode.arctic.core.helper.identity.IdentityInfo;
import org.panthercode.arctic.core.helper.version.VersionInfo;

//TODO: implement auto-identification via annotation
//TODO: change CloneNotSupportedException

/**
 * This class is used to hold a set of modules.
 */
@IdentityInfo(name = "Module Repository", group = "Repository")
@VersionInfo(major = 1)
public class ModuleRepository  {

//    /**
//     * TODO: Repository constructor without identity parameter -> annotation
//     * Constructor
//     *
//     * @param identity identity associated with this object
//     */
//    public ModuleRepository(Identity identity) {
//        super(identity);
//    }
//
//    /**
//     * Returns a new casted instance of a specific module given by its name.
//     *
//     * @param moduleClass class type of module
//     * @param name        name of module
//     * @param <T>         generic type of module
//     * @return Returns a new casted instance of module, if repository contains module and no ClassCastExceptions is
//     * thrown; Otherwise <tt>null</tt>.
//     * @throws CloneNotSupportedException Is thrown if module doesn't support cloning.
//     */
//    public <T extends Module> T order(Class<T> moduleClass, final String name)
//            throws CloneNotSupportedException {
//        return this.order(moduleClass, name, null);
//    }
//
//    /**
//     * Returns a new casted instance of a specific module given by its name.
//     *
//     * @param moduleClass class type of module
//     * @param name        name of module
//     * @param context     context of new module instance
//     * @param <T>         generic type of module
//     * @return Returns a new casted instance of module, if repository contains module and no ClassCastExceptions is
//     * thrown; Otherwise <tt>null</tt>.
//     * @throws CloneNotSupportedException Is thrown if module doesn't support cloning.
//     */
//    @SuppressWarnings("unchecked")
//    public <T extends Module> T order(Class<T> moduleClass, final String name, final Context context)
//            throws CloneNotSupportedException {
//        return (T) this.order(name, context);
//    }
//
//    /**
//     * Returns a new instance of a specific module given by its name.
//     *
//     * @param name name of module
//     * @return Returns a new casted instance of module, if repository contains module; Otherwise <tt>null</tt>.
//     * @throws CloneNotSupportedException Is thrown if module doesn't support cloning.
//     */
//    public Module order(final String name) throws CloneNotSupportedException {
//        return this.order(name, null);
//    }
//
//    /**
//     * Returns a new instance of a specific module given by its name.
//     *
//     * @param name    name of module
//     * @param context context of new module instance
//     * @return Returns a new casted instance of module, if repository contains module; Otherwise <tt>null</tt>.
//     * @throws CloneNotSupportedException Is thrown if module doesn't support cloning.
//     */
//    public Module order(final String name, final Context context) throws CloneNotSupportedException {
//        if (this.contains(name)) {
//            try {
//                Module module = this.get(name).copy();
//
//                if (context != null) {
//                    module.setContext(context);
//                }
//
//                return module;
//            } catch (ClassCastException e) {
//                return null;
//            }
//        }
//
//        return null;
//    }
}
