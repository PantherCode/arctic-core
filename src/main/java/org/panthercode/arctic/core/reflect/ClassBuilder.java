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

import org.apache.commons.lang3.reflect.ConstructorUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Utility class to create new instances of objects
 */
public class ClassBuilder<T> {

    /**
     * class type of new instances
     */
    Class<T> clazz = null;

    /**
     * parameter for constructor call
     */
    Object[] arguments = null;

    /**
     * private constructor
     *
     * @param clazz class type of new instances
     */
    private ClassBuilder(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * Sets a new class type to builder.
     *
     * @param clazz type of new instances
     * @return Returns the actual instance to builder class.
     */
    public ClassBuilder<T> setClass(Class<T> clazz) {
        this.clazz = clazz;

        return this;
    }

    /**
     * Sets parameteter, which are use to call the constructor. The order or argument objects must matching the signature
     * of the constructor. The first argument is the first parameter of the constructor and so on.
     *
     * @param arguments parameter, which are used.
     * @return Returns the actual instance to builder class.
     */
    public ClassBuilder<T> setArguments(Object... arguments) {
        this.arguments = arguments;

        return this;
    }

    /**
     * Create a new instance of the given class with stored  arguments.
     *
     * @return Returns a new instance.
     * @throws NoSuchMethodException     Is thrown if no matching constructor exists to the given argument signature.
     * @throws IllegalAccessException    Is thrown if invocation is not permitted by security. For example the constructor
     *                                   you would like to call has not modification <tt>public</tt>.
     * @throws InstantiationException    Is thrown if an error occurs on instantiation.
     * @throws InvocationTargetException Is thrown if an error occurs on invocation.
     */
    public T build()
            throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        return ConstructorUtils.invokeConstructor(clazz, this.arguments);
    }

    /**
     * Creates an new builder.
     *
     * @param clazz class of new instances the builder should create
     * @param <T>   type to be constructed
     * @return Returns a new instance of a builder.
     */
    public static <T> ClassBuilder<T> create(Class<T> clazz) {
        return new ClassBuilder<T>(clazz);
    }
}
