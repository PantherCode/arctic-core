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
package org.panthercode.arctic.core.reflect;

import org.panthercode.arctic.core.arguments.ArgumentUtils;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * Created by architect on 25.09.16.
 */
public class ClassUtils {
    /**
     * private constructor
     */
    private ClassUtils() {
    }

    /**
     * Reads all names of classes as path from a specific .jar file.
     *
     * @param path filepath to .jar file
     * @return Returns a list of class paths
     * @throws IOException              Is thrown if an error occurs while reading the .jar file.
     * @throws IllegalArgumentException Is thrown if path is null.
     */
    public static List<String> getClassNamesFromJarFile(String path)
            throws IllegalArgumentException, IOException {
        ArgumentUtils.assertNotNull(path, "path");

        List<String> classNames = new ArrayList<String>();

        JarFile jarFile = new JarFile(path);
        Enumeration<JarEntry> entries = jarFile.entries();
        JarEntry currentEntry = null;
        String currentClassName = null;

        while (entries.hasMoreElements()) {
            currentEntry = entries.nextElement();
            currentClassName = currentEntry.getName().replace("/", ".");

            if (currentClassName.endsWith(".class")) {
                classNames.add(currentClassName);
            }
        }

        return classNames;
    }

    /**
     * Creates a list of all classes from a specific .jar file.
     *
     * @param path filepath to .jar file
     * @return Returns a list with all classes in .jar file. If .jar doesn't contain any class, the list will be empty.
     * @throws IOException              Is thrown if an error occurs while reading the .jar file.
     * @throws ClassNotFoundException   Is thrown if the class is not found.
     * @throws IllegalArgumentException Is thrown if path is null.
     */
    public static List<Class<?>> getClassesFromJarFile(String path)
            throws IOException, ClassNotFoundException, IllegalArgumentException {
        ArgumentUtils.assertNotNull(path, "path");

        List<Class<?>> classes = new ArrayList<Class<?>>();

        URL url = new URL("jar:file:" + path + "!/");
        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
        //TODO: filter
        for (String name : ClassUtils.getClassNamesFromJarFile(path)) {
            name = name.substring(0, name.lastIndexOf(".class"));
            Class<?> clazz = classLoader.loadClass(name);
            classes.add(clazz);
        }

        return classes;
    }

    /**
     * Creates a list of classes fitting the filter from a specific .jar file.
     *
     * @param path   filepath to .jar file
     * @param filter super class or interface to get classes of specific type.
     * @return Returns a filtered list.
     * @throws IOException              Is thrown if an error occurs while reading the .jar file.
     * @throws ClassNotFoundException   Is thrown if class is not found.
     * @throws IllegalArgumentException Is thrown if path is null.
     */
    public static List<Class<?>> getClassesFromJarFile(String path, Class<?> filter)
            throws IOException, ClassNotFoundException, IllegalArgumentException {
        ArgumentUtils.assertNotNull(path, "path");
        ArgumentUtils.assertNotNull(filter, "filter");

        List<Class<?>> classes = ClassUtils.getClassesFromJarFile(path).stream().filter(clazz -> org.apache.commons.lang3.ClassUtils.getAllInterfaces(clazz).contains(filter) ||
                org.apache.commons.lang3.ClassUtils.getAllSuperclasses(clazz).contains(filter)).collect(Collectors.toList());

        return classes;
    }

    /**
     * Casts an object to it's real type.
     *
     * @param obj object, that should cast
     * @param <T> type to be casted
     * @return Returns a the casted object if object isn't null; Otherwise null.
     */
    public static <T> T cast(Object obj) {
        if (obj != null) {
            Class<T> clazz = (Class<T>) obj.getClass();
            return (T) obj;
        }

        return null;
    }
}
