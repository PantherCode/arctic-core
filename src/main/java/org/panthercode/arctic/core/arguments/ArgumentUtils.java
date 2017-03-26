/*
 * Copyright 2016 PantherCode
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.panthercode.arctic.core.arguments;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.util.Collection;

/**
 * This class hashHandler functions to check arguments, whether they have a valid value. It's useful to asserting valid
 * value of (method) parameters beforeRun using those for processing.
 * <p>
 * Based on Google's Guava <tt>Preconditions</tt> class
 *
 * @author PantherCode
 * @since 1.0
 */
public class ArgumentUtils {

    /**
     * Beginning of each error message
     */
    private static String PREFIX = "The value of ";

    /**
     * private Constructor
     */
    private ArgumentUtils() {
    }

    /**
     * Asserts that the parameter's value is null.
     *
     * @param value parameter to check
     * @param name  name of parameter, which appears in error message
     * @throws IllegalArgumentException Is thrown if the value is not null.
     */
    public static void checkNull(final Object value, final String name)
            throws IllegalArgumentException {
        ArgumentUtils.checkNull(value, name, null);
    }

    public static void checkNull(final Object value, final String name, String additionalMessage, Object... args) {
        Preconditions.checkArgument(value == null, generateErrorMessage(name, "not null", additionalMessage), args);
    }

    /**
     * Asserts that the parameter's value is not null.
     *
     * @param value parameter to check
     * @param name  name of parameter, which appears in error message
     * @throws NullPointerException Is thrown if the value is null.
     */
    public static <T> T checkNotNull(final T value, final String name)
            throws NullPointerException {
        return ArgumentUtils.checkNotNull(value, name, null);
    }

    public static <T> T checkNotNull(final T value, final String name, String additionalMessage, Object... args) {
        return Preconditions.checkNotNull(value, generateErrorMessage(name, "null", additionalMessage), args);
    }

    public static byte checkLessZero(final byte value, final String name)
            throws IllegalArgumentException {
        return (byte) ArgumentUtils.checkLessZero((long) value, name, null);
    }

    public static byte checkLessZero(final byte value, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (byte) ArgumentUtils.checkLessZero((long) value, name, additionalMessage, args);
    }

    public static short checkLessZero(final short value, final String name)
            throws IllegalArgumentException {
        return (short) ArgumentUtils.checkLessZero((long) value, name, null);
    }

    public static short checkLessZero(final short value, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (short) ArgumentUtils.checkLessZero((long) value, name, additionalMessage, args);
    }

    public static int checkLessZero(final int value, final String name)
            throws IllegalArgumentException {
        return (int) ArgumentUtils.checkLessZero((long) value, name, null);
    }

    public static int checkLessZero(final int value, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (int) ArgumentUtils.checkLessZero((long) value, name, additionalMessage, args);
    }

    /**
     * Asserts that the parameter's value is (real) less than zero.
     *
     * @param value parameter to check
     * @param name  name of parameter, which appears in error message
     * @throws IllegalArgumentException Is thrown if the value is zero or greater.
     */
    public static long checkLessZero(final long value, final String name)
            throws IllegalArgumentException {
        return ArgumentUtils.checkLessZero(value, name, null);
    }

    public static long checkLessZero(final long value, final String name, String additionalMessage, Object... args) {
        Preconditions.checkArgument(value < 0L, generateErrorMessage(name, "equals or greater than zero", additionalMessage), args);

        return value;
    }

    public static float checkLessZero(final float value, final String name)
            throws IllegalArgumentException {
        return (float) ArgumentUtils.checkLessZero((double) value, name, null);
    }

    public static float checkLessZero(final float value, final String name, String additionalMessage, Object... args) {
        return (float) ArgumentUtils.checkLessZero((double) value, name, null);
    }

    public static double checkLessZero(final double value, final String name)
            throws IllegalArgumentException {
        return ArgumentUtils.checkLessZero(value, name, null);
    }

