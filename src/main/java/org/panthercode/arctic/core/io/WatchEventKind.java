package org.panthercode.arctic.core.io;

import org.panthercode.arctic.core.arguments.ArgumentUtils;

import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * @author PantherCode
 * @since 1.0
 */
public enum WatchEventKind {
    CREATE("Create"),

    DELETE("Delete"),

    MODIFY("Modify"),

    OVERFLOW("Overflow");

    private final String value;

    WatchEventKind(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static WatchEventKind valueOf(WatchEvent.Kind<?> kind) {
        ArgumentUtils.checkNotNull(kind, "watch event kind");

        if (kind == ENTRY_CREATE) {
            return WatchEventKind.CREATE;
        } else if (kind == ENTRY_MODIFY) {
            return WatchEventKind.MODIFY;
        } else if (kind == ENTRY_DELETE) {
            return WatchEventKind.DELETE;
        } else if (kind == StandardWatchEventKinds.OVERFLOW) {
            return WatchEventKind.OVERFLOW;
        } else {
            throw new IllegalArgumentException("The given watch event kind is unknown.");
        }
    }
}
