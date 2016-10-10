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
package org.panthercode.arctic.core.resources.helper;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.helper.identity.Identity;
import org.panthercode.arctic.core.reflect.ClassBuilder;
import org.panthercode.arctic.core.resources.Resource;
import org.panthercode.arctic.core.settings.configuration.Configuration;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

//TODO: update implementation
//TODO: documentation
/**
 *
 */
public class ResourceFactory {
    /**
     *
     */
    private ResourceFactory() {
    }

    /**
     * @param tClass
     * @param name
     * @param group
     * @param <T>
     * @return
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static <T extends Resource> Resource create(Class<T> tClass, String name, String group)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return ResourceFactory.create(tClass, name, group, null);
    }

    /**
     * @param tClass
     * @param name
     * @param group
     * @param configuration
     * @param <T>
     * @return
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static <T extends Resource> Resource create(Class<T> tClass, String name, String group, Configuration configuration)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return ResourceFactory.create(tClass, Identity.generate(name, group), configuration);
    }

    /**
     * @param tClass
     * @param identity
     * @param <T>
     * @return
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T extends Resource> Resource create(Class<T> tClass, Identity identity)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return ResourceFactory.create(tClass, identity, null);
    }

    /**
     * @param tClass
     * @param identity
     * @param configuration
     * @param <T>
     * @return
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T extends Resource> Resource create(Class<T> tClass, Identity identity, Configuration configuration)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ArgumentUtils.assertNotNull(identity, "identity");

        Resource resource = ClassBuilder.create(tClass).setArguments(identity).build();

        if (configuration != null) {
            resource.configure(configuration);
        }

        return resource;
    }

    /**
     * @param path
     * @param name
     * @param group
     * @return
     * @throws NoSuchMethodException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws ClassNotFoundException
     */
    public static Resource create(String path, String name, String group)
            throws NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        return ResourceFactory.create(path, name, group, null);
    }

    /**
     * @param path
     * @param name
     * @param group
     * @param configuration
     * @return
     * @throws NoSuchMethodException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws ClassNotFoundException
     */
    public static Resource create(String path, String name, String group, Configuration configuration)
            throws NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        return ResourceFactory.create(path, Identity.generate(name, group), configuration);
    }

    /**
     * @param path
     * @param identity
     * @return
     * @throws NoSuchMethodException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws ClassNotFoundException
     */
    public static Resource create(String path, Identity identity)
            throws NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        return ResourceFactory.create(path, identity, null);
    }

    /**
     * @param path
     * @param identity
     * @param configuration
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static Resource create(String path, Identity identity, Configuration configuration)
            throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ArgumentUtils.assertNotNull(path, "path");
        ArgumentUtils.assertNotNull(identity, "identity");

        List<Class<?>> classes = ClassUtils.getClassesFromJarFile(path, Resource.class);

        if (classes.isEmpty()) {
            throw new ClassNotFoundException("");//TODO: find no resource class in jar file
        }

        if (classes.size() > 1) {
            throw new RuntimeException(""); //TODO: there are more than one possibilities.
        }

        Class<Resource> resourceClass = (Class<Resource>) classes.get(0);

        return ResourceFactory.create(resourceClass, identity, configuration);
    }
}
