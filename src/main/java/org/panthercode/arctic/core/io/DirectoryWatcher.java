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

import org.panthercode.arctic.core.event.Event;
import org.panthercode.arctic.core.event.EventBus;
import org.panthercode.arctic.core.event.EventHandler;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * Class to observe activities of directories in the filesystem.
 *
 * @author PantherCode
 * @since 1.0
 */
public class DirectoryWatcher implements Runnable {

    /**
     * map to store all observed directories
     */
    private final Map<Path, WatcherEntry> registeredPathsMap;

    /**
     * watch service of filesystem
     */
    private final WatchService service;

    /**
     *
     */
    private final Event<WatcherEventArgs> entryEvent;

    /**
     *
     */
    private final Event<WatcherEventArgs> entryCreateEvent;

    /**
     *
     */
    private final Event<WatcherEventArgs> entryModifyEvent;

    /**
     *
     */
    private final Event<WatcherEventArgs> entryDeleteEvent;

    /**
     *
     */
    private final Event<WatcherRegistryEventArgs> entryRegisteredEvent;

    /**
     *
     */
    private final Event<WatcherRegistryEventArgs> entryRemoveEvent;

    /**
     * Standard Constructor
     *
     * @throws IOException Is thrown if an error is occurred while creating <tt>DirectoryWatcher</tt> instance.
     */
    private DirectoryWatcher(final EventBus bus, final Path path, final boolean recursive, final EventHandler<WatcherEventArgs> handler)
            throws IOException {
        this.registeredPathsMap = new HashMap<>();

        this.service = FileSystems.getDefault().newWatchService();

        this.entryEvent = Event.register(bus);

        this.entryCreateEvent = Event.register(bus);

        this.entryModifyEvent = Event.register(bus);

        this.entryDeleteEvent = Event.register(bus);

        this.entryRegisteredEvent = Event.register(bus);

        this.entryRemoveEvent = Event.register(bus);

        this.register(path, recursive, handler);
    }

    /**
     * Creates a new instance of <tt>DirectoryWatcher</tt> class.
     *
     * @return Returns a new instance of <tt>DirectoryWatcher</tt> class.
     * @throws IOException Is thrown if an error occurred while creating new filesystem service.
     */
    public static DirectoryWatcher create(final EventBus bus, final Path path)
            throws IOException {
        return new DirectoryWatcher(bus, path, false, null);
    }

    public static DirectoryWatcher create(final EventBus bus, final Path path, final boolean recursive)
            throws IOException {
        return new DirectoryWatcher(bus, path, recursive, null);
    }

    public static DirectoryWatcher create(final EventBus bus, final Path path, final boolean recursive, final EventHandler<WatcherEventArgs> handler)
            throws IOException {
        return new DirectoryWatcher(bus, path, recursive, handler);
    }

    public Event<WatcherEventArgs> entryEvent() {
        return this.entryEvent;
    }

    public Event<WatcherEventArgs> entryCreateEvent() {
        return this.entryCreateEvent;
    }

    public Event<WatcherEventArgs> entryDeleteEvent() {
        return this.entryDeleteEvent;
    }

    public Event<WatcherEventArgs> entryModifyEvent() {
        return this.entryModifyEvent;
    }

    public Event<WatcherRegistryEventArgs> entryRegisteredEvent() {
        return this.entryRegisteredEvent;
    }

    public Event<WatcherRegistryEventArgs> entryRemovedEvent() {
        return this.entryRemoveEvent;
    }

    public synchronized boolean register(final Path path)
            throws IOException {
        return this.register(path, false);
    }

    public synchronized boolean register(final Path path, final boolean recursive)
            throws IOException {
        return this.register(path, recursive, null);
    }

