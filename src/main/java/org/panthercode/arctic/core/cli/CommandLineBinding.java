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
package org.panthercode.arctic.core.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.panthercode.arctic.core.arguments.ArgumentUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
public class CommandLineBinding {

    private CommandLine commandLine;

    private List<Class<?>> classList;

    private CommandLineBinding(List<Class<?>> classList, CommandLine commandLine) {
        this.commandLine = commandLine;
        this.classList = classList;
    }

    public static CommandLineBindingBuilder create() {
        return new CommandLineBindingBuilder();
    }

    //TODO: better exception handling
    public <T> T from(Class<T> c, T object)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ArgumentUtils.assertNotNull(c, "class");
        ArgumentUtils.assertNotNull(object, "object");

        if (!this.classList.contains(c)) {
            throw new IllegalArgumentException("The class is unknown.");
        }

        CommandLineParameter option;

        for (Method method : c.getDeclaredMethods()) {
            option = method.getAnnotation(CommandLineParameter.class);

            if (option != null) {
                if (!option.hasValue() && option.type().equals(Boolean.class)) {
                    MethodUtils.invokeMethod(object,
                            method.getName(),
                            commandLine.hasOption(option.name()));
                } else {
                    MethodUtils.invokeMethod(object,
                            method.getName(),
                            this.cast(option.type(), commandLine.getOptionValue(option.name(), option.defaultValue())));
                }
            }
        }

        return object;
    }

    public static <T> T cast(Class<T> clazz, String value) {
        if (clazz.equals(String.class)) {
            return clazz.cast(value);
        }

        if (clazz.equals(Byte.class)) {
            return clazz.cast(Integer.valueOf(value));
        }

        if (clazz.equals(Integer.class)) {
            return clazz.cast(Integer.valueOf(value));
        }

        if (clazz.equals(Double.class)) {
            return clazz.cast(Double.valueOf(value));
        }

        if (clazz.equals(Float.class)) {
            return clazz.cast(Float.valueOf(value));
        }

        if (clazz.equals(Boolean.class)) {
            return clazz.cast(Boolean.valueOf(value));
        }

        if (clazz.equals(Long.class)) {
            return clazz.cast(Long.valueOf(value));
        }

        if (clazz.equals(Character.class)) {
            return clazz.cast(value.charAt(0));
        }

        if (clazz.equals(Short.class)) {
            return clazz.cast(Short.valueOf(value));
        }

        throw new IllegalArgumentException("Unknown class type");
    }

    public static class CommandLineBindingBuilder {
        private List<Class<?>> classList = new ArrayList<>();

        public CommandLineBindingBuilder() {
        }

        public CommandLineBindingBuilder bind(Class<?> c) {
            if (c != null && !this.classList.contains(c)) {
                this.classList.add(c);
            }

            return this;
        }

        public CommandLineBinding parse(String[] args)
                throws ParseException {
            DefaultParser parser = new DefaultParser();
            Options options = new Options();

            Set<String> nameList = new TreeSet<String>();
            Set<Character> shortNameList = new TreeSet<>();

            CommandLineParameter option;

            for (Class<?> c : this.classList) {
                for (Method method : c.getDeclaredMethods()) {
                    option = method.getAnnotation(CommandLineParameter.class);

                    if (option != null) {
                        if (option.name().trim().isEmpty()) {
                            throw new IllegalArgumentException(c.getName() + "." + method.getName() + "(): The parameter name is empty.");
                        }

                        if (nameList.contains(option.name())) {
                            throw new IllegalArgumentException(c.getName() + "." + method.getName() + "(): The parameter name is already used.");
                        }

                        if (option.shortName() != ' ' && shortNameList.contains(option.shortName())) {
                            throw new IllegalArgumentException(c.getName() + "." + method.getName() + "(): The parameter short name is already used");
                        }

                        nameList.add(option.name());
                        shortNameList.add(option.shortName());

                        options.addOption(option.shortName() == ' ' ? null : String.valueOf(option.shortName()),
                                option.name(),
                                option.hasValue(),
                                option.description());
                    }
                }
            }

            return new CommandLineBinding(this.classList, parser.parse(options, args));
        }
    }
}