    public static double checkLessZero(final double value, final String name, String additionalMessage, Object... args) {
        Preconditions.checkArgument(value < 0.0, generateErrorMessage(name, "equals or greater than zero", additionalMessage), args);

        return value;
    }

    public static byte checkGreaterZero(final byte value, final String name)
            throws IllegalArgumentException {
        return (byte) ArgumentUtils.checkGreaterZero((long) value, name, null);
    }

    public static byte checkGreaterZero(final byte value, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (byte) ArgumentUtils.checkGreaterZero((long) value, name, additionalMessage, args);
    }

    public static short checkGreaterZero(final short value, final String name)
            throws IllegalArgumentException {
        return (short) ArgumentUtils.checkGreaterZero((long) value, name, null);
    }

    public static short checkGreaterZero(final short value, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (short) ArgumentUtils.checkGreaterZero((long) value, name, additionalMessage, args);
    }

    public static int checkGreaterZero(final int value, final String name)
            throws IllegalArgumentException {
        return (int) ArgumentUtils.checkGreaterZero((long) value, name, null);
    }

    public static int checkGreaterZero(final int value, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (int) ArgumentUtils.checkGreaterZero((long) value, name, additionalMessage, args);
    }

    /**
     * Asserts that the parameter's value is (real) less than zero.
     *
     * @param value parameter to check
     * @param name  name of parameter, which appears in error message
     * @throws IllegalArgumentException Is thrown if the value is zero or greater.
     */
    public static long checkGreaterZero(final long value, final String name)
            throws IllegalArgumentException {
        return ArgumentUtils.checkGreaterZero(value, name, null);
    }

    public static long checkGreaterZero(final long value, final String name, String additionalMessage, Object... args) {
        Preconditions.checkArgument(value > 0L, generateErrorMessage(name, "equals or less than zero", additionalMessage), args);

        return value;
    }

    public static float checkGreaterZero(final float value, final String name)
            throws IllegalArgumentException {
        return (float) ArgumentUtils.checkGreaterZero((double) value, name, null);
    }

    public static float checkGreaterZero(final float value, final String name, String additionalMessage, Object... args) {
        return (float) ArgumentUtils.checkGreaterZero((double) value, name, null);
    }

    public static double checkGreaterZero(final double value, final String name)
            throws IllegalArgumentException {
        return ArgumentUtils.checkGreaterZero(value, name, null);
    }

    public static double checkGreaterZero(final double value, final String name, String additionalMessage, Object... args) {
        Preconditions.checkArgument(value > 0.0, generateErrorMessage(name, "equals or less than zero", additionalMessage), args);

        return value;
    }

    public static byte checkLessOrEqualsZero(final byte value, final String name)
            throws IllegalArgumentException {
        return (byte) ArgumentUtils.checkLessOrEqualsZero((long) value, name, null);
    }

    public static byte checkLessOrEqualsZero(final byte value, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (byte) ArgumentUtils.checkLessOrEqualsZero((long) value, name, additionalMessage, args);
    }

    public static short checkLessOrEqualsZero(final short value, final String name)
            throws IllegalArgumentException {
        return (short) ArgumentUtils.checkLessOrEqualsZero((long) value, name, null);
    }

    public static short checkLessOrEqualsZero(final short value, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (short) ArgumentUtils.checkLessOrEqualsZero((long) value, name, additionalMessage, args);
    }

    public static int checkLessOrEqualsZero(final int value, final String name)
            throws IllegalArgumentException {
        return (int) ArgumentUtils.checkLessOrEqualsZero((long) value, name, null);
    }

    public static int checkLessOrEqualsZero(final int value, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (int) ArgumentUtils.checkLessOrEqualsZero((long) value, name, additionalMessage, args);
    }

