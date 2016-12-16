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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
public class ParameterBinding {

    Object object;

    Parameter[] parameters;

    private ParameterBinding(Object object, Parameter[] parameters) {
        this.parameters = parameters;
        this.object = object;
    }

    public static ParameterBinding fromConstructor(Object object, String name) {
        ArgumentUtils.assertNotNull(object, "object");
        ArgumentUtils.assertNotNull(name, "name");

        for (Constructor<?> constructor : object.getClass().getConstructors()) {
            ParameterSources sources = constructor.getAnnotation(ParameterSources.class);

            if (sources != null && sources.name().equals(name)) {
                return new ParameterBinding(object, constructor.getParameters());
            }
        }

        throw new RuntimeException("Can't find constructor");
    }

    public static ParameterBinding fromMethod(Object object, String name) {
        ArgumentUtils.assertNotNull(object, "object");
        ArgumentUtils.assertNotNull(name, "name");

        for (Method method : object.getClass().getDeclaredMethods()) {
            ParameterSources sources = method.getAnnotation(ParameterSources.class);

            if (sources != null) {
                return new ParameterBinding(object, method.getParameters());
            }
        }

        throw new RuntimeException("Can't find method");
    }

    //TODO: exception handling
    public void bind(Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Map<String, String> map = new HashMap<>();

        for (Method method : this.object.getClass().getDeclaredMethods()) {
            ParameterTarget target = method.getAnnotation(ParameterTarget.class);

            if (target != null) {
                map.put(target.name(), method.getName());
            }
        }

        int index = 0;

        for (int i = 0; i < parameters.length; i++) {
            ParameterSource source = this.parameters[i].getAnnotation(ParameterSource.class);

            if (source != null) {
                String methodName = map.get(source.name());

                MethodUtils.invokeMethod(object,
                        methodName,
                        args[i] == null ? this.cast(source.type(), source.defaultValue()) : cast(source.type(), args[i]));

                index++;
            }
        }
    }

    public <T> T cast(Class<T> clazz, Object value) {
        if (clazz.equals(String.class)) {
            return clazz.cast(value);
        }

        if (clazz.equals(Byte.class)) {
            return clazz.cast(value);
        }

        if (clazz.equals(Integer.class)) {
            return clazz.cast(value);
        }

        if (clazz.equals(Double.class)) {
            return clazz.cast(value);
        }

        if (clazz.equals(Float.class)) {
            return clazz.cast(value);
        }

        if (clazz.equals(Boolean.class)) {
            return clazz.cast(value);
        }

        if (clazz.equals(Long.class)) {
            return clazz.cast(value);
        }

        if (clazz.equals(Character.class)) {
            return clazz.cast(value);
        }

        if (clazz.equals(Short.class)) {
            return clazz.cast(value);
        }

        throw new IllegalArgumentException("Unknown class type");
    }
}
