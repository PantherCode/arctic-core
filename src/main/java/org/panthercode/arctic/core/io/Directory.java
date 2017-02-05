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
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        ArgumentUtils.checkNotNull(path, "path");

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

    public static Directory[] create(Path path1, Path path2, FileAttribute<?>... attributes) throws IOException {
        return create(new Path[]{path1, path2}, attributes);
    }

    public static Directory[] create(Path path1, Path path2, Path path3, FileAttribute<?>... attributes) throws IOException {
        return create(new Path[]{path1, path2, path3}, attributes);
    }

    public static Directory[] create(Path[] path, FileAttribute<?>... attributes) throws IOException {
        Directory[] array = new Directory[path.length];

        for (int i = 0; i < path.length; i++) {
            array[i] = create(path[i], attributes);
        }

        return array;
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

    public static Directory[] open(Path path1, Path path2) throws FileNotFoundException {
        return open(new Path[]{path1, path2});
    }

    public static Directory[] open(Path path1, Path path2, Path path3, Path... other) throws FileNotFoundException {
        Path[] paths = new Path[3 + other.length];

        paths[0] = path1;
        paths[1] = path2;
        paths[2] = path3;

        System.arraycopy(other, 1, paths, 3, other.length);

        return open(paths);
    }

    public static Directory[] open(Path[] paths) throws FileNotFoundException {
        Directory[] array = new Directory[paths.length];

        for (int i = 0; i < paths.length; i++) {
            array[i] = Directory.open(paths[i]);
        }

        return array;
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

    public static Directory[] openOrCreate(Path path1, Path path2, FileAttribute<?>... attributes) throws IOException {
        return openOrCreate(new Path[]{path1, path2}, attributes);
    }

    public static Directory[] openOrCreate(Path path1, Path path2, Path path3, FileAttribute<?>... attributes) throws IOException {
        return openOrCreate(new Path[]{path1, path2, path3}, attributes);
    }

    public static Directory[] openOrCreate(Path[] path, FileAttribute<?>... attributes) throws IOException {
        Directory[] array = new Directory[path.length];

        for (int i = 0; i < array.length; i++) {
            array[i] = openOrCreate(path[i], attributes);
        }

        return array;
    }

    public static <T extends Directory> T openAs(Class<T> clazz, Path path) throws FileNotFoundException {
        return clazz.cast(open(path));
    }

    public String name() {
        return this.path.getFileName().toString();
    }

    /**
     * Returns the path of a sub element with the given name.
     *
     * @param name name of file or directory
     * @return Returns the path of the sub element.
     */
    public Path get(String name) {
        return this.get(Paths.get(name));
    }

    public Path get(String name, String... others) {
        return this.get(Paths.get(name, others));
    }

    public Path get(Path path) {
        return this.path.resolve(path);
    }

    public boolean hasChild(String name) {
        return this.hasChild(Paths.get(name));
    }

    public boolean hasChild(String name, String... others) {
        return this.hasChild(Paths.get(name, others));
    }

    public boolean hasChild(Path path) {
        return Files.exists(this.path.resolve(path));
    }

    public boolean isEmpty() throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.path)) {
            return !stream.iterator().hasNext();
        }
    }

    public Path[] children() throws IOException {
        List<Path> result = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.path)) {
            for (Path file : stream) {
                result.add(file);
            }
        }

        Path[] array = new Path[result.size()];

        return result.toArray(array);
    }

    public Path[] files() throws IOException {
        List<Path> result = new ArrayList<>();

        DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
            @Override
            public boolean accept(Path entry) throws IOException {
                return !Files.isDirectory(path);
            }
        };

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.path, filter)) {
            for (Path path : stream) {
                result.add(path);
            }
        }

        Path[] array = new Path[result.size()];

        return result.toArray(array);
    }

    public Directory[] directories() throws IOException {
        List<Directory> result = new ArrayList<>();

        DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
            @Override
            public boolean accept(Path entry) throws IOException {
                return Files.isDirectory(path);
            }
        };

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.path, filter)) {
            for (Path path : stream) {
                result.add(Directory.open(path));
            }
        }

        Directory[] array = new Directory[result.size()];

        return result.toArray(array);
    }

    public void clear() throws IOException {
        //TODO: implement
    }

    public void delete() throws IOException {
        this.clear();

        Files.delete(this.path);
    }

    public void walkFileTree(FileVisitor<Path> visitor) throws IOException {
        Files.walkFileTree(this.path, visitor);
    }

    public void walkFileTree(Set<FileVisitOption> options, int maxDepth, FileVisitor<Path> visitor) throws IOException {
        Files.walkFileTree(this.path, options, maxDepth, visitor);
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

    @Override
    public String toString() {
        return this.path.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
