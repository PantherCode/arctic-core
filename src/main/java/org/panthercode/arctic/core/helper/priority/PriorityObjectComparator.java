package org.panthercode.arctic.core.helper.priority;

import java.util.Comparator;

/**
 * Created by architect on 13.12.16.
 */
public class PriorityObjectComparator<T extends PriorityObject> implements Comparator<T> {

    @Override
    public int compare(T first, T other) {
        return first.getPriority().priority() - other.getPriority().priority();
    }
}
