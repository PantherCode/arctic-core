package org.panthercode.arctic.core.io.watcher;

import java.nio.file.Path;
import java.nio.file.WatchKey;

/**
 * Created by architect on 26.03.17.
 */
public class DirectoryWatcherEntry {

    private final WatchKey watchKey;

    /**
     * path of directory
     */
    private final Path path;

    /**
     * flag to observe also subdirectories
     */
    private final boolean recursive;

    public DirectoryWatcherEntry(final WatchKey watchKey, final Path path, final boolean recursive) {
        this.watchKey = watchKey;
        this.path = path;
        this.recursive = recursive;
    }

    /**
     *
     * @return
     */
    public WatchKey watchKey() {
        return this.watchKey;
    }

    /**
     *
     * @return
     */
    public Path path() {
        return this.path;
    }

    /**
     *
     * @return
     */
    public boolean isRecursive() {
        return this.recursive;
    }
}
