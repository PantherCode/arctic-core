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
package org.panthercode.arctic.core.io;

import org.panthercode.arctic.core.arguments.ArgumentUtils;

import java.io.*;

/**
 * Utils class that contains a set of functions to deal with files.
 */
public class IOUtils {

    /**
     * private constructor
     */
    private IOUtils() {
    }

    /**
     * Opens a specific file as BufferedReader.
     *
     * @param path path to file
     * @return Returns an opened BufferedReader.
     * @throws IllegalArgumentException Is thrown if path is null.
     * @throws FileNotFoundException    Is thrown if the file path doesn't exist.
     */
    public static BufferedReader openBufferedReader(final String path)
            throws IllegalArgumentException, FileNotFoundException {
        ArgumentUtils.assertNotNull(path, "path");

        FileReader fileReader = new FileReader(path);

        return new BufferedReader(fileReader);
    }

    /**
     * Opens a specific file as BufferedReader.
     *
     * @param path path to file
     * @return Returns an opened BufferedWriter.
     * @throws IllegalArgumentException Is thrown if path is null.
     * @throws IOException              Is thrown if an error occurs while opening the file.
     */
    public static BufferedWriter openBufferedWriter(final String path)
            throws IllegalArgumentException, IOException {
        return IOUtils.toBufferedWriter(path, false);
    }

    /**
     * Opens a specific file as BufferedReader.
     *
     * @param path path to file
     * @return Returns an opened BufferedWriter.
     * @throws IllegalArgumentException Is thrown if path is null.
     * @throws IOException              Is thrown if an error occurs while opening the file.
     */
    public static BufferedWriter toBufferedWriter(final String path, final boolean append)
            throws IllegalArgumentException, IOException {
        ArgumentUtils.assertNotNull(path, "path");

        FileWriter fileWriter = new FileWriter(path, append);

        return new BufferedWriter(fileWriter);
    }
}
