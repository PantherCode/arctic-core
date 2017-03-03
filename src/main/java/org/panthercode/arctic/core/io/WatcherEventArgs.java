package org.panthercode.arctic.core.io;

import org.panthercode.arctic.core.event.EventArgs;

import java.nio.file.Path;

/**
 * Created by architect on 28.02.17.
 */
public class WatcherEventArgs extends EventArgs {

    private final Path path;

    private final WatchEventKind kind;

    private final boolean hasEventHandler;

    private final boolean recursive;

    public WatcherEventArgs(Path path, WatchEventKind kind, boolean hasEventHandler, boolean recursive) {
        this.path = path;

        this.kind = kind;

        this.hasEventHandler = hasEventHandler;

        this.recursive = recursive;
    }

    public Path path() {
        return this.path;
    }

    public WatchEventKind kind() {
        return this.kind;
    }

    public boolean hasEventHandler() {
        return this.hasEventHandler;
    }

    public boolean isRecursive() {
        return this.recursive;
    }
}
