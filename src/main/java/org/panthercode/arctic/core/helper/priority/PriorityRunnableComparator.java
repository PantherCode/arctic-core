package org.panthercode.arctic.core.helper.priority;

import java.util.Comparator;

/**
 * Created by architect on 12.12.16.
 */
public class PriorityRunnableComparator implements Comparator<PriorityRunnable> {

    @Override
    public int compare(PriorityRunnable firstRunnable, PriorityRunnable otherRunnable) {
        return otherRunnable.priority().priority() - firstRunnable.priority().priority();
    }
}

