package org.panthercode.arctic.core.helper.event;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Class to handle state changes of objects. To handle more complex states than single values, all values must capsule
 * in extra class.
 *
 * @author PantherCode
 * @see Handler
 * @since 1.0
 */
public class Event<T> {

    /**
     * value before change
     */
    private T beforeState;

    /**
     * value after change
     */
    private T afterState;

    /**
     * object raised  the event
     */
    private Object source;

    /**
     * flag whether event is handled or not
     */
    private boolean isHandled = false;

    /**
     * Constructor
     *
     * @param source      object raused the event
     * @param beforeState value before change
     * @param afterState  value after change
     */
    public Event(Object source, T beforeState, T afterState) {
        this.source = source;
        this.beforeState = beforeState;
        this.afterState = afterState;
    }

    /**
     * Returns the value of state before change.
     *
     * @return Returns the value of state before change.
     */
    public T before() {
        return this.beforeState;
    }

    /**
     * Returns the value of state after change.
     *
     * @return Returns the value of state after change.
     */
    public T after() {
        return this.afterState;
    }

    /**
     * Returns the object raised the event.
     *
     * @return Returns the object raised the event.
     */
    public Object source() {
        return this.source;
    }

    /**
     * Returns the casted object, that raised the event.
     *
     * @param classType class type of source
     * @param <E>       generic class type
     * @return Returns the casted object, that raised the event.
     * @throws ClassCastException Is thrown if an error occurred while casting source object.
     */
    public <E> E source(Class<E> classType)
            throws ClassCastException {
        return classType.cast(this.source);
    }

    /**
     * Sets the handle flag of event.
     *
     * @param handled value of flag
     */
    public void isHandled(boolean handled) {
        this.isHandled = handled;
    }

    /**
     * Returns a flag that indicates whether the event is handled or not.
     *
     * @return Returns <tt>true</tt> if event is handled; Otherwise <tt>false</tt>
     */
    public boolean isHandled() {
        return isHandled;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return Returns a string representation of the object.
     */
    @Override
    public String toString() {
        return "before: " + this.beforeState + ", after: " + this.afterState;
    }

    /**
     * Checks if this object is equals to another one.
     *
     * @param obj other object for comparison
     * @return Returns <code>true</code> if both objects are equal; Otherwise <tt>false</tt>.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Event)) {
            return false;
        }

        Event other = (Event) obj;

        return this.source.equals(other.source) &&
                this.beforeState == other.beforeState &&
                this.afterState == other.afterState;
    }

    /**
     * Returns a hash code value of this object.
     *
     * @return Returns a hash code value of this object.
     */
    @Override
    public int hashCode() {
        return Math.abs(new HashCodeBuilder()
                .append(this.source.hashCode())
                .append(this.beforeState.hashCode())
                .append(this.afterState.hashCode())
                .toHashCode());
    }
}
