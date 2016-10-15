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
package org.panthercode.arctic.core.xml;

import org.panthercode.arctic.core.arguments.ArgumentUtils;

import javax.xml.bind.JAXB;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Utils class that contains a set of functions to handle xml files and objects.
 */
public class XmlUtils {

    /**
     * private constructor
     */
    private XmlUtils() {
    }

    /**
     * Writes an object to a xml file.
     *
     * @param path path to xml file
     * @param obj  object to store in xml file
     * @throws IOException Is thrown if an error occurs while writing in file.
     */
    public static void writeToFile(final String path, final Object obj)
            throws IOException {
        ArgumentUtils.assertNotNull(obj, "object");

        try (FileOutputStream stream = new FileOutputStream(path)) {
            JAXB.marshal(obj, stream);
        }
    }

    /**
     * Reads in an object from a xml file.
     *
     * @param path  path to file
     * @param clazz class type of returned object
     * @param <T>   type of return type
     * @return Returns an object with given type.
     * @throws IOException Is thrown if an error occurs while reading the file.
     */
    public static <T> T readFromFile(final String path, final Class<T> clazz)
            throws IOException {
        ArgumentUtils.assertNotNull(path, "path");
        ArgumentUtils.assertNotNull(clazz, "clazz");

        T result;

        try (FileInputStream stream = new FileInputStream(path)) {
            result = JAXB.unmarshal(stream, clazz);
        }

        return result;
    }

    //Todo: Implement methods to load file in Document class
}
