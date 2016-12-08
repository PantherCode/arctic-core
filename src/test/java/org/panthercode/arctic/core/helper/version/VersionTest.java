package org.panthercode.arctic.core.helper.version;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test cases for Version class
 *
 * @author PantherCode
 */
public class VersionTest {

    @VersionInfo(major = 1, minor = 5, build = 9)
    public static class TestClass {
    }

    public static final String TEST_VERSION_STRING_SHORT = "1.0";

    public static final String TEST_VERSION_STRING_LONG = "1.2.3.4";

    public static final Version TEST_VERSION_SHORT = new Version(1, 0);

    public static final Version TEST_VERSION_LONG = new Version(1, 2, 3, 4);

    public static final Version TEST_VERSION_CLASS = new Version(1, 5, 0, 9);

    @Test
    public void T01_Version_parse() {
        Version actualVersion = Version.parse(TEST_VERSION_STRING_LONG);

        boolean result = TEST_VERSION_LONG.equals(actualVersion);

        Assert.assertTrue(result, "Versions are equal.");
    }

    @Test
    public void T02_Version_isAnnotated() {
        TestClass testClass = new TestClass();

        Assert.assertTrue(Version.isAnnotated(testClass.getClass()), "Class is annotated.");
    }

    @Test
    public void T03_Version_fromAnnotation() {
        TestClass testClass = new TestClass();

        Version actualVersion = Version.fromAnnotation(testClass.getClass());

        boolean result = TEST_VERSION_CLASS.equals(actualVersion);

        Assert.assertTrue(result, "Versions are equal.");
    }
}