    public static long checkLessOrEqualsZero(final long value, final String name)
            throws IllegalArgumentException {
        return ArgumentUtils.checkLessOrEqualsZero(value, name, null);
    }

    public static long checkLessOrEqualsZero(final long value, final String name, String additionalMessage, Object... args) {
        Preconditions.checkArgument(value <= 0L, generateErrorMessage(name, "greater than zero", additionalMessage), args);

        return value;
    }

    public static float checkLessOrEqualsZero(final float value, final String name)
            throws IllegalArgumentException {
        return (float) ArgumentUtils.checkLessOrEqualsZero((double) value, name, null);
    }

    public static float checkLessOrEqualsZero(final float value, final String name, String additionalMessage, Object... args) {
        return (float) ArgumentUtils.checkLessOrEqualsZero((double) value, name, null);
    }

    public static double checkLessOrEqualsZero(final double value, final String name)
            throws IllegalArgumentException {
        return ArgumentUtils.checkLessOrEqualsZero(value, name, null);
    }

    public static double checkLessOrEqualsZero(final double value, final String name, String additionalMessage, Object... args) {
        Preconditions.checkArgument(value <= 0.0, generateErrorMessage(name, "greater than zero", additionalMessage), args);

        return value;
    }

    public static byte checkGreaterOrEqualsZero(final byte value, final String name)
            throws IllegalArgumentException {
        return (byte) ArgumentUtils.checkGreaterOrEqualsZero((long) value, name, null);
    }

    public static byte checkGreaterOrEqualsZero(final byte value, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (byte) ArgumentUtils.checkGreaterOrEqualsZero((long) value, name, additionalMessage, args);
    }

    public static short checkGreaterOrEqualsZero(final short value, final String name)
            throws IllegalArgumentException {
        return (short) ArgumentUtils.checkGreaterOrEqualsZero((long) value, name, null);
    }

    public static short checkGreaterOrEqualsZero(final short value, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (short) ArgumentUtils.checkGreaterOrEqualsZero((long) value, name, additionalMessage, args);
    }

    public static int checkGreaterOrEqualsZero(final int value, final String name)
            throws IllegalArgumentException {
        return (int) ArgumentUtils.checkGreaterOrEqualsZero((long) value, name, null);
    }

    public static int checkGreaterOrEqualsZero(final int value, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (int) ArgumentUtils.checkGreaterOrEqualsZero((long) value, name, additionalMessage, args);
    }

    public static long checkGreaterOrEqualsZero(final long value, final String name)
            throws IllegalArgumentException {
        return ArgumentUtils.checkGreaterOrEqualsZero(value, name, null);
    }

    public static long checkGreaterOrEqualsZero(final long value, final String name, String additionalMessage, Object... args) {
        Preconditions.checkArgument(value >= 0L, generateErrorMessage(name, "less than zero", additionalMessage), args);

        return value;
    }

    public static float checkGreaterOrEqualsZero(final float value, final String name)
            throws IllegalArgumentException {
        return (float) ArgumentUtils.checkGreaterOrEqualsZero((double) value, name, null);
    }

    public static float checkGreaterOrEqualsZero(final float value, final String name, String additionalMessage, Object... args) {
        return (float) ArgumentUtils.checkGreaterOrEqualsZero((double) value, name, null);
    }

    public static double checkGreaterOrEqualsZero(final double value, final String name)
            throws IllegalArgumentException {
        return ArgumentUtils.checkGreaterOrEqualsZero(value, name, null);
    }

    public static double checkGreaterOrEqualsZero(final double value, final String name, String additionalMessage, Object... args) {
        Preconditions.checkArgument(value >= 0.0, generateErrorMessage(name, "less than zero", additionalMessage), args);

        return value;
    }

    public static byte checkLessThan(final byte value, final long limit, final String name)
            throws IllegalArgumentException {
        return (byte) ArgumentUtils.checkLessThan((long) value, limit, name, null);
    }

