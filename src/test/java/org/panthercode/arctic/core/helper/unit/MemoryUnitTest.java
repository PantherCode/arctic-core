package org.panthercode.arctic.core.helper.unit;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by architect on 01.12.16.
 */
public class MemoryUnitTest {

    public static final double TEST_DOUBLE_ONE = 1.0;

    public static final double TEST_DOUBLE_VALUE = 1000.0;

    public static final MemoryUnit TEST_UNIT_PETABYTE = MemoryUnit.PETABYTE;

    public static final MemoryUnit TEST_UNIT_ZETTABYTE = MemoryUnit.ZETTABYTE;

    public static final MemoryUnit TEST_UNIT_YOTTABYTE = MemoryUnit.YOTTABYTE;

    @Test
    public void T01_MemoryUnit_valueOf() {
        MemoryUnit actualUnit = MemoryUnit.valueOf(TEST_DOUBLE_VALUE, TEST_UNIT_ZETTABYTE);

        Assert.assertEquals(actualUnit, TEST_UNIT_YOTTABYTE, "Units are equal.");
    }

    @Test
    public void T02_MemoryUnit_toExaByte() {
        double actualValue = TEST_UNIT_PETABYTE.toExaByte(TEST_DOUBLE_VALUE);

        Assert.assertEquals(actualValue, TEST_DOUBLE_ONE, "Values are equal.");
    }
}
