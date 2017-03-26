package org.panthercode.arctic.core.io;

import org.panthercode.arctic.core.event.impl.arguments.AbstractEventArgs;

import java.nio.file.Path;

/**
 * Created by architect on 28.02.17.
 */
public class WatcherEventArgs extends AbstractEventArgs {

    private final Path path;

    private final WatchEventKind kind;

    private final boolean recursive;

    public WatcherEventArgs(Path path, WatchEventKind kind, boolean recursive) {
        super();

        this.path = path;

        this.kind = kind;

        this.recursive = recursive;
    }

    public Path path() {
        return this.path;
    }

    public WatchEventKind kind() {
        return this.kind;
    }

    public boolean isRecursive() {
        return this.recursive;
    }
}
