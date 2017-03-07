package org.panthercode.arctic.core.io;

import org.panthercode.arctic.core.event.EventArgs;

import java.nio.file.Path;
import java.nio.file.WatchKey;

/**
 * Created by architect on 28.02.17.
 */
public class WatcherRegistryEventArgs extends EventArgs {

    private final WatchKey watchKey;

    private final Path path;

    private final boolean recursive;

    private final boolean hasHandler;

    public WatcherRegistryEventArgs(WatchKey watchKey, Path path, boolean recursive, boolean hasHandler) {
        this.watchKey = watchKey;

        this.path = path;

        this.recursive = recursive;

        this.hasHandler = hasHandler;
    }

    public WatchKey watchKey() {
        return this.watchKey;
    }

    public Path path() {
        return this.path;
    }

    public boolean isRecursive() {
        return this.recursive;
    }

    public boolean hasHandler() {
        return this.hasHandler;
    }
}
