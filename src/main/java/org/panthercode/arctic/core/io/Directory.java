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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;

/**
 * Represents a directory entry in filesystem
 *
 * @author PantherCode
 * @see DirectoryWatcher
 * @since 1.0
 */
public class Directory {

    /**
     * actual directory path
     */
    private Path path;

    /**
     * Constructor
     *
     * @param path actual directory path
     * @throws FileNotFoundException Is thrown if the directory with given path doesn't exist.
     */
    private Directory(Path path)
            throws FileNotFoundException {
        ArgumentUtils.assertNotNull(path, "path");

        if (!Files.exists(path)) {
            throw new FileNotFoundException("The directory with given path doesn't exists.");
        }

        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("The given path is not a directory");
        }

        this.path = path;
    }

    /**
     * Creates a new directory in filesystem.
     *
     * @param path       directory path
     * @param attributes file attributes
     * @return Returns a new instance of <tt>Directory</tt> class.
     * @throws IOException Is thrown if an error is occurred while creating directory.
     */
    public static Directory create(Path path, FileAttribute<?>... attributes) throws IOException {
        return new Directory(Files.createDirectory(path, attributes));
    }

    /**
     * Opens an existing directory.
     *
     * @param path directory path
     * @return Returns a new instance of <tt>Directory</tt> class.
     * @throws FileNotFoundException Is thrown if the element with given path doesn't exists.
     */
    public static Directory open(Path path)
            throws FileNotFoundException {
        return new Directory(path);
    }

    /**
     * If the director with given path exits it will be opened, otherwise created.
     *
     * @param path       directory path
     * @param attributes file attributes
     * @return Returns a new instance of <tt>Directory</tt> class.
     * @throws IOException Is thrown if an error is occurred while creating or opening directory.
     */
    public static Directory openOrCreate(Path path, FileAttribute<?>... attributes) throws IOException {
        if (!Files.exists(path)) {
            return Directory.create(path, attributes);
        }

        return Directory.open(path);
    }

    /**
     * Returns the path of a sub element with the given name.
     *
     * @param name name of file or directory
     * @return Returns the path of the sub element.
     */
    public Path get(String name) {
        return Paths.get(this.toUri().toString(), name);
    }

    /**
     * Returns the actual path of directory as <tt>Path</tt> object.
     *
     * @return Returns the actual path of directory as <tt>Path</tt> object.
     */
    public Path toPath() {
        return Paths.get(this.path.toUri());
    }

    /**
     * Returns the actual path of directory as <tt>URI</tt>.
     *
     * @return Returns the actual path of directory as <tt>URI</tt>.
     */
    public URI toUri() {
        return this.path.toUri();
    }
}
