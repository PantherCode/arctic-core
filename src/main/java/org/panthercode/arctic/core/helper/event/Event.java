package org.panthercode.arctic.core.helper.event;

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

    public void isHandled(boolean handled) {
        this.isHandled = handled;
    }

    public boolean isHandled() {
        return isHandled;
    }

    //TODO implement toString, Hashcode, equals
}
