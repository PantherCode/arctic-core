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
package org.panthercode.arctic.core.io.watcher;

import org.panthercode.arctic.core.event.Event;
import org.panthercode.arctic.core.event.EventFactory;
import org.panthercode.arctic.core.event.Handler;
import org.panthercode.arctic.core.runtime.Message;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

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
    private Handler<Message<WatcherEventArgs>> messageHandler;

    /**
     *
     */
    private final Event<WatcherEventArgs> createEvent;

    /**
     *
     */
    private final Event<WatcherEventArgs> modifyEvent;

    /**
     *
     */
    private final Event<WatcherEventArgs> deleteEvent;

    /**
     * Standard Constructor
     *
     * @throws IOException Is thrown if an error is occurred while creating <tt>DirectoryWatcher</tt> instance.
     */
    private DirectoryWatcher(final EventFactory eventFactory, Handler<Message<WatcherEventArgs>> messageHandler)
            throws IOException {
        this.service = FileSystems.getDefault().newWatchService();

        this.registeredPathsMap = new HashMap<>();

        this.messageHandler = messageHandler;

        this.createEvent = eventFactory.create();

        this.modifyEvent = eventFactory.create();

        this.deleteEvent = eventFactory.create();
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

    /**
     * @param eventFactory
     * @param messageHandler
     * @return
     * @throws IOException
     */
    public static DirectoryWatcher create(final EventFactory eventFactory, Handler<Message<WatcherEventArgs>> messageHandler)
            throws IOException {
        return new DirectoryWatcher(eventFactory, messageHandler);
    }

    /**
     * @return
     */
    public Event<WatcherEventArgs> createEvent() {
        return this.createEvent;
    }

    /**
     * @return
     */
    public Event<WatcherEventArgs> deleteEvent() {
        return this.deleteEvent;
    }

    /**
     * @return
     */
    public Event<WatcherEventArgs> modifyEvent() {
        return this.modifyEvent;
    }

    /**
     * @param path
     * @return
     * @throws IOException
     */
    public synchronized boolean add(final Path path)
            throws IOException {
        if (path == null) {
            return false;
        }

        if (!this.registeredPathsMap.containsKey(path)) {
            this.createWatcherEntry(path, false);

            return true;
        }

        return false;
    }

    /**
     * @param path
     * @return
     * @throws IOException
     */
    public synchronized boolean addRecursively(final Path path)
            throws IOException {
        if (path == null) {
            return false;
        }

        Iterator<Path> iterator = Files.walk(path).iterator();

        if (iterator.hasNext()) {
            for (Path p = iterator.next(); iterator.hasNext(); p = iterator.next()) {
                if (Files.isDirectory(p) && !this.registeredPathsMap.containsKey(p)) {
                    this.createWatcherEntry(path, true);
                }
            }
        }

        return true;
    }

    /**
     * @param path
     * @return
     */
    public boolean contains(final Path path) {
        return this.registeredPathsMap.containsKey(path);
    }

    /**
     * @param path
     * @return
     */
    public synchronized boolean remove(final Path path) {
        if (this.registeredPathsMap.containsKey(path)) {
            DirectoryWatcherEntry oldEntry = this.registeredPathsMap.remove(path);

            return true;
        }

        return false;
    }

    /**
     * @return
     */
    public Set<Path> pathSet() {
        return this.registeredPathsMap.keySet();
    }

    /**
     * @return
     */
    public Map<Path, DirectoryWatcherEntry> paths() {
        return this.registeredPathsMap;
    }

    /**
     *
     */
    public synchronized void run() {
        Set<Path> newElements = new HashSet<>();
        Path path;
        WatchEventKind kind;

        for (DirectoryWatcherEntry entry : registeredPathsMap.values()) {
            for (WatchEvent event : entry.watchKey().pollEvents()) {
                kind = WatchEventKind.valueOf(event.kind());

                path = entry.path().resolve(event.context().toString());

                if (kind == WatchEventKind.CREATE && Files.isDirectory(path) && entry.isRecursive()) {
                    newElements.add(path);
                }

                WatcherEventArgs e = new WatcherEventArgs(path, kind, entry.isRecursive());

                switch (kind) {
                    case CREATE:
                        createEvent.send(this, e, this.messageHandler);
                        break;
                    case DELETE:
                        deleteEvent.send(this, e, this.messageHandler);
                        break;
                    case MODIFY:
                        modifyEvent.send(this, e, this.messageHandler);
                        break;
                    default:
                        break;
                }
            }

            entry.watchKey().reset();
        }

        try {
            if (!newElements.isEmpty()) {
                for (Path newPath : newElements) {
                    createWatcherEntry(newPath, true);
                }

                newElements.clear();
            }
        } catch (IOException e) {
            throw new DirectoryWatcherException(e);
        }
    }

    /**
     * @param path
     * @param recursive
     * @throws IOException
     */
    private synchronized void createWatcherEntry(final Path path, final boolean recursive)
            throws IOException {
        if (path != null) {
            WatchKey watchKey = path.register(this.service, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);

            DirectoryWatcherEntry entry = new DirectoryWatcherEntry(watchKey, path, recursive);

            this.registeredPathsMap.put(path, entry);
        }
    }
}
