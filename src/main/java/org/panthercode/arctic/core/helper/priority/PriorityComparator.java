package org.panthercode.arctic.core.helper.priority;

import java.util.Comparator;

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
public class PriorityComparator implements Comparator<Priority> {

    @Override
    public int compare(Priority firstPriority, Priority otherPriority) {
        return otherPriority.priority() - firstPriority.priority();
    }
}
