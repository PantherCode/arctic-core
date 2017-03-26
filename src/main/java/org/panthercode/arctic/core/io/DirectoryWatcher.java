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

import org.panthercode.arctic.core.event.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
    private final Map<Path, DirectoryWatcherEntry> registeredPathsMap;

    /**
     * watch service of filesystem
     */
    private final WatchService service;

    /**
     *
     */
    private Handler<EventMessage<WatcherEventArgs>> messageHandler;

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
     * Standard Constructor
     *
     * @throws IOException Is thrown if an error is occurred while creating <tt>DirectoryWatcher</tt> instance.
     */
    private DirectoryWatcher(final EventFactory eventFactory, Handler<EventMessage<WatcherEventArgs>> messageHandler)
            throws IOException {
        this.service = FileSystems.getDefault().newWatchService();

        this.registeredPathsMap = new HashMap<>();

        this.messageHandler = messageHandler;

        this.entryEvent = eventFactory.create();

        this.entryCreateEvent = eventFactory.create();

        this.entryModifyEvent = eventFactory.create();

        this.entryDeleteEvent = eventFactory.create();
    }

    /**
     * Creates a new instance of <tt>DirectoryWatcher</tt> class.
     *
     * @return Returns a new instance of <tt>DirectoryWatcher</tt> class.
     * @throws IOException Is thrown if an error occurred while creating new filesystem service.
     */
    public static DirectoryWatcher create(final EventFactory eventFactory)
            throws IOException {
        return DirectoryWatcher.create(eventFactory, null);
    }

    public static DirectoryWatcher create(final EventFactory eventFactory, Handler<EventMessage<WatcherEventArgs>> messageHandler)
            throws IOException {
        return new DirectoryWatcher(eventFactory, messageHandler);
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

    public synchronized boolean register(final Path path)
            throws IOException {
        return this.register(path, false);
    }

    public synchronized boolean register(final Path path, final boolean recursive)
            throws IOException {
        if (path == null) {
            return false;
        }

        if (recursive) {
            Iterator<Path> iterator = Files.walk(path).iterator();

            if (iterator.hasNext()) {
                for (Path p = iterator.next(); iterator.hasNext(); p = iterator.next()) {
                    if (Files.isDirectory(p)) {
                        this.createWatcherEntry(path, recursive);
                    }
                }
            }
        } else {
            this.createWatcherEntry(path, recursive);
        }

        return true;
    }

    public boolean isRegistered(final Path path) {
        return this.registeredPathsMap.containsKey(path);
    }

    public synchronized boolean unregister(final Path path) {
        if (this.registeredPathsMap.containsKey(path)) {
            DirectoryWatcherEntry oldEntry = this.registeredPathsMap.remove(path);

            return true;
        }

        return false;
    }

    public Set<Path> pathSet() {
        return this.registeredPathsMap.keySet();
    }

    public Map<Path, DirectoryWatcherEntry> entries() {
        return this.registeredPathsMap;
    }

    public synchronized void run() {
        Map<Path, EventHandler<WatcherEventArgs>> newEntries = new HashMap<>();
        Path path;
        WatchEventKind kind;

        for (DirectoryWatcherEntry entry : registeredPathsMap.values()) {
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

    private synchronized void createWatcherEntry(final Path path, final boolean recursive)
            throws IOException {
        if (path != null) {
            WatchKey watchKey = path.register(this.service, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);

            DirectoryWatcherEntry entry = new DirectoryWatcherEntry(watchKey, path, recursive);

            this.registeredPathsMap.put(path, entry);
        }
    }

    /**
     * Inner class to store information about registered directories.
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

        public WatchKey watchKey() {
            return this.watchKey;
        }

        public Path path() {
            return this.path;
        }

        public boolean isRecursive() {
            return this.recursive;
        }
    }
}
