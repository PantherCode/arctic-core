package org.panthercode.arctic.core.security;

import java.util.Arrays;

/**
 * Created by architect on 22.03.17.
 */
public class SecurityUtils {
    private SecurityUtils() {
    }


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

    public static void clearArray(char[] array) {
        Arrays.fill(array, (char) 0);
    }

    public static void clearArray(byte[] array) {
        Arrays.fill(array, (byte) 0);
    }
}