    public static byte checkLessThan(final byte value, final long limit, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (byte) ArgumentUtils.checkLessThan((long) value, limit, name, additionalMessage, args);
    }

    public static short checkLessThan(final short value, final long limit, final String name)
            throws IllegalArgumentException {
        return (short) ArgumentUtils.checkLessThan((long) value, limit, name, null);
    }

    public static short checkLessThan(final short value, final long limit, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (short) ArgumentUtils.checkLessThan((long) value, limit, name, additionalMessage, args);
    }

    public static int checkLessThan(final int value, final long limit, final String name)
            throws IllegalArgumentException {
        return (int) ArgumentUtils.checkLessThan((long) value, limit, name, null);
    }

    public static int checkLessThan(final int value, final long limit, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (int) ArgumentUtils.checkLessThan((long) value, limit, name, additionalMessage, args);
    }

    public static long checkLessThan(final long value, final long limit, final String name)
            throws IllegalArgumentException {
        return ArgumentUtils.checkLessThan(value, limit, name, null);
    }

    public static long checkLessThan(final long value, final long limit, final String name, String additionalMessage, Object... args) {
        Preconditions.checkArgument(value < limit, generateErrorMessage(name, "equals or greater than the limit", additionalMessage), args);

        return value;
    }

    public static float checkLessThan(final float value, final double limit, final String name)
            throws IllegalArgumentException {
        return (float) ArgumentUtils.checkLessThan((double) value, limit, name, null);
    }

    public static float checkLessThan(final float value, final double limit, final String name, String additionalMessage, Object... args) {
        return (float) ArgumentUtils.checkLessThan((double) value, limit, name, null);
    }

    public static double checkLessThan(final double value, final double limit, final String name)
            throws IllegalArgumentException {
        return ArgumentUtils.checkLessThan(value, limit, name, null);
    }

    public static double checkLessThan(final double value, final double limit, final String name, String additionalMessage, Object... args) {
        Preconditions.checkArgument(value < limit, generateErrorMessage(name, "equals or greater than the limit", additionalMessage), args);

        return value;
    }

    public static byte checkLessOrEqualsThan(final byte value, final long limit, final String name)
            throws IllegalArgumentException {
        return (byte) ArgumentUtils.checkLessOrEqualsThan((long) value, limit, name, null);
    }

    public static byte checkLessOrEqualsThan(final byte value, final long limit, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (byte) ArgumentUtils.checkLessOrEqualsThan((long) value, limit, name, additionalMessage, args);
    }

    public static short checkLessOrEqualsThan(final short value, final long limit, final String name)
            throws IllegalArgumentException {
        return (short) ArgumentUtils.checkLessOrEqualsThan((long) value, limit, name, null);
    }

    public static short checkLessOrEqualsThan(final short value, final long limit, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (short) ArgumentUtils.checkLessOrEqualsThan((long) value, limit, name, additionalMessage, args);
    }

    public static int checkLessOrEqualsThan(final int value, final long limit, final String name)
            throws IllegalArgumentException {
        return (int) ArgumentUtils.checkLessOrEqualsThan((long) value, limit, name, null);
    }

    public static int checkLessOrEqualsThan(final int value, final long limit, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (int) ArgumentUtils.checkLessOrEqualsThan((long) value, limit, name, additionalMessage, args);
    }

    public static long checkLessOrEqualsThan(final long value, final long limit, final String name)
            throws IllegalArgumentException {
        return ArgumentUtils.checkLessOrEqualsThan(value, limit, name, null);
    }

    public static long checkLessOrEqualsThan(final long value, final long limit, final String name, String additionalMessage, Object... args) {
        Preconditions.checkArgument(value <= limit, generateErrorMessage(name, "greater than the limit", additionalMessage), args);

        return value;
    }

    public static float checkLessOrEqualsThan(final float value, final double limit, final String name)
            throws IllegalArgumentException {
        return (float) ArgumentUtils.checkLessOrEqualsThan((double) value, limit, name, null);
    }

