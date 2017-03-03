package org.panthercode.arctic.core.runtime.plugins;

/**
 * Created by architect on 02.03.17.
 */
public interface InteractivePlugin extends Plugin {

    Object action(Object data) throws Exception;
}
