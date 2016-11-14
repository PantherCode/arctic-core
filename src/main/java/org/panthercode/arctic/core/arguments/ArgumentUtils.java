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

/**
 * This class contains functions to check arguments, whether they have a valid value. It's useful to asserting valid
 * value of (method) parameters before using those for processing.
 *
 * @author PantherCode
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
    public static void assertNull(final Object value, final String name)
            throws IllegalArgumentException {
        if (value != null) {
            throw new IllegalArgumentException(PREFIX + name + " is not null.");
        }
    }

    /**
     * Asserts that the parameter's value is not null.
     *
     * @param value parameter to check
     * @param name  name of parameter, which appears in error message
     * @throws NullPointerException Is thrown if the value is null.
     */
    public static void assertNotNull(final Object value, final String name)
            throws NullPointerException {
        if (value == null) {
            throw new NullPointerException(PREFIX + name + " is null.");
        }
    }

    /**
     * Asserts that the parameter's value is (real) less than zero.
     *
     * @param value parameter to check
     * @param name  name of parameter, which appears in error message
     * @throws IllegalArgumentException Is thrown if the value is zero or greater.
     */
    public static void assertLessZero(final long value, final String name)
            throws IllegalArgumentException {
        if (value >= 0) {
            throw new IllegalArgumentException(PREFIX + name + " is equals or greater than zero.");
        }
    }

    /**
     * Asserts that the parameter's value is (real) greater than zero.
     *
     * @param value parameter to check
     * @param name  getName of parameter, which appears in error message
     * @throws IllegalArgumentException Is thrown if the value is zero or less.
     */
    public static void assertGreaterZero(final long value, final String name)
            throws IllegalArgumentException {
        if (value <= 0) {
            throw new IllegalArgumentException(PREFIX + name + " is equals or less than zero.");
        }
    }

    /**
     * Asserts that the parameter's value is zero or less.
     *
     * @param value parameter to check
     * @param name  name of parameter, which appears in error message
     * @throws IllegalArgumentException Is thrown if the value is (real) greater than zero.
     */
    public static void assertLessOrEqualsZero(final long value, final String name)
            throws IllegalArgumentException {
        if (value > 0) {
            throw new IllegalArgumentException(PREFIX + name + " is greater than zero.");
        }
    }

    /**
     * Asserts that the parameters's value is zero or greater.
     *
     * @param value parameter to check
     * @param name  name of parameter, which appears in error message
     * @throws IllegalArgumentException Is thrown if the value is (real) less than zero.
     */
    public static void assertGreaterOrEqualsZero(final long value, final String name)
            throws IllegalArgumentException {
        if (value < 0) {
            throw new IllegalArgumentException(PREFIX + name + " is less than zero.");
        }
    }

    /**
     * Asserts that the parameter's value is (real) less than a specific limit.
     *
     * @param value parameter to check
     * @param limit value, which it's not allowed to exceeded
     * @param name  name of parameter, which appears in error message
     * @throws IllegalArgumentException Is thrown if the value is equals or greater than the limit.
     */
    public static void assertLessThan(final long value, final long limit, final String name)
            throws IllegalArgumentException {
        if (value >= limit) {
            throw new IllegalArgumentException(PREFIX + name + " is equals or greater than the limit.");
        }
    }

    /**
     * Asserts that the parameter's value is equals or less than a given limit.
     *
     * @param value parameter to check
     * @param limit value, which it's not allowed to exceeded
     * @param name  name of parameter, which appears in error message
     * @throws IllegalArgumentException Is thrown if the value is (real) greater than the limit.
     */
    public static void assertLessOrEqualsThan(final long value, final long limit, final String name)
            throws IllegalArgumentException {
        if (value > limit) {
            throw new IllegalArgumentException(PREFIX + name + " is greater than the limit");
        }
    }

    /**
     * Asserts that the parameter's value is (real) greater than a specific limit.
     *
     * @param value parameter to check
     * @param limit value, which it's not allowed to fall below
     * @param name  name of parameter, which appears in error message
     * @throws IllegalArgumentException Is thrown if the value is equals or less than the limit.
     */
    public static void assertGreaterThan(final long value, final long limit, final String name)
            throws IllegalArgumentException {
        if (value <= limit) {
            throw new IllegalArgumentException(PREFIX + name + " is less or equals than limit.");
        }
    }

    /**
     * Asserts that the parameter's value is equals or greater than a specific limit.
     *
     * @param value parameter to check
     * @param limit value, which it's not allowed to fall below
     * @param name  name of parameter, which appears in error message
     * @throws IllegalArgumentException Is thrown if the value (real) less than the limit.
     */
    public static void assertGreaterOrEqualsThan(final long value, final long limit, final String name)
            throws IllegalArgumentException {
        if (value < limit) {
            throw new IllegalArgumentException(PREFIX + name + " is less than limit.");
        }
    }

    /**
     * Asserts, that the parameter's value is in a specific range. The value must be (real)
     * greater than the lower limit and (real) less than the upper one.
     *
     * @param value parameter to check
     * @param lower value, which it's not allowed to fall below
     * @param upper value, which it's not allowed to exceeded
     * @param name  getName of parameter, which appears in error message
     * @throws IllegalArgumentException Is thrown if the value is out of the given boundary.
     */
    public static void assertInLimits(final long value, final long lower, final long upper, final String name)
            throws IllegalArgumentException {
        if (value <= lower) {
            throw new IllegalArgumentException(PREFIX + name + " is less than lower limit.");
        }

        if (value >= upper) {
            throw new IllegalArgumentException(PREFIX + name + " is greater than upper limit.");
        }
    }

    /**
     * Asserts that the parameter's value is (real) less than zero.
     *
     * @param value parameter to check
     * @param name  name of parameter, which appears in error message
     * @throws IllegalArgumentException Is thrown if the value is zero or greater.
     */
    public static void assertLessZero(final double value, final String name)
            throws IllegalArgumentException {
        if (value >= 0) {
            throw new IllegalArgumentException(PREFIX + name + " is equals or greater than zero.");
        }
    }

    /**
     * Asserts that the parameter's value is (real) greater than zero.
     *
     * @param value parameter to check
     * @param name  name of parameter, which appears in error message
     * @throws IllegalArgumentException Is thrown if the value is zero or less.
     */
    public static void assertGreaterZero(final double value, final String name)
            throws IllegalArgumentException {
        if (value <= 0) {
            throw new IllegalArgumentException(PREFIX + name + " is equals or less than zero.");
        }
    }

    /**
     * Asserts that the parameter's value is zero or less.
     *
     * @param value parameter to check
     * @param name  name of parameter, which appears in error message
     * @throws IllegalArgumentException Is thrown if the value is (real) greater than zero.
     */
    public static void assertLessOrEqualsZero(final double value, final String name)
            throws IllegalArgumentException {
        if (value > 0) {
            throw new IllegalArgumentException(PREFIX + name + " is greater than zero.");
        }
    }

    /**
     * Asserts that the parameters's value is greater than or equals zero.
     *
     * @param value parameter to check
     * @param name  name of parameter, which appears in error message
     * @throws IllegalArgumentException Is thrown if the value is (real) less than zero.
     */
    public static void assertGreaterOrEqualsZero(final double value, final String name)
            throws IllegalArgumentException {
        if (value < 0) {
            throw new IllegalArgumentException(PREFIX + name + " is less than zero.");
        }
    }

    /**
     * Asserts that the parameter's value is (real) less than a specific limit.
     *
     * @param value parameter to check
     * @param limit value, which it's not allowed to exceeded
     * @param name  name of parameter, which appears in error message
     * @throws IllegalArgumentException Is thrown if the value is equals or greater than the limit.
     */
    public static void assertLessThan(final double value, final double limit, final String name)
            throws IllegalArgumentException {
        if (value >= limit) {
            throw new IllegalArgumentException(PREFIX + name + " is greater than or equals limit.");
        }
    }

    /**
     * Asserts that the parameter's value is equals or less than a specific limit.
     *
     * @param value parameter to check
     * @param limit value, which it's not allowed to exceeded
     * @param name  name of parameter, which appears in error message
     * @throws IllegalArgumentException Is thrown if the value is (real) greater than the limit.
     */
    public static void assertLessOrEqualsThan(final double value, final double limit, final String name)
            throws IllegalArgumentException {
        if (value > limit) {
            throw new IllegalArgumentException(PREFIX + name + " is greater than limit.");
        }
    }

    /**
     * Asserts that the parameter's value is (real) greater than a specific limit.
     *
     * @param value parameter to check
     * @param limit value, which it's not allowed to fall below
     * @param name  name of parameter, which appears in error message
     * @throws IllegalArgumentException Is thrown if the value is equals or less than the limit.
     */
    public static void assertGreaterThan(final double value, final double limit, final String name)
            throws IllegalArgumentException {
        if (value <= limit) {
            throw new IllegalArgumentException(PREFIX + name + " is less or equals than limit.");
        }
    }

    /**
     * Asserts that the parameter's value is equals or greater than a specific limit.
     *
     * @param value parameter to check
     * @param limit value, which it's not allowed to fall below
     * @param name  name of parameter, which appears in error message
     * @throws IllegalArgumentException Is thrown if the value (real) less than the limit.
     */
    public static void assertGreaterOrEqualsThan(final double value, final double limit, final String name)
            throws IllegalArgumentException {
        if (value < limit) {
            throw new IllegalArgumentException(PREFIX + name + " is less than limit.");
        }
    }

    /**
     * Asserts that the parameter's value is in a specific range. The value must be (real)
     * greater than the lower limit and (real) less than the upper one.
     *
     * @param value parameter to check
     * @param lower value, which it's not allowed to fall below
     * @param upper value, which it's not allowed to exceeded
     * @param name  name of parameter, which appears in error message
     * @throws IllegalArgumentException Is thrown if the value is out of the given boundary.
     */
    public static void assertInLimits(final double value, final double lower, final double upper, final String name)
            throws IllegalArgumentException {
        if (value <= lower) {
            throw new IllegalArgumentException(PREFIX + name + " is less than lower limit.");
        }

        if (value >= upper) {
            throw new IllegalArgumentException(PREFIX + name + " is greater than upper limit.");
        }
    }

    /**
     * Asserts that two parameters have the same value.
     *
     * @param actual   current value of parameter
     * @param expected value, that the parameter should have
     * @param name     name of parameter, which appears in error message
     * @throws IllegalArgumentException Is thrown if the actual value is not equals to the expected one.
     */
    public static void assertEquals(final Object actual, final Object expected, final String name)
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
    public static void assertNotEquals(final Object actual, final Object expected, final String name)
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
}

