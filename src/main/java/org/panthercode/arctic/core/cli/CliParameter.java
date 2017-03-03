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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The <tt>Parameter</tt> annotation is used to read parameter from the command line automatically and call
 * the annotated setter function.
 * </p>
 * The following example combine the parameter <tt>--port</tt> or <tt>-p</tt> with the corresponding setter function:
 * <p>
 * <pre>
 * &#064;Parameter(name="port", shortName='p', hasValue=true, defaultValue="1234", type = Integer.class, description="used server port")
 * public void setPort(int port){
 *     this.port = port;
 * }
 * </pre>
 * Or to set flags:
 * * <p>
 * <pre>
 * &#064;Parameter(name="ssh", shortName='s', defaultValue="false", type = Boolean.class, description="use ssh for communication")
 * public void useSsh(boolean flag){
 *     this.useSsh = flag;
 * }
 * </pre>
 *
 * @author PantherCode
 * @see CliBinder
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CliParameter {
    /**
     * Returns the long name of commandline parameter. Usually it's introduced by <tt>--name</tt>.
     *
     * @return Returns the (long) name of the commandline parameter.
     */
    String name() default "";

    /**
     * Returns the short name of commandline parameter. Usally it's introduced by <tt>-name</tt>
     *
     * @return Returns the short name of the commandline parameter.
     */
    char shortName() default ' ';

    /**
     * The description is shown in help text at terminal.
     *
     * @return Returns the description of the commandline parameter.
     */
    String description() default "";

    /**
     * Returns a flag that indicates whether the parameter expected a value.
     *
     * @return Returns <tt>true</tt> if the parameter expected a value; Otherwise <tt>false</tt>.
     */
    boolean hasValue() default false;

    /**
     * Returns a default value for the parameter if it is not set explicitly at command line.
     *
     * @return Returns the default value of the parameter.
     */
    String defaultValue() default "";

    /**
     * Returns the class type the parameter value should converted to. Each value coming from command line is interpreted
     * as commonly string literal. To auto convert the string to correct value a type can set optionally.
     *
     * @return Returns the class the parameter's value should converted to.
     */
    Class<?> type() default String.class;
}
