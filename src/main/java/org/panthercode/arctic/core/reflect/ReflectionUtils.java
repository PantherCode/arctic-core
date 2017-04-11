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

import org.apache.commons.lang3.reflect.MethodUtils;
import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.settings.OptionalField;
import org.panthercode.arctic.core.settings.OptionalFields;
import org.panthercode.arctic.core.settings.RequiredField;
import org.panthercode.arctic.core.settings.RequiredFields;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Utility class that contains methods to load classes from jar archives and filter lists.
 *
 * @author PantherCode
 * @sinec 1.0
 */
public class ReflectionUtils {
    /**
     * private Constructor
     */
    private ReflectionUtils() {
    }

    /**
     * Invokes a method to a specific object. The order of arguments must matching the signature of the method.
     * The first argument is the first parameter of the invoked method and so on.
     *
     * @param object       object to invoke method
     * @param functionName method getName to invoke
     * @param arguments    parameters the method will be called with
     * @throws NoSuchMethodException     Is thrown if no matching method is found.
     * @throws IllegalAccessException    Is thrown if invocation is not permitted by security. For example the constructor
     *                                   you would like to call has not modification <tt>public</tt>.
     * @throws InvocationTargetException Is thrown if an error occurs on invocation.
     */
    public static void invokeMethod(Object object, String functionName, Object... arguments)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ArgumentUtils.checkNotNull(object, "object");
        ArgumentUtils.checkNotNull(functionName, "function name");