    public static float checkLessOrEqualsThan(final float value, final double limit, final String name, String additionalMessage, Object... args) {
        return (float) ArgumentUtils.checkLessOrEqualsThan((double) value, limit, name, null);
    }

    public static double checkLessOrEqualsThan(final double value, final double limit, final String name)
            throws IllegalArgumentException {
        return ArgumentUtils.checkLessOrEqualsThan(value, limit, name, null);
    }

    public static double checkLessOrEqualsThan(final double value, final double limit, final String name, String additionalMessage, Object... args) {
        Preconditions.checkArgument(value <= limit, generateErrorMessage(name, "greater than the limit", additionalMessage), args);

        return value;
    }

    public static byte checkGreaterThan(final byte value, final long limit, final String name)
            throws IllegalArgumentException {
        return (byte) ArgumentUtils.checkGreaterThan((long) value, limit, name, null);
    }

    public static byte checkGreaterThan(final byte value, final long limit, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (byte) ArgumentUtils.checkGreaterThan((long) value, limit, name, additionalMessage, args);
    }

    public static short checkGreaterThan(final short value, final long limit, final String name)
            throws IllegalArgumentException {
        return (short) ArgumentUtils.checkGreaterThan((long) value, limit, name, null);
    }

    public static short checkGreaterThan(final short value, final long limit, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (short) ArgumentUtils.checkGreaterThan((long) value, limit, name, additionalMessage, args);
    }

    public static int checkGreaterThan(final int value, final long limit, final String name)
            throws IllegalArgumentException {
        return (int) ArgumentUtils.checkGreaterThan((long) value, limit, name, null);
    }

    public static int checkGreaterThan(final int value, final long limit, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (int) ArgumentUtils.checkGreaterThan((long) value, limit, name, additionalMessage, args);
    }

    public static long checkGreaterThan(final long value, final long limit, final String name)
            throws IllegalArgumentException {
        return ArgumentUtils.checkGreaterThan(value, limit, name, null);
    }

    public static long checkGreaterThan(final long value, final long limit, final String name, String additionalMessage, Object... args) {
        Preconditions.checkArgument(value > limit, generateErrorMessage(name, "less or equals than the limit", additionalMessage), args);

        return value;
    }

    public static float checkGreaterThan(final float value, final double limit, final String name)
            throws IllegalArgumentException {
        return (float) ArgumentUtils.checkGreaterThan((double) value, limit, name, null);
    }

    public static float checkGreaterThan(final float value, final double limit, final String name, String additionalMessage, Object... args) {
        return (float) ArgumentUtils.checkGreaterThan((double) value, limit, name, null);
    }

    public static double checkGreaterThan(final double value, final double limit, final String name)
            throws IllegalArgumentException {
        return ArgumentUtils.checkGreaterThan(value, limit, name, null);
    }

    public static double checkGreaterThan(final double value, final double limit, final String name, String additionalMessage, Object... args) {
        Preconditions.checkArgument(value > limit, generateErrorMessage(name, "less or equals than the limit", additionalMessage), args);

        return value;
    }

    public static byte checkGreaterOrEqualsThan(final byte value, final long limit, final String name)
            throws IllegalArgumentException {
        return (byte) ArgumentUtils.checkGreaterOrEqualsThan((long) value, limit, name, null);
    }

    public static byte checkGreaterOrEqualsThan(final byte value, final long limit, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (byte) ArgumentUtils.checkGreaterOrEqualsThan((long) value, limit, name, additionalMessage, args);
    }

    public static short checkGreaterOrEqualsThan(final short value, final long limit, final String name)
            throws IllegalArgumentException {
        return (short) ArgumentUtils.checkGreaterOrEqualsThan((long) value, limit, name, null);
    }

