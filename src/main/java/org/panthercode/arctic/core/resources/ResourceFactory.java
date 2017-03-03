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
import org.panthercode.arctic.core.concurrent.Semaphore;
import org.panthercode.arctic.core.processing.priority.Priority;
import org.panthercode.arctic.core.reflect.ReflectionUtils;
import org.panthercode.arctic.core.settings.Configuration;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.List;

/**
 * TODO: documentation
 *
 * @author PantherCode
 * @since 1.0
 */
public class ResourceFactory {

    /**
     * Private Constructor
     */
    private ResourceFactory() {
    }

    @SuppressWarnings("unchecked")
    public List<Class<?>> load(Path path)
            throws IOException, ClassNotFoundException {
        List<Class<?>> classes = ReflectionUtils.extractClassesFromJar(path);

        return ReflectionUtils.filterClassList(classes, AbstractResource.class);
    }

    public static Resource create(Class<? extends AbstractResource> resourceClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return ResourceFactory.create(resourceClass, null);
    }

    public static Resource create(Class<? extends AbstractResource> resourceClass, Configuration configuration) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        return ResourceFactory.create(resourceClass, configuration, null);
    }

    public static Resource create(Class<? extends AbstractResource> resourceClass, Configuration configuration, Semaphore<Priority> semaphore) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ArgumentUtils.checkNotNull(resourceClass, "resource class");

        Resource resource = null;

        if (semaphore != null && resourceClass.isInstance(AbstractCriticalResource.class)) {
            resource = resourceClass.getConstructor(Semaphore.class).newInstance(semaphore);
        } else {
            resource = resourceClass.getConstructor().newInstance();
        }

        resource.configure(configuration);

        return resource;
    }
}
