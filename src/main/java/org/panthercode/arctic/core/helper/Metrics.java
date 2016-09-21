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
package org.panthercode.arctic.core.helper;

import org.panthercode.arctic.core.settings.context.Context;

import java.time.LocalDateTime;
import java.util.Map;

/**
 *
 */
public class Metrics extends Context {

    /**
     *
     */
    private long id = 0;

    /**
     *
     */
    private LocalDateTime timestamp = LocalDateTime.now();

    /**
     *
     */
    private String description = "";

    /**
     *
     */
    public Metrics(long id) {
        this(id, "");
    }

    /**
     *
     * @param id
     * @param description
     */
    public Metrics(long id, String description) {
        this(id, description, 11);
    }

    /**
     * @param capacity
     */
    public Metrics(long id, String description, int capacity) {
        super(capacity);

        this.setId(id);
        this.setDescription(description);
    }

    /**
     * @param map
     */
    public Metrics(long id, String description, Map<? extends String, ? extends Object> map) {
        super(map);

        this.setId(id);
        this.setDescription(description);
    }

    /**
     * @return
     */
    public long getId() {
        return this.id;
    }

    /**
     * @param id
     */
    public void setId(long id) {
        this.id = (id < 0) ? -id : id;
    }

    /**
     * @return
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description == null ? "" : description;
    }

    /**
     * @return
     */
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    /**
     * @param timestamp
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp == null ? LocalDateTime.now() : timestamp;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "id = " + this.id + ", timestamp = " + this.timestamp.toString();
    }
}
