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
package org.panthercode.arctic.core.resources;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.helper.identity.IdentityInfo;
import org.panthercode.arctic.core.helper.priority.Priority;
import org.panthercode.arctic.core.helper.version.VersionInfo;
import org.panthercode.arctic.core.reflect.ClassBuilder;
import org.panthercode.arctic.core.settings.Configuration;

import java.lang.reflect.InvocationTargetException;

//import org.panthercode.arctic.core.helper.priority.Semaphore;

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
public class ResourceFactory {

    public static String CAPACITY_KEY = "capacity";

    public static String PRIORITY_KEY = "priority";

    /**
     *
     */
    private ResourceFactory() {
    }

    public static <T extends AbstractResource> Resource create(Class<T> clazz)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return ResourceFactory.create(clazz, null);
    }

    public static <T extends AbstractResource> Resource create(Class<T> clazz, Configuration configuration)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, ClassCastException {
        ArgumentUtils.assertNotNull(clazz, "class");

        if (!clazz.isAnnotationPresent(IdentityInfo.class)) {
            throw new IllegalArgumentException("The resource class has no IdentityInfo annotation");
        }

        if (!clazz.isAnnotationPresent(VersionInfo.class)) {
            throw new IllegalArgumentException("The resource class has no VersionInfo annotation");
        }

        configuration = configuration == null ? new Configuration() : configuration;

        Priority priority = configuration.containsKey(PRIORITY_KEY) ? (Priority) configuration.get(PRIORITY_KEY) : Priority.NORMAL;

        int capacity = configuration.containsKey(CAPACITY_KEY) ? (int) configuration.get(CAPACITY_KEY) : 1;

        AbstractResource abstractResource = ClassBuilder.create(AbstractResource.class).build();

        //TODO: fix
        return null; //new ResourceImpl(abstractResource, new Semaphore(capacity), priority, configuration);
    }
}
