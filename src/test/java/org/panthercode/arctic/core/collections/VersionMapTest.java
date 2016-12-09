package org.panthercode.arctic.core.collections;

import org.apache.commons.io.input.TeeInputStream;
import org.panthercode.arctic.core.helper.version.Version;
import org.panthercode.arctic.core.helper.version.VersionInfo;
import org.panthercode.arctic.core.helper.version.Versionable;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.TestInstance;

/**
 * Test cases for VersionMap class
 *
 * @author PantherCode
 */
public class VersionMapTest {

    private static String TEST_KEY_1 = "Test Key 1";

    private static String TEST_KEY_2 = "Test Key 2";

    private static Version TEST_VERSION = new Version(1,0);

    @VersionInfo(major = 1,minor = 2,build = 3,revision = 4)
    class TestClass implements Versionable{
        private Version version;

        TestClass(){
            version = Version.fromAnnotation(this.getClass());
        }

        TestClass(Version version){
            this.version = version;
        }

        @Override
        public Version version() {
            return this.version;
        }
    }

    @VersionInfo(major = 1,minor = 2,build = 3,revision = 4)
    class OtherTestClass extends TestClass implements Versionable{
        private Version version;

        OtherTestClass(){
            super();
        }

        OtherTestClass(Version version){
            super(version);
        }
    }

    @Test
    public void T01_VersionMap_get(){
        VersionMap<String, TestClass> map = new VersionMap<>();

        TestClass actualTestClass = null;
        TestClass expectedTestClass = new TestClass();

        map.put(TEST_KEY_1, expectedTestClass);

        actualTestClass = map.get(TEST_KEY_1);

        Assert.assertEquals(actualTestClass, expectedTestClass, "object with version");
    }

    @Test
    public void T02_VersionMap_size(){
        VersionMap<String, TestClass> map = new VersionMap<>();

        TestClass testClass1 = new TestClass();
        TestClass testClass2 = new TestClass(new Version(1,0));

        int actualCount = 0;
        int expectedCount1 = 2;
        int expectedCount2 = 3;

        map.put(TEST_KEY_1, testClass1);
        map.put(TEST_KEY_1, testClass2);

        actualCount = map.size();

        Assert.assertEquals(actualCount, expectedCount1, "size of map");

        map.put(TEST_KEY_2, testClass1);

        actualCount = map.size();

        Assert.assertEquals(actualCount, expectedCount2, "size of map");
    }

    @Test
    public void T03_VersionMap_size_Override(){
        VersionMap<String, TestClass> map = new VersionMap<>();

        Version testVersion = new Version(1,0);

        TestClass testClass1 = new TestClass();
        TestClass testClass2 = new TestClass(testVersion);

        OtherTestClass testClass3 = new OtherTestClass();
        OtherTestClass testClass4 = new OtherTestClass(testVersion);

        int actualCount = 0;
        int expectedCount1 = 2;
        int expectedCount2 = 3;

        map.put(TEST_KEY_1, testClass1);
        map.put(TEST_KEY_1, testClass2);
        map.put(TEST_KEY_1, testClass4);

        actualCount = map.size();

        Assert.assertEquals(actualCount, expectedCount1, "size of map");

        map.put(TEST_KEY_2, testClass1);
        map.put(TEST_KEY_2, testClass3);

        actualCount = map.size();

        Assert.assertEquals(actualCount, expectedCount2, "size of map");
    }

    @Test
    public void T04_VersionMap_isEmpty(){
        VersionMap<String, TestClass> map = new VersionMap<>();

        TestClass testClass = new TestClass();

        Assert.assertTrue(map.isEmpty(), "The map is empty");

        map.put(TEST_KEY_1, testClass);

        Assert.assertFalse(map.isEmpty(), "The map is empty");
    }

    @Test
    public void T05_VersionMap_containsKey(){
        VersionMap<String, TestClass> map = new VersionMap<>();

        TestClass testClass = new TestClass();

        Assert.assertFalse(map.containsKey(TEST_KEY_1), "The map contains key");

        map.put(TEST_KEY_1, testClass);

        Assert.assertTrue(map.containsKey(TEST_KEY_1), "The map contains key");
    }

    @Test
    public void T06_VersionMap_containsValue(){
        VersionMap<String, TestClass> map = new VersionMap<>();

        TestClass testClass1 = new TestClass();
        TestClass testClass2 = new TestClass(TEST_VERSION);

        Assert.assertFalse(map.containsValue(testClass1), "The map contains value.");

        map.put(TEST_KEY_1, testClass1);
        map.put(TEST_KEY_2, testClass2);

        Assert.assertTrue(map.containsKey(testClass1), "The map contains value");
        Assert.assertTrue(map.containsValue(testClass2), "The map contains value");
    }

    @Test
    public void T07_VersionMap_get_Version(){
        VersionMap<String, TestClass> map = new VersionMap<>();

        TestClass testClass = new TestClass(TEST_VERSION);

        TestClass actualClass = null;

        Version version = Version.fromAnnotation(TestClass.class);

        map.put(TEST_KEY_1, testClass);

        actualClass = map.get(TEST_KEY_1, version);

        Assert.assertNull(actualClass, "Actual value of class");

        actualClass = map.get(TEST_KEY_1, TEST_VERSION);

        Assert.assertNotNull(actualClass, "Actual value of class");
    }


    @Test
    public void T08_VersionMap_remove(){
        VersionMap<String, TestClass> map = new VersionMap<>();

        TestClass testClass = new TestClass();

        Assert.assertTrue(map.isEmpty(), "The map is empty");

        map.put(TEST_KEY_1, testClass);

        Assert.assertFalse(map.isEmpty(), "The map is empty");

        map.remove(TEST_KEY_1);

        Assert.assertTrue(map.isEmpty(), "The map is empty");
    }

    @Test
    public void T09_VersionMap_remove_Version(){
        VersionMap<String, TestClass> map = new VersionMap<>();

        TestClass testClass1 = new TestClass();
        TestClass testClass2 = new TestClass(TEST_VERSION);

        int acutalCount = 0;
        int expectedCount = 1;

        Assert.assertTrue(map.isEmpty(), "The map is empty");

        map.put(TEST_KEY_1, testClass1);
        map.put(TEST_KEY_1, testClass2);

        map.remove(TEST_KEY_1, TEST_VERSION);

        acutalCount  = map.size();

        Assert.assertEquals(acutalCount, expectedCount, "Size of map");
    }


}
