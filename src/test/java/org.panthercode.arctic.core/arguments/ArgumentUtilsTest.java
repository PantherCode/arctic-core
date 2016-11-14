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
    public void T18_assertLessThan_Long_Fail_Equals() {
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
    public void T24_assertGreaterThan_Long_Fail_Equals() {
        ArgumentUtils.assertGreaterThan(LONG_ZERO, LONG_ZERO, TEST_OBJECT_NAME);
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

    @Test
    public void T29_assertLimits_Long() {
        ArgumentUtils.assertLimits(LONG_ZERO, LONG_NEGATIVE_VALUE, LONG_POSITIVE_VALUE, TEST_OBJECT_NAME);
    }

    @Test
    public void T30_assertLimits_Long_EqualsLowerLimit() {
        ArgumentUtils.assertLimits(LONG_NEGATIVE_VALUE, LONG_NEGATIVE_VALUE, LONG_POSITIVE_VALUE, TEST_OBJECT_NAME);
    }

    @Test
    public void T31_assertLimits_Long_EqualsUpperLimit() {
        ArgumentUtils.assertLimits(LONG_POSITIVE_VALUE, LONG_NEGATIVE_VALUE, LONG_POSITIVE_VALUE, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T32_assertLimits_Long_Fail_Less() {
        ArgumentUtils.assertLimits(LONG_NEGATIVE_VALUE, LONG_ZERO, LONG_POSITIVE_VALUE, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T33_assertLimits_Long_Fail_Greater() {
        ArgumentUtils.assertLimits(LONG_POSITIVE_VALUE, LONG_NEGATIVE_VALUE, LONG_ZERO, TEST_OBJECT_NAME);
    }

    @Test
    public void T34_assertLessZero_Double() {
        ArgumentUtils.assertLessZero(DOUBLE_NEGATIVE_VALUE, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T35_assertLessZero_Double_Fail_Zero() {
        ArgumentUtils.assertLessZero(DOUBLE_ZERO, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T36_assertLessZero_Double_Fail_GreaterZero() {
        ArgumentUtils.assertLessZero(DOUBLE_POSITIVE_VALUE, TEST_OBJECT_NAME);
    }

    @Test
    public void T37_assertGreaterZero_Double() {
        ArgumentUtils.assertGreaterZero(DOUBLE_POSITIVE_VALUE, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T38_assertGreaterZero_Double_Fail_Zero() {
        ArgumentUtils.assertGreaterZero(DOUBLE_ZERO, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T39_assertGreaterZero_Double_Fail_LessZero() {
        ArgumentUtils.assertGreaterZero(DOUBLE_NEGATIVE_VALUE, TEST_OBJECT_NAME);
    }

    @Test
    public void T40_assertLessOrEqualsZero_Double() {
        ArgumentUtils.assertLessOrEqualsZero(DOUBLE_NEGATIVE_VALUE, TEST_OBJECT_NAME);
    }

    @Test
    public void T41_assertLessOrEqualsZero_Double_Zero() {
        ArgumentUtils.assertLessOrEqualsZero(DOUBLE_ZERO, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T42_assertLessOrEqualsZero_Double_Fail_GreaterZero() {
        ArgumentUtils.assertLessOrEqualsZero(DOUBLE_POSITIVE_VALUE, TEST_OBJECT_NAME);
    }

    @Test
    public void T43_assertGreaterOrEqualsZero_Double() {
        ArgumentUtils.assertGreaterOrEqualsZero(DOUBLE_POSITIVE_VALUE, TEST_OBJECT_NAME);
    }

    @Test
    public void T44_assertGreaterOrEqualsZero_Double_Zero() {
        ArgumentUtils.assertGreaterOrEqualsZero(DOUBLE_ZERO, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T45_assertGreaterOrEqualsZero_Double_Fail_LessZero() {
        ArgumentUtils.assertGreaterOrEqualsZero(DOUBLE_NEGATIVE_VALUE, TEST_OBJECT_NAME);
    }

    @Test
    public void T46_assertLessThan_Double() {
        ArgumentUtils.assertLessThan(DOUBLE_NEGATIVE_VALUE, DOUBLE_ZERO, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T47_assertLessThan_Double_Fail_Equals() {
        ArgumentUtils.assertLessThan(DOUBLE_ZERO, DOUBLE_ZERO, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T48_assertLessThan_Double_Fail_Greater() {
        ArgumentUtils.assertLessThan(DOUBLE_POSITIVE_VALUE, DOUBLE_ZERO, TEST_OBJECT_NAME);
    }

    @Test
    public void T49_assertLessOrEqualsThan_Double() {
        ArgumentUtils.assertLessOrEqualsThan(DOUBLE_NEGATIVE_VALUE, DOUBLE_ZERO, TEST_OBJECT_NAME);
    }

    @Test
    public void T50_assertLessOrEqualsThan_Double_Equals() {
        ArgumentUtils.assertLessOrEqualsThan(DOUBLE_ZERO, DOUBLE_ZERO, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T51_assertLessThanOrEquals_Double_Fail_Greater() {
        ArgumentUtils.assertLessOrEqualsThan(DOUBLE_POSITIVE_VALUE, DOUBLE_ZERO, TEST_OBJECT_NAME);
    }

    @Test
    public void T52_assertGreaterThan_Double() {
        ArgumentUtils.assertGreaterThan(DOUBLE_POSITIVE_VALUE, DOUBLE_ZERO, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T53_assertGreaterThan_Double_Fail_Equals() {
        ArgumentUtils.assertGreaterThan(DOUBLE_ZERO, DOUBLE_ZERO, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T54_assertGreaterThan_Double_Fail_Less() {
        ArgumentUtils.assertGreaterThan(DOUBLE_NEGATIVE_VALUE, DOUBLE_ZERO, TEST_OBJECT_NAME);
    }

    @Test
    public void T55_assertGreaterOrEqualsThan_Double() {
        ArgumentUtils.assertGreaterOrEqualsThan(DOUBLE_POSITIVE_VALUE, DOUBLE_ZERO, TEST_OBJECT_NAME);
    }

    @Test
    public void T56_assertLessOrEqualsThan_Double_Equals() {
        ArgumentUtils.assertGreaterOrEqualsThan(DOUBLE_ZERO, DOUBLE_ZERO, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T57_assertGreaterThanOrEquals_Double_Fail_Less() {
        ArgumentUtils.assertGreaterOrEqualsThan(DOUBLE_NEGATIVE_VALUE, DOUBLE_ZERO, TEST_OBJECT_NAME);
    }

    @Test
    public void T58_assertLimits_Double() {
        ArgumentUtils.assertLimits(DOUBLE_ZERO, DOUBLE_NEGATIVE_VALUE, DOUBLE_POSITIVE_VALUE, TEST_OBJECT_NAME);
    }

    @Test
    public void T59_assertLimits_Double_EqualsLowerLimit() {
        ArgumentUtils.assertLimits(DOUBLE_NEGATIVE_VALUE, DOUBLE_NEGATIVE_VALUE, DOUBLE_POSITIVE_VALUE, TEST_OBJECT_NAME);
    }

    @Test
    public void T60_assertLimits_Double_EqualsUpperLimit() {
        ArgumentUtils.assertLimits(DOUBLE_POSITIVE_VALUE, DOUBLE_NEGATIVE_VALUE, DOUBLE_POSITIVE_VALUE, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T61_assertLimits_Double_Fail_Less() {
        ArgumentUtils.assertLimits(DOUBLE_NEGATIVE_VALUE, DOUBLE_ZERO, DOUBLE_POSITIVE_VALUE, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T62_assertLimits_Double_Fail_Greater() {
        ArgumentUtils.assertLimits(DOUBLE_POSITIVE_VALUE, DOUBLE_NEGATIVE_VALUE, DOUBLE_ZERO, TEST_OBJECT_NAME);
    }

    @Test
    public void T63_assertEquals() {
        ArgumentUtils.assertEquals(VALID_STRING, VALID_STRING, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T64_assertEquals_Fail() {
        ArgumentUtils.assertEquals(NULL_STRING, VALID_STRING, TEST_OBJECT_NAME);
    }

    @Test
    public void T65_assertNotEquals() {
        ArgumentUtils.assertNotEquals(NULL_STRING, VALID_STRING, TEST_OBJECT_NAME);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T66_assertNotEquals_Fail() {
        ArgumentUtils.assertNotEquals(NULL_STRING, NULL_STRING, TEST_OBJECT_NAME);
    }
}