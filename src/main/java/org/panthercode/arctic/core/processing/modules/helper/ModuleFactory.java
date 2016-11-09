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

    public static <T extends Module> T create(String path)
            throws NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        return ModuleFactory.create(path, null);
    }

    /**
     * @param path
     * @param
     * @param <T>
     * @return
     * @throws NoSuchMethodException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws ClassNotFoundException
     */
    public static <T extends Module> T create(String path, Context context)
            throws NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        return ModuleFactory.create(path, null, context);
    }

    /**
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
    public static <T extends Module> T create(String path, Identity identity, Context context)
            throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        ArgumentUtils.assertNotNull(path, "path");
        ArgumentUtils.assertNotNull(identity, "identity");

        //TODO: extend ClassUtils with annotation filter
        List<Class<?>> classes = null; // ClassUtils.getClassesFromJarFile(path, RootModule.class);

        if (classes.isEmpty()) {
            throw new ClassNotFoundException("The file doesn't contains a root element.");
        }

        if (classes.size() > 1) {
            throw new RuntimeException("The file contains more than one root element.");
        }

        Class<T> moduleClass = (Class<T>) classes.get(0);

        //TODO: check annotation of class

        return ModuleFactory.create(moduleClass, identity, context);
    }

    /**
     * @param tClass
     * @param <T>
     * @return
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T extends Module> T create(Class<T> tClass)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return ModuleFactory.create(tClass, null);
    }

    /**
     * @param tClass
     * @param
     * @param <T>
     * @return
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static <T extends Module> T create(Class<T> tClass, Context context)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return ModuleFactory.create(tClass, null, context);
    }

    /**
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
    public static <T extends Module> T create(Class<T> tClass, Identity identity, Context context)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ArgumentUtils.assertNotNull(identity, "identity");

        return ClassBuilder.create(tClass).setArguments(identity, context).build();
    }
}
