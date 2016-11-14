package org.panthercode.arctic.core.arguments;

import org.testng.annotations.Test;

/**
 * Test class for ArgumentUtils
 *
 * @author PantherCode
 */
public class ArgumentUtilsTest {

    private static final String TEST_OBJECT_NAME = "test object";

    private static final String NULL_STRING = null;
    private static final String VALID_STRING = "Hello World";

    private static final long LONG_ZERO = 0;
    private static final long LONG_POSITIVE_VALUE = 1234;
    private static final long LONG_NEGATIVE_VALUE = -1234;

    private static final double DOUBLE_ZERO = 0.0;
    private static final double DOUBLE_POSITIVE_VALUE = 1234.56789;
    private static final double DOUBLE_NEGATIVE_VALUE = -1234.56789;

    @Test
    public void T01_assertNull() {
        ArgumentUtils.assertNull(NULL_STRING, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T02_assertNull_Fail() {
        ArgumentUtils.assertNull(VALID_STRING, TEST_OBJECT_NAME);
    }

    @Test
    public void T03_assertNotNull() {
        ArgumentUtils.assertNotNull(VALID_STRING, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void T04_assertNotNull_Fail() {
        ArgumentUtils.assertNotNull(NULL_STRING, TEST_OBJECT_NAME);
    }

    @Test
    public void T05_assertLessZero_Long() {
        ArgumentUtils.assertLessZero(LONG_NEGATIVE_VALUE, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T06_assertLessZero_Long_Fail_Zero() {
        ArgumentUtils.assertLessZero(LONG_ZERO, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T07_assertLessZero_Long_Fail_GreaterZero() {
        ArgumentUtils.assertLessZero(LONG_POSITIVE_VALUE, TEST_OBJECT_NAME);
    }

    @Test
    public void T08_assertGreaterZero_Long() {
        ArgumentUtils.assertGreaterZero(LONG_POSITIVE_VALUE, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T09_assertGreaterZero_Long_Fail_Zero() {
        ArgumentUtils.assertGreaterZero(LONG_ZERO, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T10_assertGreaterZero_Long_Fail_LessZero() {
        ArgumentUtils.assertGreaterZero(LONG_NEGATIVE_VALUE, TEST_OBJECT_NAME);
    }

    @Test
    public void T11_assertLessOrEqualsZero_Long() {
        ArgumentUtils.assertLessOrEqualsZero(LONG_NEGATIVE_VALUE, TEST_OBJECT_NAME);
    }

    @Test
    public void T12_assertLessOrEqualsZero_Long_Zero() {
        ArgumentUtils.assertLessOrEqualsZero(LONG_ZERO, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T13_assertLessOrEqualsZero_Long_Fail_GreaterZero() {
        ArgumentUtils.assertLessOrEqualsZero(LONG_POSITIVE_VALUE, TEST_OBJECT_NAME);
    }

    @Test
    public void T14_assertGreaterOrEqualsZero_Long() {
        ArgumentUtils.assertGreaterOrEqualsZero(LONG_POSITIVE_VALUE, TEST_OBJECT_NAME);
    }

    @Test
    public void T15_assertGreaterOrEqualsZero_Long_Zero() {
        ArgumentUtils.assertGreaterOrEqualsZero(LONG_ZERO, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T16_assertGreaterOrEqualsZero_Long_Fail_LessZero() {
        ArgumentUtils.assertGreaterOrEqualsZero(LONG_NEGATIVE_VALUE, TEST_OBJECT_NAME);
    }

    @Test
    public void T17_assertLessThan_Long() {
        ArgumentUtils.assertLessThan(LONG_NEGATIVE_VALUE, LONG_ZERO, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T18_assertLessThan_Long_Equals() {
        ArgumentUtils.assertLessThan(LONG_ZERO, LONG_ZERO, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T19_assertLessThan_Long_Fail_Greater() {
        ArgumentUtils.assertLessThan(LONG_POSITIVE_VALUE, LONG_ZERO, TEST_OBJECT_NAME);
    }

    @Test
    public void T20_assertLessOrEqualsThan_Long() {
        ArgumentUtils.assertLessOrEqualsThan(LONG_NEGATIVE_VALUE, LONG_ZERO, TEST_OBJECT_NAME);
    }

    @Test
    public void T21_assertLessOrEqualsThan_Long_Equals() {
        ArgumentUtils.assertLessOrEqualsThan(LONG_ZERO, LONG_ZERO, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T22_assertLessThanOrEquals_Long_Fail_Greater() {
        ArgumentUtils.assertLessOrEqualsThan(LONG_POSITIVE_VALUE, LONG_ZERO, TEST_OBJECT_NAME);
    }

    @Test
    public void T23_assertGreaterThan_Long() {
        ArgumentUtils.assertGreaterThan(LONG_POSITIVE_VALUE, LONG_ZERO, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T24_assertGreaterThan_Long_Equals() {
        ArgumentUtils.assertLessThan(LONG_ZERO, LONG_ZERO, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T25_assertGreaterThan_Long_Fail_Less() {
        ArgumentUtils.assertGreaterThan(LONG_NEGATIVE_VALUE, LONG_ZERO, TEST_OBJECT_NAME);
    }

    @Test
    public void T26_assertGreaterOrEqualsThan_Long() {
        ArgumentUtils.assertGreaterOrEqualsThan(LONG_POSITIVE_VALUE, LONG_ZERO, TEST_OBJECT_NAME);
    }

    @Test
    public void T27_assertLessOrEqualsThan_Long_Equals() {
        ArgumentUtils.assertGreaterOrEqualsThan(LONG_ZERO, LONG_ZERO, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T28_assertGreaterThanOrEquals_Long_Fail_Less() {
        ArgumentUtils.assertGreaterOrEqualsThan(LONG_NEGATIVE_VALUE, LONG_ZERO, TEST_OBJECT_NAME);
    }


}
