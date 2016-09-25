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

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.helper.identity.Identity;
import org.panthercode.arctic.core.processing.modules.Module;
import org.panthercode.arctic.core.reflect.ClassBuilder;
import org.panthercode.arctic.core.reflect.ClassUtils;
import org.panthercode.arctic.core.settings.context.Context;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

//TODO: update implementation
//TODO: documentation
/**
 *
 */
public class ModuleFactory {
    /**
     *
     */
    private ModuleFactory() {
    }

    /**
     *
     * @param tClass
     * @param name
     * @param group
     * @param <T>
     * @return
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T extends Module> Module create(Class<T> tClass, String name, String group)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return ModuleFactory.create(tClass, name, group, null);
    }

    /**
     *
     * @param tClass
     * @param name
     * @param group
     * @param context
     * @param <T>
     * @return
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static <T extends Module> Module create(Class<T> tClass, String name, String group, Context context)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return ModuleFactory.create(tClass, Identity.generate(name, group), context);
    }

    /**
     *
     * @param tClass
     * @param identity
     * @param <T>
     * @return
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static <T extends Module> Module create(Class<T> tClass, Identity identity)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return ModuleFactory.create(tClass, identity, null);
    }

    /**
     *
     * @param tClass
     * @param identity
     * @param context
     * @param <T>
     * @return
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T extends Module> Module create(Class<T> tClass, Identity identity, Context context)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ArgumentUtils.assertNotNull(identity, "identity");

        Module module = ClassBuilder.create(tClass).setArguments(identity).build();

        if (context != null) {
            module.setContext(context);
        }

        return module;
    }

    /**
     *
     * @param path
     * @param name
     * @param group
     * @param <T>
     * @return
     * @throws NoSuchMethodException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws ClassNotFoundException
     */
    public static <T extends Module> Module create(String path, String name, String group)
            throws NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        return ModuleFactory.create(path, name, group, null);
    }

    /**
     *
     * @param path
     * @param name
     * @param group
     * @param context
     * @param <T>
     * @return
     * @throws NoSuchMethodException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws ClassNotFoundException
     */
    public static <T extends Module> Module create(String path, String name, String group, Context context)
            throws NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        return ModuleFactory.create(path, Identity.generate(name, group), context);
    }

    /**
     *
     * @param path
     * @param identity
     * @param <T>
     * @return
     * @throws NoSuchMethodException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws ClassNotFoundException
     */
    public static <T extends Module> Module create(String path, Identity identity)
            throws NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        return ModuleFactory.create(path, identity, null);
    }

    /**
     *
     * @param path
     * @param identity
     * @param context
     * @param <T>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static <T extends Module> Module create(String path, Identity identity, Context context)
            throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        ArgumentUtils.assertNotNull(path, "path");
        ArgumentUtils.assertNotNull(identity, "identity");

        List<Class<?>> classes = ClassUtils.getClassesFromJarFile(path, Module.class);

        if (classes.isEmpty()) {
            throw new ClassNotFoundException("");//TODO: find no module class in jar file
        }

        if (classes.size() > 1) {
            throw new RuntimeException(""); //TODO: there are more than one possibilities.
        }

        Class<Module> moduleClass = (Class<Module>) classes.get(0);

        return ModuleFactory.create(moduleClass, identity, context);
    }
}
