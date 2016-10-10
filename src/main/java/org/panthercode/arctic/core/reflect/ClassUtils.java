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
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
    public static List<String> extractClassNamesFromJar(String path)
            throws NullPointerException, IOException {
        ArgumentUtils.assertNotNull(path, "path");

        JarFile jarFile = new JarFile(path);

        List<String> classNameList = new ArrayList<>();

        Enumeration<JarEntry> entries = jarFile.entries();

        for (JarEntry currentEntry = entries.nextElement();
             entries.hasMoreElements();
             currentEntry = entries.nextElement()) {
            if (currentEntry != null &&
                    currentEntry.getName().endsWith(".class")) {
                classNameList.add(currentEntry.getName().replace("/", ".").split("\\.class", 2)[0]);
            }
        }

        return classNameList;
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
    public static List<Class<?>> extractClassesFromJar(String path)
            throws IOException, ClassNotFoundException, IllegalArgumentException {
        ArgumentUtils.assertNotNull(path, "path");

        List<Class<?>> classList = new ArrayList<>();

        URL[] urlArray = {new URL("jar:file:" + path + "!/")};
        URLClassLoader classLoader = new URLClassLoader(urlArray);

        for (String className : ClassUtils.extractClassNamesFromJar(path)) {
            classList.add(classLoader.loadClass(className));
        }

        return classList;
    }

    /**
     * @param classList
     * @param filter
     * @return
     * @throws NullPointerException
     */
    public static List<Class<?>> filterClassList(final List<Class<?>> classList, final Class<?> filter)
            throws NullPointerException {
        ArgumentUtils.assertNotNull(classList, "list");
        ArgumentUtils.assertNotNull(filter, "filter");

        final List<Class<?>> filteredList = new ArrayList<>();

        filteredList.forEach(item -> {
            if (item.isInstance(filter)) {
                filteredList.add(item);
            }
        });

        return filteredList;
    }

    /**
     * @param classList
     * @param filter
     * @return
     * @throws NullPointerException
     */
    public static List<Class<?>> filterClassListByAnnotation(final List<Class<?>> classList,
                                                             final Class<? extends Annotation> filter)
            throws NullPointerException {
        ArgumentUtils.assertNotNull(classList, "list");
        ArgumentUtils.assertNotNull(filter, "filter");

        final List<Class<?>> filteredList = new ArrayList<>();

        filteredList.forEach(item -> {
            if (ClassUtils.isAnnotated(item, filter)) {
                filteredList.add(item);
            }
        });

        return filteredList;
    }

    /**
     * @param clazz
     * @param annotation
     * @return
     */
    public static boolean isAnnotated(final Class<?> clazz, final Class<? extends Annotation> annotation) {
        return clazz != null && annotation != null && clazz.getAnnotation(annotation) != null;
    }
}
