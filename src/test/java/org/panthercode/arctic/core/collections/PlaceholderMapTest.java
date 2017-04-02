package org.panthercode.arctic.core.collections;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by architect on 08.03.17.
 */
@Test(sequential = true)
public class PlaceholderMapTest {

    private static String PLACEHOLDER = "#version#";

    private static String VALUE = "1.0";

    private static String KEY = "Key";

    private static String CONTENT = "path/" + PLACEHOLDER + "/file";

    private static PlaceholderMap MAP = new PlaceholderMap(PLACEHOLDER, VALUE);

    @BeforeClass
    public void before() {
        MAP.put(KEY, CONTENT);
    }

    @Test
    public void T001_PlaceholderMap_Constructor() {
        Assert.assertEquals(MAP.getPlaceholder(), PLACEHOLDER, "Actual value of placeholder");

        Assert.assertEquals(MAP.getValue(), VALUE, "Actual value");
    }

    @Test
    public void T002_PlaceholderMap_setPlaceHolder() {
        final String expectedValue = "Test";

        final PlaceholderMap actualMap = new PlaceholderMap(PLACEHOLDER, VALUE);

        actualMap.setPlaceholder(expectedValue);

        Assert.assertEquals(actualMap.getPlaceholder(), expectedValue, "Actual value of placeholder");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void T003_PlaceholderMap_setPlaceHolderFail_null() {
        final String value = null;

        final PlaceholderMap actualMap = new PlaceholderMap(PLACEHOLDER, VALUE);

        actualMap.setPlaceholder(value);
    }

    @Test
    public void T004_PlaceholderMap_setValue() {
        final String expectedValue = "Test";

        final PlaceholderMap actualMap = new PlaceholderMap(PLACEHOLDER, VALUE);

        actualMap.setValue(expectedValue);

        Assert.assertEquals(actualMap.getValue(), expectedValue, "Actual value");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void T005_PlaceholderMap_setValueFail_null() {
        final String expectedValue = null;

        final PlaceholderMap actualMap = new PlaceholderMap(PLACEHOLDER, VALUE);

        actualMap.setValue(expectedValue);
    }

    @Test
    public void T006_PlaceholderMap_get() {
        final String expectedValue = "path/" + VALUE + "/file";

        final String actualValue = MAP.get(KEY);

        Assert.assertNotNull(actualValue, "Actual value");

        Assert.assertEquals(actualValue, expectedValue, "Actual value");
    }

    @Test
    public void T007_PlaceholderMap_get_withValue() {
        final String value = "Test";

        final String expectedValue = "path/" + value + "/file";

        final String actualValue = MAP.get(KEY, value);

        Assert.assertNotNull(actualValue, "Actual value");

        Assert.assertEquals(actualValue, expectedValue, "Actual value");
    }

    @Test
    public void T008_PlaceholderMap_get_withPlaceholderAndValue() {
        final String value = "Test";

        final String placeholder = "path";

        final String expectedString = value + "/" + PLACEHOLDER + "/file";

        final String actualValue = MAP.get(KEY, placeholder, value);

        Assert.assertEquals(actualValue, expectedString, "Actual value");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void T009_PlaceholderMap_getFail_null() {
        final String value = null;

        MAP.get(KEY, value);
    }
}