    public static short checkGreaterOrEqualsThan(final short value, final long limit, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (short) ArgumentUtils.checkGreaterOrEqualsThan((long) value, limit, name, additionalMessage, args);
    }

    public static int checkGreaterOrEqualsThan(final int value, final long limit, final String name)
            throws IllegalArgumentException {
        return (int) ArgumentUtils.checkGreaterOrEqualsThan((long) value, limit, name, null);
    }

    public static int checkGreaterOrEqualsThan(final int value, final long limit, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (int) ArgumentUtils.checkGreaterOrEqualsThan((long) value, limit, name, additionalMessage, args);
    }

    public static long checkGreaterOrEqualsThan(final long value, final long limit, final String name)
            throws IllegalArgumentException {
        return ArgumentUtils.checkGreaterOrEqualsThan(value, limit, name, null);
    }

    public static long checkGreaterOrEqualsThan(final long value, final long limit, final String name, String additionalMessage, Object... args) {
        Preconditions.checkArgument(value >= limit, generateErrorMessage(name, "less than the limit", additionalMessage), args);

        return value;
    }

    public static float checkGreaterOrEqualsThan(final float value, final double limit, final String name)
            throws IllegalArgumentException {
        return (float) ArgumentUtils.checkGreaterOrEqualsThan((double) value, limit, name, null);
    }

    public static float checkGreaterOrEqualsThan(final float value, final double limit, final String name, String additionalMessage, Object... args) {
        return (float) ArgumentUtils.checkGreaterOrEqualsThan((double) value, limit, name, null);
    }

    public static double checkGreaterOrEqualsThan(final double value, final double limit, final String name)
            throws IllegalArgumentException {
        return ArgumentUtils.checkGreaterOrEqualsThan(value, limit, name, null);
    }

    public static double checkGreaterOrEqualsThan(final double value, final double limit, final String name, String additionalMessage, Object... args) {
        Preconditions.checkArgument(value >= limit, generateErrorMessage(name, "less than the limit", additionalMessage), args);

        return value;
    }

    public static byte checkRange(final byte value, final long lowerLimit, final long upperLimit, final String name)
            throws IllegalArgumentException {
        return (byte) ArgumentUtils.checkRange((long) value, lowerLimit, upperLimit, name, null);
    }

    public static byte checkRange(final byte value, final long lowerLimit, final long upperLimit, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (byte) ArgumentUtils.checkRange((long) value, lowerLimit, upperLimit, name, additionalMessage, args);
    }

    public static short checkRange(final short value, final long lowerLimit, final long upperLimit, final String name)
            throws IllegalArgumentException {
        return (short) ArgumentUtils.checkRange((long) value, lowerLimit, upperLimit, name, null);
    }

    public static short checkRange(final short value, final long lowerLimit, final long upperLimit, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (short) ArgumentUtils.checkRange((long) value, lowerLimit, upperLimit, name, additionalMessage, args);
    }

    public static int checkRange(final int value, final long lowerLimit, final long upperLimit, final String name)
            throws IllegalArgumentException {
        return (int) ArgumentUtils.checkRange((long) value, lowerLimit, upperLimit, name, null);
    }

    public static int checkRange(final int value, final long lowerLimit, final long upperLimit, final String name, String additionalMessage, Object... args)
            throws IllegalArgumentException {
        return (int) ArgumentUtils.checkRange((long) value, lowerLimit, upperLimit, name, additionalMessage, args);
    }

    public static long checkRange(final long value, final long lowerLimit, final long upperLimit, final String name)
            throws IllegalArgumentException {
        return ArgumentUtils.checkRange(value, lowerLimit, upperLimit, name, null);
    }

    public static long checkRange(final long value, final long lowerLimit, final long upperLimit, final String name, String additionalMessage, Object... args) {
        Preconditions.checkArgument(value >= lowerLimit, generateErrorMessage(name, "less than the lower limit", additionalMessage), args);
        Preconditions.checkArgument(value <= upperLimit, generateErrorMessage(name, "greater than the upper limit", additionalMessage), args);

        return value;
    }

