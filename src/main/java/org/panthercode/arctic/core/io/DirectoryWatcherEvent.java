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

import org.panthercode.arctic.core.helper.event.BasicEvent;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * Event class are raised by <tt>DirectoryWatcher/tt> if an registered directory changed.
 *
 * @author PantherCode
 * @see DirectoryWatcher
 * @since 1.0
 */
public class DirectoryWatcherEvent extends BasicEvent {

    /**
     * directory which raised the event
     */
    private Path source;

    /**
     * cause of event
     */
    private WatchEvent.Kind<?> kind;

    /**
     * Constructor
     *
     * @param source directory which raised the event
     * @param kind   cause of event
     */
    public DirectoryWatcherEvent(Path source, WatchEvent.Kind kind) {
        super();

        this.source = source;
        this.kind = kind;
    }

    /**
     * Returns the directory's path which raised the event.
     *
     * @return Returns the directory's path which raised the event.
     */
    public Path source() {
        return this.source;
    }

    /**
     * Returns the cause of the event.
     *
     * @return Returns the cause of the event.
     */
    public WatchEvent.Kind<?> kind() {
        return this.kind;
    }
}
