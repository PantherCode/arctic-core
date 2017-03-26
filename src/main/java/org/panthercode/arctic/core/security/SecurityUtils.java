package org.panthercode.arctic.core.security;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;

/**
 * Created by architect on 22.03.17.
 */
public class SecurityUtils {
    /**
     *
     */
    private SecurityUtils() {
    }

    /**
     * @param alphabet
     * @param length
     * @return
     */
    public static char[] generatePasswort(char[] alphabet, int length) {
        throw new NotImplementedException();
    }

    /**
     * @param data
     * @param other
     * @return
     */
    public static boolean equals(char[] data, char[] other) {
        if (data == null) {
            data = new char[0];
        }

        if (other == null) {
            other = new char[0];
        }

        int length = Math.min(data.length, other.length);

        boolean flag = (data.length == other.length);

        for (int index = 0; index < length; index++) {
            if (data[index] != other[index]) {
                flag = false;
            }
        }

        return flag;
    }

    /**
     * @param data
     * @param other
     * @return
     */
    public static boolean equals(byte[] data, byte[] other) {
        if (data == null) {
            data = new byte[0];
        }

        if (other == null) {
            other = new byte[0];
        }

        int length = Math.min(data.length, other.length);

        boolean flag = (data.length == other.length);

        for (int index = 0; index < length; index++) {
            if (data[index] != other[index]) {
                flag = false;
            }
        }

        return flag;
    }

    /**
     * @param array
     */
    public static void clearArray(char[] array) {
        Arrays.fill(array, (char) 0);
    }

    /**
     * @param array
     */
    public static void clearArray(byte[] array) {
        Arrays.fill(array, (byte) 0);
    }
}