    public static float checkRange(final float value, final double lowerLimit, final double upperLimit, final String name)
            throws IllegalArgumentException {
        return (float) ArgumentUtils.checkRange((double) value, lowerLimit, upperLimit, name, null);
    }

    public static float checkRange(final float value, final double lowerLimit, final double upperLimit, final String name, String additionalMessage, Object... args) {
        return (float) ArgumentUtils.checkRange((double) value, lowerLimit, upperLimit, name, null);
    }

    public static double checkRange(final double value, final double lowerLimit, final double upperLimit, final String name)
            throws IllegalArgumentException {
        return ArgumentUtils.checkRange(value, lowerLimit, upperLimit, name, null);
    }

    public static double checkRange(final double value, final double lowerLimit, final double upperLimit, final String name, String additionalMessage, Object... args) {
        Preconditions.checkArgument(value >= lowerLimit, generateErrorMessage(name, "less than the lower limit", additionalMessage), args);
        Preconditions.checkArgument(value <= upperLimit, generateErrorMessage(name, "greater than the upper limit", additionalMessage), args);

        return value;
    }

    public static <T> int checkIndex(final int index, final String name, Collection<T> collection) {
        ArgumentUtils.checkNotNull(collection, "collection");

        return Preconditions.checkPositionIndex(index, collection.size(), generateErrorMessage(name, "out of bounds", null));
    }

    public static <T> int checkIndex(final int index, final String name, T[] array) {
        ArgumentUtils.checkNotNull(array, "array");

        return Preconditions.checkPositionIndex(index, array.length, generateErrorMessage(name, "out of bounds", null));
    }

    public static <T> int checkIndex(final int index, final String name, String object) {
        ArgumentUtils.checkNotNull(object, "object");

        return Preconditions.checkPositionIndex(index, object.length(), generateErrorMessage(name, "out of bounds", null));
    }

    /**
     * Asserts that two parameters have the same value.
     *
     * @param actual   current value of parameter
     * @param expected value, that the parameter should have
     * @param name     name of parameter, which appears in error message
     * @throws IllegalArgumentException Is thrown if the actual value is not equals to the expected one.
     */
    public static void checkEquals(final Object actual, final Object expected, final String name)
            throws IllegalArgumentException {
        if (actual == null) {
            if (expected != null) {
                throw new IllegalArgumentException(PREFIX + name + " is not equals the expected one.");
            }
        } else {
            if (!actual.equals(expected)) {
                throw new IllegalArgumentException(PREFIX + name + " is not equals the expected one.");
            }
        }
    }

    /**
     * Asserts that two parameters don't have the same value.
     *
     * @param actual   current value of parameter
     * @param expected value, that the parameter should have
     * @param name     name of parameter, which appears in error message
     * @throws IllegalArgumentException Is thrown if the actual value is equals to the expected one.
     */
    public static void checkNotEquals(final Object actual, final Object expected, final String name)
            throws IllegalArgumentException {
        if (actual == null) {
            if (expected == null) {
                throw new IllegalArgumentException(PREFIX + name + " is equals the expected one.");
            }
        } else {
            if (actual.equals(expected)) {
                throw new IllegalArgumentException(PREFIX + name + " is equals the expected one.");
            }
        }
    }

    private static String generateErrorMessage(String name, String message, String additionalMessage) {
        StringBuilder builder = new StringBuilder();

        builder.append("The value ");

        if (!Strings.isNullOrEmpty(name)) {
            builder.append("of \'")
                    .append(name)
                    .append("\' ");
        }

        builder.append("is ")
                .append(message)
                .append(".");

        if (!Strings.isNullOrEmpty(additionalMessage)) {
            builder.append(" ")
                    .append(additionalMessage);
        }

        return builder.toString();
    }
}

