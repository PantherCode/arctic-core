package org.panthercode.arctic.core.helper.unit;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by architect on 01.12.16.
 */
public class BinaryMemoryUnitTest {

    public static final double TEST_DOUBLE_ONE = 1.0;

    public static final double TEST_DOUBLE_VALUE = 1024.0;

    public static final BinaryMemoryUnit TEST_UNIT_MEBIBYTE = BinaryMemoryUnit.MEBIBYTE;

    public static final BinaryMemoryUnit TEST_UNIT_GIBIBYTE = BinaryMemoryUnit.GIBIBYTE;

    public static final BinaryMemoryUnit TEST_UNIT_TEBIBYTE = BinaryMemoryUnit.TEBIBYTE;

    @Test
    public void T01_BinaryMemoryUnit_valueOf() {
        BinaryMemoryUnit actualUnit = BinaryMemoryUnit.valueOf(TEST_DOUBLE_VALUE, TEST_UNIT_MEBIBYTE);

        Assert.assertEquals(actualUnit, TEST_UNIT_GIBIBYTE, "Units are equal.");
    }

    @Test
    public void T02_BinaryMemoryUnit_toMebiByte() {
        double actualValue = TEST_UNIT_TEBIBYTE.toPebiByte(TEST_DOUBLE_VALUE);

        Assert.assertEquals(actualValue, TEST_DOUBLE_ONE, "Values are equal.");
    }
}
