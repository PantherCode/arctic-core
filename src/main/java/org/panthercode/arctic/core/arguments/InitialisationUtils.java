package org.panthercode.arctic.core.arguments;

/**
 * Created by architect on 03.10.16.
 */
public class InitialisationUtils {

    private InitialisationUtils() {
    }

    public static String string(String value) {
        return InitialisationUtils.string(value, "");
    }

    public static String string(String value, String defaultValue) {
        return (value == null) ? ((defaultValue == null) ? "" : defaultValue) : value;
    }
}