    public synchronized boolean register(final Path path, final boolean recursive, final EventHandler<WatcherEventArgs> handler)
            throws IOException {
        if (path == null) {
            return false;
        }

        if (recursive) {
            Iterator<Path> iterator = Files.walk(path).iterator();

            if (iterator.hasNext()) {
                for (Path p = iterator.next(); iterator.hasNext(); p = iterator.next()) {
                    if (Files.isDirectory(p)) {
                        this.createWatcherEntry(path, recursive, handler);
                    }
                }
            }
        } else {
            this.createWatcherEntry(path, recursive, handler);
        }

        return true;
    }

    public boolean isRegistered(final Path path) {
        return this.registeredPathsMap.containsKey(path);
    }

    public synchronized boolean unregister(final Path path) {
        if (this.registeredPathsMap.containsKey(path)) {
            WatcherEntry oldEntry = this.registeredPathsMap.remove(path);

            this.entryRemoveEvent.raise(this, new WatcherRegistryEventArgs(oldEntry.watchKey, oldEntry.path(), oldEntry.isRecursive(), oldEntry.hasHandler()));

            return true;
        }

        return false;
    }

    public Path[] registeredPaths() {
        Path[] paths = new Path[this.registeredPathsMap.size()];

        return this.registeredPathsMap.keySet().toArray(paths);
    }

    public synchronized void run() {
        Map<Path, EventHandler<WatcherEventArgs>> newEntries = new HashMap<>();
        Path path;
        WatchEventKind kind;

        for (WatcherEntry entry : registeredPathsMap.values()) {
            for (WatchEvent event : entry.watchKey().pollEvents()) {
                kind = WatchEventKind.valueOf(event.kind());

                path = entry.path().resolve(event.context().toString());

                if (kind == WatchEventKind.CREATE && Files.isDirectory(path) && entry.isRecursive()) {
                    newEntries.put(path, entry.handler());
                }

                WatcherEventArgs e = new WatcherEventArgs(path, kind, entry.handler != null, entry.isRecursive());

                if (entry.hasHandler()) {
                    entry.handler().handle(this, e);
                } else {
                    switch (kind) {
                        case CREATE:
                            entryCreateEvent.raise(this, e);
                            break;
                        case DELETE:
                            entryDeleteEvent.raise(this, e);
                            break;
                        case MODIFY:
                            entryModifyEvent.raise(this, e);
                            break;
                        default:
                            break;
                    }

                    entryEvent.raise(this, e);
                }
            }

            entry.watchKey.reset();
        }

        try {
            if (!newEntries.isEmpty()) {
                for (Path newPath : newEntries.keySet()) {
                    createWatcherEntry(newPath, true, newEntries.get(newPath));
                }

                newEntries.clear();
            }
        } catch (IOException e) {
            throw new DirectoryWatcherException(e);
        }
    }

    private synchronized void createWatcherEntry(final Path path, final boolean recursive, final EventHandler<WatcherEventArgs> eventHandler)
            throws IOException {
        if (path != null) {
            WatchKey watchKey = path.register(this.service, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);

            WatcherEntry entry = new WatcherEntry(watchKey, path, recursive, eventHandler);

            this.registeredPathsMap.put(path, entry);

            this.entryRegisteredEvent.raise(this, new WatcherRegistryEventArgs(watchKey, path, recursive, entry.hasHandler()));
        }
    }

    /**
     * Inner class to store information about registered directories.
     */
    private class WatcherEntry {

        private WatchKey watchKey;

        /**
         * path of directory
         */
        private Path path;

        /**
         * actual event handler
         */
        private EventHandler<WatcherEventArgs> handler;

        /**
         * flag to observe also subdirectories
         */
        private boolean recursive;

        WatcherEntry(WatchKey watchKey, Path path, boolean recursive, EventHandler<WatcherEventArgs> handler) {
            this.watchKey = watchKey;
            this.path = path;
            this.handler = handler;
            this.recursive = recursive;
        }

        WatchKey watchKey() {
            return this.watchKey;
        }

        Path path() {
            return this.path;
        }

        EventHandler<WatcherEventArgs> handler() {
            return this.handler;
        }

        boolean hasHandler() {
            return this.handler != null;
        }

        boolean isRecursive() {
            return this.recursive;
        }
    }
}
