package org.panthercode.arctic.core.io.watcher;

import org.panthercode.arctic.core.event.impl.arguments.AbstractEventArgs;

import java.nio.file.Path;

/**
 * Created by architect on 28.02.17.
 */
public class WatcherEventArgs extends AbstractEventArgs {

    /**
     *
     */
    private final Path path;

    /**
     *
     */
    private final WatchEventKind kind;

    /**
     *
     */
    private final boolean recursive;

    /**
     *
     * @param path
     * @param kind
     * @param recursive
     */
    public WatcherEventArgs(Path path, WatchEventKind kind, boolean recursive) {
        this.path = path;

        this.kind = kind;

        this.recursive = recursive;
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
    public WatchEventKind kind() {
        return this.kind;
    }

    /**
     *
     * @return
     */
    public boolean isRecursive() {
        return this.recursive;
    }
}
