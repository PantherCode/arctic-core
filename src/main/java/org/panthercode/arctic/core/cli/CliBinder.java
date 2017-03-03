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
 * The <tt>CliBinder</tt> class is used to read commandline arguments automatically. For parsing the arguments
 * a list of <tt>Option</tt>s is created from Apache's <tt>commons-cli</tt> library.
 * <p>
 * The following example reads the age of a person from commandline and
 * <pre>
 * public class Person{
 *
 *     ...
 *
 *     private int age = 0;
 *
 *     public Person(){
 *         ...
 *     }
 *
 *     &#064;CommandLineParameter(name="age", shortName='a', hasValue=true, defaultValue="0", description="age of a person")
 *     public void setAge(int age){
 *         this.age = age;
 *     }
 *
 *     public int getAge(){
 *         return this.age;
 *     }
 * }
 *
 * ...
 *
 * //commandline: ... --age 50 ...
 * public static void main(String[] args){
 *      CommandLineBinder binder =  CommandLineBinder.bind(Person.class).parse(args);
 *
 *      Person person = binder.from(Person.class, new Person());
 *
 *      System.out.println(person.getAge()) //print 50
 * }
 * </pre>
 * If the commandline containsHandler a unknown parameter the parsing process will fail with an exception. The list of allowed
 * parameters is the sum of annotated setter functions in each class. It's possible to bind as much classes you want,
 * but each name of each parameter must be unique.
 * <p>
 * The <tt>CliBinder</tt> class can only convert strings to the following types: <tt>Byte, Short, Int, Long,
 * Float, Double, Char</tt> and <tt>Boolean</tt>. If you try to convert to custom or other higher class types the
 * converting process will throw an <tt>IllegalArgumentException</tt>, because the binder doesn't know to handle it.
 *
 * @author PantherCode
 * @see CliParameter
 * @since 1.0
 */
public class CliBinder {

    /**
     * actual commandline
     */
    private CommandLine commandLine;

    /**
     * list with all classes binding at commandline
     */
    private List<Class<?>> classList;

    /**
     * Constructor
     *
     * @param classList   list with bounded classes
     * @param commandLine actual commandline
     */
    private CliBinder(List<Class<?>> classList, CommandLine commandLine) {
        this.commandLine = commandLine;
        this.classList = classList;
    }

    /**
     * Creates a new builder for <tt>CommandLineBinder</tt>.
     *
     * @return Returns a new instance of a builder.
     */
    public static CliBindingBuilder create() {
        return new CliBindingBuilder();
    }

    /**
     * Sets all parameter values to given object by invoking the corresponding setter function.
     *
     * @param clazz  class type of the object
     * @param object used object to set values
     * @param <T>    generic class type
     * @return Returns the updated object.
     * @throws NoSuchMethodException     Is thrown if searched function doesn't exist.
     * @throws IllegalAccessException    Is thrown if the setter function has to less privileged access.
     * @throws InvocationTargetException Is thrown if an error is occurred while invoking setter function.
     * @throws NullPointerException      Is thrown if one the parameters is <tt>null</tt>.
     */
    public <T> T from(Class<T> clazz, T object)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NullPointerException {
        ArgumentUtils.checkNotNull(clazz, "class");
        ArgumentUtils.checkNotNull(object, "object");

        if (!this.classList.contains(clazz)) {
            throw new IllegalArgumentException("The class is unknown.");
        }

        CliParameter option;

        for (Method method : clazz.getDeclaredMethods()) {
            option = method.getAnnotation(CliParameter.class);

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

    /**
     * Function to cast string value to corresponding value type.
     *
     * @param clazz class of value type
     * @param value value as string
     * @param <T>   generic class type
     * @return Returns the value as needed value type.
     */
    private <T> T cast(Class<T> clazz, String value) {
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

    /**
     * Helper class to create a <tt>CommandLineBinder</tt> class.
     */
    public static class CliBindingBuilder {
        /**
         * list with all class binding to commandline
         */
        private List<Class<?>> classList = new ArrayList<>();

        /**
         * Constructor
         */
        public CliBindingBuilder() {
        }

        /**
         * Adds a new class to commandline.
         *
         * @param clazz class to bind
         * @return Returns the actual instance of builder class.
         */
        public CliBindingBuilder bind(Class<?> clazz) {
            if (clazz != null && !this.classList.contains(clazz)) {
                this.classList.add(clazz);
            }

            return this;
        }

        /**
         * Parse the given commandline arguments by creating an <tt>Option</tt>s list with all <tt>CommandLineParameter</tt>
         * from classes in the list.
         *
         * @param args commandline arguments
         * @return Returns a new instance of <tt>CommandLineBuilder</tt>.
         * @throws ParseException Is thrown if an error is occurred while parsing commandline arguments.
         */
        public CliBinder parse(String[] args)
                throws ParseException, IllegalArgumentException {
            DefaultParser parser = new DefaultParser();
            Options options = new Options();

            Set<String> nameList = new TreeSet<String>();
            Set<Character> shortNameList = new TreeSet<>();

            CliParameter option;

            for (Class<?> c : this.classList) {
                for (Method method : c.getDeclaredMethods()) {
                    option = method.getAnnotation(CliParameter.class);

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

            return new CliBinder(this.classList, parser.parse(options, args));
        }
    }
}
