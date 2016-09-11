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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Utilclass that contains a set of functions to handle files.
 */
public class IOUtils {

    /**
     * private constructor
     */
    private IOUtils() {
    }

    /**
     * Opens a specific file path as FileInputStream.
     *
     * @param path path to file
     * @return Returns an opened FileInputStream.
     * @throws IllegalArgumentException Is thrown if path is null.
     * @throws FileNotFoundException    Is thrown if the filepath doesn't exist.
     */
    public static FileInputStream toFileIntputStream(String path)
            throws IllegalArgumentException, FileNotFoundException {
        ArgumentUtils.assertNotNull(path, "path");

        File file = new File(path);

        return new FileInputStream(file);
    }

    /**
     * Opens a specific file path as BufferedReader.
     *
     * @param path path to file
     * @return Returns an opened BufferedReader.
     * @throws IllegalArgumentException Is thrown if path is null.
     * @throws FileNotFoundException    Is thrown if the filepath doesn't exist.
     */
    public static BufferedReader toBufferedReader(String path)
            throws IllegalArgumentException, FileNotFoundException {
        ArgumentUtils.assertNotNull(path, "path");

        FileReader fileReader = new FileReader(path);

        return new BufferedReader(fileReader);
    }

    /**
     * Opens a specific file path as FileOutputStream.
     *
     * @param path path to file
     * @return Returns an opened FileOutputStream.
     * @throws IllegalArgumentException Is thrown if path is null.
     * @throws FileNotFoundException    Is thrown if the filepath doesn't exist.
     */
    public static FileOutputStream toFileOutputStream(String path)
            throws IllegalArgumentException, FileNotFoundException {
        ArgumentUtils.assertNotNull(path, "path");

        File file = new File(path);

        return new FileOutputStream(file);
    }

    /**
     * Opens a specific file path as BufferedReader.
     *
     * @param path path to file
     * @return Returns an opened BufferedWriter.
     * @throws IllegalArgumentException Is thrown if path is null.
     * @throws IOException              Is thrown if an error occurs while opening the file.
     */
    public static BufferedWriter toBufferedWriter(String path)
            throws IllegalArgumentException, IOException {
        ArgumentUtils.assertNotNull(path, "path");

        FileWriter fileWriter = new FileWriter(path);

        return new BufferedWriter(fileWriter);
    }

    /**
     * Reads a text file completely and store the content as string.
     *
     * @param path path to file
     * @return Returns a String with file content.
     * @throws IllegalArgumentException Is thrown if path is null.
     * @throws IOException              Is thrown if an error occurs while reading the file.
     */
    public static String readTextFile(String path)
            throws IllegalArgumentException, IOException {
        ArgumentUtils.assertNotNull(path, " path");

        StringBuilder builder = new StringBuilder();

        try (BufferedReader reader = IOUtils.toBufferedReader(path)) {
            while (reader.ready()) {
                builder.append(reader.readLine());
                builder.append("\n");

            }
        }

        return builder.toString();
    }

    /**
     * Reads a text file completely and store the content in a list. For each line
     * in the file one entry is added to list.
     *
     * @param path path to file
     * @return Returns a List with file content.
     * @throws IllegalArgumentException Is thrown if path is null.
     * @throws IOException              Is thrown if an error occurs while reading the file.
     */
    public static List<String> readTextFileAsList(String path)
            throws IllegalArgumentException, IOException {
        ArgumentUtils.assertNotNull(path, "path");

        List<String> fileContent = new ArrayList<>();

        try (BufferedReader reader = IOUtils.toBufferedReader(path)) {
            while (reader.ready()) {
                fileContent.add(reader.readLine());
            }
        }

        return fileContent;
    }

    /**
     * Writes a string in a specific file.
     *
     * @param path    path to file
     * @param content String to store in file
     * @throws IllegalArgumentException Is thrown if path is null.
     * @throws IOException              Is thrown if an error occurs while writing in file.
     */
    public static void writeToTextFile(String path, String content)
            throws IllegalArgumentException, IOException {
        ArgumentUtils.assertNotNull(path, "path");

        content = (content == null) ? "" : content;

        try (BufferedWriter writer = IOUtils.toBufferedWriter(path)) {
            writer.write(content);
        }
    }

    /**
     * Writes a list of strings in a specific file. Each list entry is one line in file.
     *
     * @param path    path to file
     * @param content list with Strings to store in file
     * @throws IllegalArgumentException Is thrown if path is null.
     * @throws IOException              Is thrown if an error occurs while writing in file.
     */
    public static void writeTextFile(String path, Collection<? extends String> content)
            throws IllegalArgumentException, IOException {
        ArgumentUtils.assertNotNull(path, "path");

        content = (content == null) ? Collections.EMPTY_LIST : content;

        try (BufferedWriter writer = IOUtils.toBufferedWriter(path)) {
            for (String line : content) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
}
