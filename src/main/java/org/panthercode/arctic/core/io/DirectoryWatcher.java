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
import org.panthercode.arctic.core.helper.event.Handler;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * Class to observe activities of directories in the filesystem.
 *
 * @author PantherCode
 * @see DirectoryWatcherEvent
 * @since 1.0
 */
public class DirectoryWatcher {

    /**
     * flag to indicate whether the watcher is running or not
     */
    private boolean isRunning = false;

    /**
     * delay time  after each loop run
     */
    private long delayTimeInMillis = 1000L;

    /**
     * map to store all observed directories
     */
    private Map<WatchKey, DirectoryWatcherEntry> entries;

    /**
     * watch service of filesystem
     */
    private WatchService service = null;

    /**
     * Standard Constructor
     *
     * @throws IOException Is thrown if an error is occurred while creating <tt>DirectoryWatcher</tt> instance.
     */
    private DirectoryWatcher() throws IOException {
        this(1000L);
    }

    /**
     * Constructor
     *
     * @param delayTimeInMillis delay time in milliseconds
     * @throws IOException Is thrown if an error is occurred while creating <tt>DirectoryWatcher</tt> instance.
     */
    private DirectoryWatcher(long delayTimeInMillis) throws IOException {
        ArgumentUtils.assertGreaterOrEqualsZero(delayTimeInMillis, "delay time");

        this.delayTimeInMillis = delayTimeInMillis;

        this.entries = new HashMap<>();

        this.service = FileSystems.getDefault().newWatchService();
    }

    /**
     * Creates a new instance of <tt>DirectoryWatcher</tt> class.
     *
     * @return Returns a new instance of <tt>DirectoryWatcher</tt> class.
     * @throws IOException Is thrown if an error occurred while creating new filesystem service.
     */
    public static DirectoryWatcher create() throws IOException {
        return new DirectoryWatcher();
    }

    /**
     * Creates a new instance of <tt>DirectoryWatcher</tt> class.
     *
     * @param delayTimeInMillis delay time after each look up the event loop
     * @return Returns a new instance of <tt>DirectoryWatcher</tt> class.
     * @throws IOException Is thrown if an error occurred while creating new filesystem service.
     */
    public static DirectoryWatcher create(long delayTimeInMillis) throws IOException {
        return new DirectoryWatcher(delayTimeInMillis);
    }

    /**
     * Creates a new instance of <tt>DirectoryWatcher</tt> class.
     *
     * @param duration duration of delay time after each look up the event loop
     * @param unit     time unit of duration
     * @return Returns a new instance of <tt>DirectoryWatcher</tt> class.
     * @throws IOException Is thrown if an error is occurred while creating new filesystem service.
     */
    public static DirectoryWatcher create(long duration, TimeUnit unit) throws IOException {
        ArgumentUtils.assertNotNull(unit, "time unit");

        return new DirectoryWatcher(unit.toMillis(duration));
    }