        MethodUtils.invokeMethod(object, functionName, arguments);
    }

    /**
     * Invokes a method to a specific object. The order arguments must matching the signature fo the method.
     * The first argument is the first parameter of the invoked method and so on.
     *
     * @param object       object to invoke method
     * @param functionName method getName to invoke
     * @param returnType   class type of returned object
     * @param arguments    parameters the method will be called with
     * @param <T>          type of returned object
     * @return Returns the output of the invoked method.
     * @throws NoSuchMethodException     Is thrown if no matching mehtod is found.
     * @throws IllegalAccessException    Is thrown if invovation is not permitted by security. security. For example the constructor
     *                                   you would like to call has not modification <tt>public</tt>.
     * @throws InvocationTargetException Is thrown if  an error occurs on invocation.
     */
    @SuppressWarnings("unchecked")
    public static <T> T invokeMethod(Object object, String functionName, Class<T> returnType, Object... arguments)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ArgumentUtils.checkNotNull(object, "object");
        ArgumentUtils.checkNotNull(functionName, "function name");
        ArgumentUtils.checkNotNull(returnType, "return type");

        return returnType.cast(MethodUtils.invokeMethod(object, functionName, arguments));
    }

    /**
     * Reads all names of classes as path from a specific jar file.
     *
     * @param path filepath to jar file
     * @return Returns a list of class paths
     * @throws IOException              Is thrown if an error occurs while reading the .jar file.
     * @throws IllegalArgumentException Is thrown if path is null.
     */
    public static List<String> extractClassNamesFromJar(Path path)
            throws NullPointerException, IOException {
        ArgumentUtils.checkNotNull(path, "path");

        if (!Files.exists(path)) {
            throw new FileNotFoundException("Can't find the given path.");
        }

        List<String> classNameList = new ArrayList<>();

        try (JarFile jarFile = new JarFile(path.toString())) {
            Enumeration<JarEntry> entries = jarFile.entries();

            for (JarEntry currentEntry = entries.nextElement();
                 entries.hasMoreElements();
                 currentEntry = entries.nextElement()) {
                if (currentEntry != null &&
                        currentEntry.getName().endsWith(".class")) {
                    classNameList.add(currentEntry.getName().replace("/", ".").split("\\.class", 2)[0]);
                }
            }
        }

        return classNameList;
    }

    /**
     * Creates a list of all classes from a specific jar file.
     *
     * @param path filepath to jar file
     * @return Returns a list with all classes in jar file. If jar file doesn't contain any class, the list will be empty.
     * @throws IOException              Is thrown if an error occurs while reading the .jar file.
     * @throws ClassNotFoundException   Is thrown if the class is not found.
     * @throws IllegalArgumentException Is thrown if path is null.
     */
    public static List<Class<?>> extractClassesFromJar(Path path)
            throws IOException, ClassNotFoundException, IllegalArgumentException {
        ArgumentUtils.checkNotNull(path, "path");

        if (!Files.exists(path)) {
            throw new FileNotFoundException("Can't find the given path.");
        }

        List<Class<?>> classList = new ArrayList<>();

        URL[] urlArray = {new URL("jar:file:" + path + "!/")};

        try (URLClassLoader classLoader = new URLClassLoader(urlArray)) {
            for (String className : ReflectionUtils.extractClassNamesFromJar(path)) {
                classList.add(classLoader.loadClass(className));
            }
        }

        return classList;
    }

    /**
     * Filters a list of classes with a given pattern.
     *
     * @param classList list with classes
     * @param filter    pattern for filtering
     * @return Returns a filtered list.
     * @throws NullPointerException Is thrown if one of the parameters is <tt>null</tt>.
     */
    public static List<Class<?>> filterClassList(final List<Class<?>> classList, final Class<?> filter)
            throws NullPointerException {
        ArgumentUtils.checkNotNull(classList, "list");
        ArgumentUtils.checkNotNull(filter, "filter");

        final List<Class<?>> filteredList = new ArrayList<>();

        for (Class<?> clazz : classList) {
            if (filter.isAssignableFrom(clazz)) {
                filteredList.add(clazz);
            }
        }

        return filteredList;
    }

    /**
     * Filters a list of classes with a given pattern.
     *
     * @param classList list with classes
     * @param filter    pattern for filtering
     * @return Returns a filtered list.
     * @throws NullPointerException Is thrown if one of the parameters is <tt>null</tt>.
     */
    public static List<Class<?>> filterClassListByAnnotation(final List<Class<?>> classList,
                                                             final Class<? extends Annotation> filter) {
        ArgumentUtils.checkNotNull(classList, "list");
        ArgumentUtils.checkNotNull(filter, "filter");

        final List<Class<?>> filteredList = new ArrayList<>();

        for (Class<?> clazz : classList) {
            if (clazz.isAnnotationPresent(filter)) {
                filteredList.add(clazz);
            }
        }

        return filteredList;
    }

    /**
     * Returns a flag that indicates whether the given class is annotated with a specified annotation or not.
     *
     * @param clazz      class to check
     * @param annotation annotation to check
     * @return Returns <tt>true</tt> if the class is annotated with the given annotation; Otherwise <tt>false</tt>.
     */
    public static boolean isAnnotated(final Class<?> clazz, final Class<? extends Annotation> annotation) {
        return clazz != null && annotation != null && clazz.isAnnotationPresent(annotation);
    }

    public static RequiredField[] getRequiredFields(final Class<?> clazz) {
        if (clazz == null) {
            return null;
        } else if (clazz.isAnnotationPresent(RequiredField.class)) {
            return new RequiredField[]{clazz.getAnnotation(RequiredField.class)};
        } else if (clazz.isAnnotationPresent(RequiredFields.class)) {
            return clazz.getAnnotation(RequiredFields.class).value();
        }

        return new RequiredField[]{};
    }

    public static OptionalField[] getOptionalFields(final Class<?> clazz) {
        if (clazz == null) {
            return null;
        } else if (clazz.isAnnotationPresent(OptionalField.class)) {
            return new OptionalField[]{clazz.getAnnotation(OptionalField.class)};
        } else if (clazz.isAnnotationPresent(OptionalFields.class)) {
            return clazz.getAnnotation(OptionalFields.class).value();
        }

        return new OptionalField[]{};
    }
}
