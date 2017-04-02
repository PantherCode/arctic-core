package org.panthercode.arctic.core.runtime.service;

import org.panthercode.arctic.core.event.Handler;
import org.panthercode.arctic.core.helper.identity.Identifiable;
import org.panthercode.arctic.core.helper.version.Versionable;
import org.panthercode.arctic.core.runtime.message.Message;
import org.panthercode.arctic.core.runtime.message.MessageResponse;

/**
 * Created by architect on 05.03.17.
 */
public interface Service<E> extends Identifiable, Versionable, BackgroundService {
    /**
     * @param message
     */
    <T extends E> void process(Message<T> message);

    <T extends E> void process(Message<T> message, Handler<MessageResponse<T>> responseHandler);

    <T extends E> void process(Message<T> message, Handler<MessageResponse<T>> responseHandler, Handler<Exception> exceptionHandler);
}