    /**
     * Register a new directory for observing.
     *
     * @param directory directory to observe
     * @param handler   event handler
     * @return Returns the corresponding <tt>WatchKey</tt> of registered directory.
     * @throws IOException Is thrown if an error is occurred while directory's path is registered.
     */
    public WatchKey register(Directory directory, Handler<DirectoryWatcherEvent> handler) throws IOException {
        return this.register(directory, handler, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
    }

    /**
     * Register a new directory for observing.
     *
     * @param directory directory to observe
     * @param handler   event handler
     * @param kinds     triggers for raising events
     * @return Returns the corresponding <tt>WatchKey</tt> of registered directory.
     * @throws IOException Is thrown if an error is occurred while directory's path is registered.
     */
    public WatchKey register(Directory directory, Handler<DirectoryWatcherEvent> handler, WatchEvent.Kind<?>... kinds) throws IOException {
        return this.register(directory.toPath(), handler, false, kinds);
    }

    /**
     * Register a directory and its subdirectories for observing.
     *
     * @param directory directory to observe
     * @param handler   event handler
     * @return Returns the corresponding <tt>WatchKey</tt>s of directory and its subdirectories.
     * @throws IOException Is thrown if an error is occurred while a directory's path is registered.
     */
    public List<WatchKey> registerTree(Directory directory, Handler<DirectoryWatcherEvent> handler) throws IOException {
        WatchService service = FileSystems.getDefault().newWatchService();

        Iterator<Path> iterator = Files.walk(directory.toPath()).iterator();

        List<WatchKey> watchKeyList = new ArrayList<>();

        if (iterator.hasNext()) {
            for (Path p = iterator.next(); iterator.hasNext(); p = iterator.next()) {
                if (Files.isDirectory(p)) {
                    watchKeyList.add(this.register(directory.toPath(), handler, true, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY));
                }
            }
        }

        return watchKeyList;
    }

    /**
     * Starts the observation of all registered directories. This function is <tt>synchronized</tt>.
     */
    public synchronized void start() {
        if (!isRunning) {
            this.isRunning = true;

            new Thread(() -> {
                Path source;
                DirectoryWatcherEntry actualDirectoryWatchEntry;
                Map<Path, Handler<DirectoryWatcherEvent>> newEntryMap = new HashMap<>();

                while (isRunning) {
                    for (WatchKey key : entries.keySet()) {
                        for (WatchEvent event : key.pollEvents()) {
                            actualDirectoryWatchEntry = entries.get(key);

                            source = actualDirectoryWatchEntry.source().resolve(event.context().toString());

                            if (event.kind() == ENTRY_CREATE &&
                                    Files.isDirectory(source) &&
                                    actualDirectoryWatchEntry.observeSubdirectories()) {
                                newEntryMap.put(Paths.get(source.toUri()), actualDirectoryWatchEntry.handler);
                            }

                            actualDirectoryWatchEntry.handler().handle(new DirectoryWatcherEvent(source, event.kind()));
                        }

                        key.reset();
                    }

                    try {
                        if (!newEntryMap.isEmpty()) {
                            for (Path path : newEntryMap.keySet()) {
                                register(path, newEntryMap.get(path), true, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
                            }

                            newEntryMap.clear();
                        }

                        Thread.sleep(delayTimeInMillis);
                    } catch (InterruptedException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }
    }

    /**
     * Stops the observation of registered directories. This function is <tt>synchronized</tt>.
     */
    public synchronized void stop() {
        this.isRunning = false;
    }

    /**
     * Returns a flag that indicates whether the watch is observing registered directories or not.
     *
     * @return Returns <tt>true</tt> if the watcher is observing registered directories; Otherwise <tt>false</tt>.
     */
    public boolean isRunning() {
        return this.isRunning;
    }

    /**
     * Returns the actual delay time of the watcher.
     *
     * @return Returns the actual delay time of the watcher.
     */
    public long delayTime() {
        return this.delayTimeInMillis;
    }

    /**
     * Returns the actual delay time of the watcher.
     *
     * @param other transform delay time to given time units
     * @return Returns the actual delay time of the watcher.
     */
    public long delayTime(TimeUnit other) {
        return other.convert(this.delayTimeInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Register a directory for observation.
     *
     * @param path    path of directory
     * @param handler event handler
     * @param observe also observe  subdirectories
     * @param kinds   triggers for raising events
     * @return Returns the corresponding <tt>WatchKey</tt> of registered path.
     * @throws IOException Is thrown if an error is occurred while path is registered.
     */
    private synchronized WatchKey register(Path path, Handler<DirectoryWatcherEvent> handler, boolean observe, WatchEvent.Kind<?>... kinds) throws IOException {
        WatchKey watchKey = path.register(this.service, kinds);

        DirectoryWatcherEntry entry = new DirectoryWatcherEntry(path, handler, observe);

        this.entries.put(watchKey, entry);

        return watchKey;
    }

    /**
     * Inner class to store information about registered directories.
     */
    private class DirectoryWatcherEntry {

        /**
         * path of directory
         */
        private Path source;

        /**
         * actual event handler
         */
        private Handler<DirectoryWatcherEvent> handler;

        /**
         * flag to observe also subdirectories
         */
        private boolean observeSubdirectories;

        /**
         * Constructor
         *
         * @param source                path of directory
         * @param handler               event handler
         * @param observeSubdirectories flag to observe subdirectories
         */
        DirectoryWatcherEntry(Path source, Handler<DirectoryWatcherEvent> handler, boolean observeSubdirectories) {
            this.source = source;
            this.handler = handler;
            this.observeSubdirectories = observeSubdirectories;
        }

        /**
         * Returns the path of registered directory.
         *
         * @return Returns the path of registered directory.
         */
        Path source() {
            return this.source;
        }

        /**
         * Returns the event handler of registered directory.
         *
         * @return Returns the event handler of registered directory.
         */
        Handler<DirectoryWatcherEvent> handler() {
            return this.handler;
        }

        /**
         * Returns a flag that indicates whether the directory's sub elements are observed or not.
         *
         * @return Returns <tt>true</tt> if the sub elements are observed; Otherwise <tt>false</tt>.
         */
        boolean observeSubdirectories() {
            return this.observeSubdirectories;
        }
    }
}
