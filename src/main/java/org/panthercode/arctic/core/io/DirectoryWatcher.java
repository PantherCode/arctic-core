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

    public static DirectoryWatcher create() throws IOException {
        return new DirectoryWatcher();
    }

    public static DirectoryWatcher create(long delayTimeInMillis) throws IOException {
        return new DirectoryWatcher(delayTimeInMillis);
    }

    public static DirectoryWatcher create(long duration, TimeUnit unit) throws IOException {
        ArgumentUtils.assertNotNull(unit, "time unit");

        return new DirectoryWatcher(unit.toMillis(duration));
    }

    public WatchKey register(Directory directory, Handler<DirectoryWatcherEvent> handler) throws IOException {
        return this.register(directory, handler, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
    }

    public WatchKey register(Directory directory, Handler<DirectoryWatcherEvent> handler, WatchEvent.Kind<?>... kinds) throws IOException {
        return this.register(directory.toPath(), handler, false, kinds);
    }

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

    public synchronized void stop() {
        this.isRunning = false;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public long delayTime() {
        return this.delayTimeInMillis;
    }

    public long delayTime(TimeUnit other) {
        return other.convert(this.delayTimeInMillis, TimeUnit.MILLISECONDS);
    }

    private synchronized WatchKey register(Path path, Handler<DirectoryWatcherEvent> handler, boolean observe, WatchEvent.Kind<?>... kinds) throws IOException {
        WatchKey watchKey = path.register(this.service, kinds);

        DirectoryWatcherEntry entry = new DirectoryWatcherEntry(path, handler, observe);

        this.entries.put(watchKey, entry);

        return watchKey;
    }

    private class DirectoryWatcherEntry {

        private Path source;

        private Handler<DirectoryWatcherEvent> handler;

        private boolean observeSubdirectories;

        DirectoryWatcherEntry(Path source, Handler<DirectoryWatcherEvent> handler, boolean observeSubdirectories) {
            this.source = source;
            this.handler = handler;
            this.observeSubdirectories = observeSubdirectories;
        }

        Path source() {
            return this.source;
        }

        Handler<DirectoryWatcherEvent> handler() {
            return this.handler;
        }

        boolean observeSubdirectories() {
            return this.observeSubdirectories;
        }
    }
}
