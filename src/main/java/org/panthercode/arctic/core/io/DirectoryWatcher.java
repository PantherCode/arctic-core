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
import org.panthercode.arctic.core.event.Event;
import org.panthercode.arctic.core.event.EventHandler;
import org.quartz.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardWatchEventKinds.*;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * Class to observe activities of directories in the filesystem.
 *
 * @author PantherCode
 * @since 1.0
 */
public class DirectoryWatcher implements Runnable {

    /**
     * flag to indicate whether the watcher is running or not
     */
    private boolean isRunning = false;

    /**
     * delay time  afterRun each loop run
     */
    private long delayTimeInMillis = 1000L;

    /**
     * map to store all observed directories
     */
    private Map<Path, WatcherEntry> registeredPathsMap;

    /**
     * watch service of filesystem
     */
    private WatchService service = null;

    private Event<WatcherEventArgs> entryEvent = null;

    private Event<WatcherEventArgs> entryCreateEvent = null;

    private Event<WatcherEventArgs> entryModifyEvent = null;

    private Event<WatcherEventArgs> entryDeleteEvent = null;

    private Event<WatcherRegistryEventArgs> entryRegistryEvent = null;

    private Event<WatcherRegistryEventArgs> entryRemoveEvent = null;

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
        ArgumentUtils.checkGreaterOrEqualsZero(delayTimeInMillis, "delay time");

        this.delayTimeInMillis = delayTimeInMillis;

        this.registeredPathsMap = new HashMap<>();

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
     * @param delayTimeInMillis delay time afterRun each look up the event loop
     * @return Returns a new instance of <tt>DirectoryWatcher</tt> class.
     * @throws IOException Is thrown if an error occurred while creating new filesystem service.
     */
    public static DirectoryWatcher create(long delayTimeInMillis) throws IOException {
        return new DirectoryWatcher(delayTimeInMillis);
    }

    /**
     * Creates a new instance of <tt>DirectoryWatcher</tt> class.
     *
     * @param duration duration of delay time afterRun each look up the event loop
     * @param unit     time unit of duration
     * @return Returns a new instance of <tt>DirectoryWatcher</tt> class.
     * @throws IOException Is thrown if an error is occurred while creating new filesystem service.
     */
    public static DirectoryWatcher create(long duration, TimeUnit unit) throws IOException {
        ArgumentUtils.checkNotNull(unit, "time unit");

        return new DirectoryWatcher(unit.toMillis(duration));
    }

    public synchronized boolean register(Directory directory)
            throws IOException {
        return directory != null && this.register(directory.toPath());
    }

    public synchronized boolean register(Directory directory, boolean recursive)
            throws IOException {
        return directory != null && this.register(directory.toPath(), recursive);
    }

    public synchronized boolean register(Directory directory, boolean recursive, EventHandler<WatcherEventArgs> eventHandler)
            throws IOException {
        return directory != null && this.register(directory.toPath(), recursive, eventHandler);
    }

    public synchronized boolean register(Path path) throws IOException {
        return this.register(path, false);
    }

    public synchronized boolean register(Path path, boolean recursive) throws IOException {
        return this.register(path, recursive, null);
    }

    public synchronized boolean register(Path path, boolean recursive, EventHandler<WatcherEventArgs> eventHandler) throws IOException {
        if (path == null) {
            return false;
        }

        if (recursive) {
            Iterator<Path> iterator = Files.walk(path).iterator();

            if (iterator.hasNext()) {
                for (Path p = iterator.next(); iterator.hasNext(); p = iterator.next()) {
                    if (Files.isDirectory(p)) {
                        this.createWatcherEntry(path, recursive, eventHandler);
                    }
                }
            }
        } else {
            this.createWatcherEntry(path, recursive, eventHandler);
        }

        return true;
    }

    public boolean isRegistered(Path path) {
        return this.registeredPathsMap.containsKey(path);
    }

    public synchronized boolean remove(Path path) {
        if (this.registeredPathsMap.containsKey(path)) {
            WatcherEntry oldEntry = this.registeredPathsMap.remove(path);

            this.entryRemoveEvent.raise(this, new WatcherRegistryEventArgs(oldEntry.watchKey, oldEntry.path(), oldEntry.isRecursive(), oldEntry.hasEventHandler()));

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
                    newEntries.put(path, entry.eventHandler());
                }

                WatcherEventArgs e = new WatcherEventArgs(path, kind, entry.eventHandler != null, entry.isRecursive());

                if (entry.hasEventHandler()) {
                    entry.eventHandler().handle(this, e);
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

    /**
     * Starts the observation of all registered directories. This function is <tt>synchronized</tt>.
     */
    public synchronized void start() {
        if (!isRunning) {
            this.isRunning = true;

            try {
                SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();

                Scheduler sched = schedFact.getScheduler();

                sched.start();

                //JobDataMap map = RunnableJob.addToJobDataMap(this);

                //JobDetail job = JobBuilder.newJob(RunnableJob.class).setJobData(map).build();

                //Trigger trigger = TriggerBuilder.newTrigger().startNow().forJob(job).withSchedule(simpleSchedule()
                //        .withIntervalInSeconds(10)
                //        .repeatForever()).build();

                //sched.scheduleJob(job, trigger);

            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Stops the observation of registered directories. This function is <tt>synchronized</tt>.
     */
    public synchronized void stop() {
        if (this.isRunning) {
            this.isRunning = false;
        }
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

    public Event<WatcherEventArgs> entryEvent() {
        return this.entryEvent;
    }

    public Event<WatcherEventArgs> entryCreateEvent() {
        return this.entryCreateEvent;
    }

    public Event<WatcherEventArgs> entryDeleteEvent() {
        return this.entryDeleteEvent;
    }

    public Event<WatcherEventArgs> getEntryModifyEvent() {
        return this.entryModifyEvent;
    }

    public Event<WatcherRegistryEventArgs> entryRegistryEvent() {
        return this.entryRegistryEvent;
    }

    public Event<WatcherRegistryEventArgs> entryRemoveEvent() {
        return this.entryRemoveEvent;
    }

    private synchronized void createWatcherEntry(Path path, boolean recursive, EventHandler<WatcherEventArgs> eventHandler)
            throws IOException {
        if (path != null) {
            WatchKey watchKey = path.register(this.service, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);

            WatcherEntry entry = new WatcherEntry(watchKey, path, recursive, eventHandler);

            this.registeredPathsMap.put(path, entry);

            this.entryRegistryEvent.raise(this, new WatcherRegistryEventArgs(watchKey, path, recursive, entry.hasEventHandler()));
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
        private EventHandler<WatcherEventArgs> eventHandler;

        /**
         * flag to observe also subdirectories
         */
        private boolean recursive;

        WatcherEntry(WatchKey watchKey, Path path, boolean recursive, EventHandler<WatcherEventArgs> eventHandler) {
            this.watchKey = watchKey;
            this.path = path;
            this.eventHandler = eventHandler;
            this.recursive = recursive;
        }

        WatchKey watchKey() {
            return this.watchKey;
        }

        Path path() {
            return this.path;
        }

        EventHandler<WatcherEventArgs> eventHandler() {
            return this.eventHandler;
        }

        boolean hasEventHandler() {
            return this.eventHandler != null;
        }

        boolean isRecursive() {
            return this.recursive;
        }
    }
}
