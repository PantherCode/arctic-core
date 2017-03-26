package org.panthercode.arctic.core.http.entity;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.helper.Validator;

/**
 * Created by architect on 26.03.17.
 */
public abstract class AbstractEntity<T> {
    /**
     *
     */
    private T data;

    /**
     *
     */
    public AbstractEntity() {
        this(null);
    }

    /**
     *
     * @param data
     */
    public AbstractEntity(T data) {
        this.data = data;
    }

    /**
     *
     * @return
     */
    public T getData() {
        return this.data;
    }

    /**
     *
     * @param data
     */
    public void setData(T data) {
        this.data = ArgumentUtils.checkNotNull(data, "content");
    }

    /**
     *
     * @param validator
     * @return
     */
    public boolean accept(Validator<T> validator) {
        return ArgumentUtils.checkNotNull(validator, "validator").accept(this.data);
    }

    /**
     *
     * @param validator
     * @return
     */
    public T validate(Validator<T> validator) {
        return ArgumentUtils.checkNotNull(validator, "validator").validate(this.data);
    }
}
