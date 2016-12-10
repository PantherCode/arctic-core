package org.panthercode.arctic.core.helper.priority;

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
public enum Priority {

    VERY_HIGH(5, "Very High"),

    HIGH(4, "High"),

    NORMAL(3, "Normal"),

    LOW(2, "Low"),

    VERY_LOW(1, "Very Low");

    private final int priority;

    private final String value;

    Priority(int priority, String value) {
        this.priority = priority;
        this.value = value;
    }

    public int priority() {
        return this.priority;
    }

    public String value() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value();
    }
}
