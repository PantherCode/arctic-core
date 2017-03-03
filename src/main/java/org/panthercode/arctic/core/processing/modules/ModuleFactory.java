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
package org.panthercode.arctic.core.processing.modules;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.reflect.ReflectionUtils;
import org.panthercode.arctic.core.settings.Context;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.List;

//TODO: update implementation
//TODO: documentation

/**
 * Utility class with functions to load modules from jar files or create new instances of module classes.
 *
 * @author PantherCode
 * @since 1.0
 */
public class ModuleFactory {

    /**
     * Private Constructor
     */
    private ModuleFactory() {
    }

    /**
     * Loads all modules annoted as <tt>RootModule</tt> from jar file.
     *
     * @param path path of jar file
     * @return Returns a list with found classes.
     * @throws IOException            Is thrown if an error occurred while reading the .jar file.
     * @throws ClassNotFoundException Is thrown if the class is not found.
     */
    @SuppressWarnings("unchecked")
    public List<Class<?>> load(Path path)
            throws IOException, ClassNotFoundException {
        List<Class<?>> classes = ReflectionUtils.extractClassesFromJar(path);

        return ReflectionUtils.filterClassListByAnnotation(classes, RootModule.class);
    }

    /**
     * Creates a new instance of a module class.
     *
     * @param moduleClass module class type
     * @return Returns a new instance of given module class.
     * @throws NoSuchMethodException     Is thrown if no standard constructor was found.
     * @throws IllegalAccessException    Is thrown if standart constructor is not <tt>public</tt>.
     * @throws InvocationTargetException Is thrown if an error occurred while invoking constructor.
     * @throws InstantiationException    Is thrown if an error occurred while invoking constructor.
     */
    public static Module create(Class<? extends Module> moduleClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return ModuleFactory.create(moduleClass, null);
    }

    /**
     * Creates a new instance of a module class.
     *
     * @param moduleClass module class type
     * @param context     context of module instance
     * @return Returns a new instance of given module class.
     * @throws NoSuchMethodException     Is thrown if no standard constructor was found.
     * @throws IllegalAccessException    Is thrown if standart constructor is not <tt>public</tt>.
     * @throws InvocationTargetException Is thrown if an error occurred while invoking constructor.
     * @throws InstantiationException    Is thrown if an error occurred while invoking constructor.
     */
    public static Module create(Class<? extends Module> moduleClass, Context context) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        ArgumentUtils.checkNotNull(moduleClass, "module class");

        Module module = moduleClass.getConstructor().newInstance();

        module.setContext(context);

        return module;
    }
}
