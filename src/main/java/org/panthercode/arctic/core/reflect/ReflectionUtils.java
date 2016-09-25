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

import java.lang.reflect.InvocationTargetException;

/**
 * Created by architect on 25.09.16.
 */
public class ReflectionUtils {
    /**
     * private constructor
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
     *                                   you would like to call has not modification <code>public</code>.
     * @throws InvocationTargetException Is thrown if an error occurs on invocation.
     */
    public static void invokeMethod(Object object, String functionName, Object... arguments)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
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
     *                                   you would like to call has not modification <code>public</code>.
     * @throws InvocationTargetException Is thrown if  an error occurs on invocation.
     */
    public static <T> T invokeMethod(Object object, String functionName, Class<T> returnType, Object... arguments)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return (T) MethodUtils.invokeMethod(object, functionName, arguments);
    }
}
