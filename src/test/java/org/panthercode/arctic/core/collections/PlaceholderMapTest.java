package org.panthercode.arctic.core.collections;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test cases for PlaceholderMap class
 *
 * @author PantherCode
 */
public class PlaceholderMapTest {

    private static String TEST_KEY = "Test Key";

    private static String TEST_PLACEHOLDER = "#version#";

    private static String TEST_VALUE = "1.0";

    private static String TEST_STRING = "path/to/" + TEST_PLACEHOLDER + "/resource";

    @Test
    public void T01_PlaceholderMap_get() {
        PlaceholderMap map = new PlaceholderMap(TEST_PLACEHOLDER, TEST_VALUE);

        String actualString = "";
        String expectedString = "path/to/1.0/resource";

        map.put(TEST_KEY, TEST_STRING);

        actualString = map.get(TEST_KEY);

        Assert.assertEquals(actualString, expectedString, "Value of string");
    }

    @Test
    public void T02_PlaceholderMap_get_Value() {
        PlaceholderMap map = new PlaceholderMap(TEST_PLACEHOLDER, TEST_VALUE);

        String actualValue = "other";

        String actualString = "";
        String expectedString = "path/to/other/resource";

        map.put(TEST_KEY, TEST_STRING);

        actualString = map.get(TEST_KEY, actualValue);

        Assert.assertEquals(actualString, expectedString, "Value of string");
    }

    @Test
    public void T03_PlaceholderMap_get_ValueAndPlaceholder() {
        PlaceholderMap map = new PlaceholderMap(TEST_PLACEHOLDER, TEST_VALUE);

        String actualValue = "P47H";
        String actualPlaceholder = "path";

        String actualString = "";
        String expectedString = "P47H/to/#version#/resource";

        map.put(TEST_KEY, TEST_STRING);

        actualString = map.get(TEST_KEY, actualPlaceholder, actualValue);

        Assert.assertEquals(actualString, expectedString, "Value of string");
    }

}
