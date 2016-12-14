package org.panthercode.arctic.core.helper.priority;

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
public class PriorityObject<T> implements Comparable<PriorityObject<T>> {

    private Priority priority = Priority.NORMAL;

    private T content;

    public PriorityObject() {
    }

    public PriorityObject(T content) {
        this(content, Priority.NORMAL);
    }

    public PriorityObject(T content, Priority priority) {
        this.setPriority(priority);
        this.setContent(content);
    }

    public synchronized void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public synchronized void setContent(T content) {
        this.content = content;
    }

    public T getContent() {
        return this.content;
    }

    @Override
    public int compareTo(PriorityObject<T> other) {
        return this.getPriority().priority() - other.getPriority().priority();
    }
}
