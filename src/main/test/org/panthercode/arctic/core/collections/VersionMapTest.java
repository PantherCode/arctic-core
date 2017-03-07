package org.panthercode.arctic.core.collections;

import org.panthercode.arctic.core.helper.version.Version;
import org.panthercode.arctic.core.helper.version.Versionable;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by architect on 06.03.17.
 */
@Test(singleThreaded = true, sequential = true)
public class VersionMapTest {

    private static final String KEY_1 = "Test Key 1";

    private static final String KEY_2 = "Test Key 2";

    private static final String KEY_3 = "Test Key 3";

    private static final Version VERSION_1 = new Version(0, 0);

    private static final Version VERSION_2 = new Version(1, 0);

    private static final Version VERSION_3 = new Version(1, 2, 3, 4);

    private static class TestVersionClass implements Versionable {
        private final Version version;

        TestVersionClass(Version version) {
            this.version = version;
        }

        @Override
        public Version version() {
            return this.version;
        }
    }

    private static final TestVersionClass TEST_VERSION_CLASS_1 = new TestVersionClass(VERSION_1);

    private static final TestVersionClass TEST_VERSION_CLASS_2 = new TestVersionClass(VERSION_2);

    private static final TestVersionClass TEST_VERSION_CLASS_3 = new TestVersionClass(VERSION_3);

    private static final VersionMap<String, TestVersionClass> MAP = new VersionMap<>();

    @BeforeMethod
    public void before() {
        MAP.put(KEY_1, TEST_VERSION_CLASS_1);
        MAP.put(KEY_2, TEST_VERSION_CLASS_2);
        MAP.put(KEY_3, TEST_VERSION_CLASS_3);
    }

    @AfterMethod
    public void after() {
        MAP.clear();
    }

    @Test
    public void T001_VersionMap_size() {
        final int expectedSize = 3;

        Assert.assertEquals(MAP.size(), expectedSize, "Size of map");
    }

    @Test
    public void T002_VersionMap_isEmpty() {
        final VersionMap<String, TestVersionClass> actualMap = new VersionMap<>();

        Assert.assertTrue(actualMap.isEmpty(), "Map is empty");

        Assert.assertFalse(MAP.isEmpty(), "Map is empty");
    }

    @Test
    public void T003_VersionMap_containsKey() {
        Assert.assertTrue(MAP.containsKey(KEY_2), "The map contains given key");
    }

    @Test
    public void T004_VersionMap_containsKeyFail_unknownKey() {
        final String key = "Hello World";

        Assert.assertFalse(MAP.containsKey(key), "The map contains given key");
    }

    @Test
    public void T005_VersionMap_containsValue() {
        Assert.assertTrue(MAP.containsValue(TEST_VERSION_CLASS_2), "The map contains given value");
    }

    @Test
    public void T006_VersionMap_containsValueFail_unknownValue() {
        final Version version = new Version(9, 8, 7, 6);

        final TestVersionClass value1 = new TestVersionClass(version);

        final TestVersionClass value2 = new TestVersionClass(VERSION_3);

        Assert.assertFalse(MAP.containsValue(value1), "The map contains given value");

        Assert.assertFalse(MAP.containsValue(value2), "The map contains given value");
    }

    @Test
    public void T007_VersionMap_contains() {
        Assert.assertTrue(MAP.contains(KEY_3, VERSION_3), "The map contains given element");
    }

    @Test
    public void T008_VersionMap_containsFail_unknownKeyAndVersion() {
        final String key = "Hello World";

        Assert.assertFalse(MAP.contains(key, VERSION_1), "The map contains given element");

        Assert.assertFalse(MAP.contains(KEY_1, VERSION_2), "The map contains given element");
    }

    @Test
    public void T009_VersionMap_get() {
        final Version version = new Version(2, 0);

        final TestVersionClass value = new TestVersionClass(version);

        Assert.assertEquals(MAP.get(KEY_1), TEST_VERSION_CLASS_1, "Value from map");

        MAP.put(KEY_1, value);

        Assert.assertEquals(MAP.get(KEY_1), value, "Value from map");

        MAP.remove(KEY_1, value);
    }

    @Test
    public void T010_VersionMap_getFail_unknownKey() {
        final String key = "Hello World";

        Assert.assertNull(MAP.get(key), "Value from map");
    }

    @Test
    public void T011_VersionMap_get() throws Exception {
        Assert.assertEquals(MAP.get(KEY_1, VERSION_1), TEST_VERSION_CLASS_1, "Value from map");
    }

    @Test
    public void T012_VersionMap_getFail() {
        Assert.assertNull(MAP.get(KEY_1, VERSION_2), "Value from map");
    }

    @Test
    public void T012_VersionMap_put() {
        final String key = "Hello World";

        final Version version = new Version(2, 0);

        final TestVersionClass value = new TestVersionClass(version);

        final int expectedSize = 4;

        MAP.put(key, value);

        Assert.assertEquals(MAP.get(key, version), value, "Value from map");

        Assert.assertEquals(MAP.get(key), value);

        Assert.assertEquals(MAP.size(), expectedSize, "Size of map");

        MAP.remove(key, version);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void T013_VersionMap_putFail_nullValue() {
        final TestVersionClass value = null;

        MAP.put(KEY_1, value);
    }

    @Test
    public void T014_VersionMap_remove() {
        final String key = "Hello World";

        final Version version1 = new Version(2, 0);

        final Version version2 = new Version(3, 0);

        final TestVersionClass value1 = new TestVersionClass(version1);

        final TestVersionClass value2 = new TestVersionClass(version2);

        MAP.put(key, value1);

        MAP.put(key, value2);

        Assert.assertTrue(MAP.contains(key, version1), "Map contains given element");

        Assert.assertTrue(MAP.contains(key, version2), "Map contains given element");

        Assert.assertEquals(MAP.remove(key), value2, "Value from map");

        Assert.assertFalse(MAP.contains(key, version1), "Map contains given element");

        Assert.assertFalse(MAP.contains(key, version2), "Map contains given element");

        Assert.assertFalse(MAP.containsKey(key), "Map contains key");
    }

    @Test
    public void T015_VersionMap_removeFail_unknownKey() {
        final String key = "Hello World";

        Assert.assertNull(MAP.remove(key), "Value from map");
    }

    @Test
    public void T016_VersionMap_remove() {
        final Version version = new Version(2, 0);

        final TestVersionClass value = new TestVersionClass(version);

        MAP.put(KEY_3, value);

        Assert.assertTrue(MAP.contains(KEY_3, version), "Map contains given element");

        Assert.assertEquals(MAP.remove(KEY_3, version), value, "Value from map");

        Assert.assertFalse(MAP.contains(KEY_3, version), "Map contains given element");
    }

    @Test
    public void T017_VersionMap_removeFail_unknownKeyAndVersion() {
        final String key = "Hello World";

        final Version version = new Version(2, 0);

        Assert.assertNull(MAP.remove(key, VERSION_1), "Value from map");

        Assert.assertNull(MAP.remove(KEY_1, version), "Value from map");
    }

    @Test
    public void T018_VersionMap_putAll() {
        final String key1 = "Local Key 1";

        final String key2 = "Local Key 2";

        final Version version1 = new Version(2, 0);

        final Version version2 = new Version(3, 0);

        final TestVersionClass value1 = new TestVersionClass(version1);

        final TestVersionClass value2 = new TestVersionClass(version2);

        final Map<String, TestVersionClass> map = new HashMap<>();

        final int expectedSize = 5;

        map.put(key1, value1);

        map.put(key2, value2);

        MAP.putAll(map);

        Assert.assertEquals(MAP.size(), expectedSize, "Size of map");

        Assert.assertTrue(MAP.contains(key1, version1), "Map contains given element");

        Assert.assertTrue(MAP.contains(key2, version2), "Map contains given element");

        MAP.remove(key1, value1);

        MAP.remove(key2, value2);
    }

    @Test
    public void T018_VersionMap_putAllFail_nullValue() {
        final String key1 = "Local key 1";

        final String key2 = null;

        final Version version = new Version(2, 0);

        final TestVersionClass value1 = null;

        final TestVersionClass value2 = new TestVersionClass(version);

        final int expectedSize = 3;

        final Map<String, TestVersionClass> map = new HashMap<>();

        map.put(key1, value1);

        map.put(key2, value2);

        MAP.putAll(map);

        Assert.assertEquals(MAP.size(), expectedSize, "Size of map");
    }

    @Test
    public void T019_VersionMap_clear() {
        final int expectedSize = 0;

        MAP.clear();

        Assert.assertEquals(MAP.size(), expectedSize, "Size of map");

        MAP.put(KEY_1, TEST_VERSION_CLASS_1);
        MAP.put(KEY_2, TEST_VERSION_CLASS_2);
        MAP.put(KEY_3, TEST_VERSION_CLASS_3);
    }

    @Test
    public void T020_VersionMap_keySet() {
        final Set<String> actualKeySet = MAP.keySet();

        final int expectedSize = 3;

        Assert.assertNotNull(actualKeySet, "Key set from map");

        Assert.assertEquals(actualKeySet.size(), expectedSize, "Size of set");
    }

    @Test
    public void T021_VersionMap_values() {
        final Version version1 = new Version(2, 0);

        final Version version2 = new Version(3, 0);

        final TestVersionClass value1 = new TestVersionClass(version1);

        final TestVersionClass value2 = new TestVersionClass(version2);

        final int expectedSize = 5;

        MAP.put(KEY_1, value1);

        MAP.put(KEY_3, value2);

        final Collection<TestVersionClass> actualCollection = MAP.values();

        Assert.assertNotNull(actualCollection, "Value collection from map");

        Assert.assertEquals(actualCollection.size(), expectedSize, "Size of value collection");

        MAP.remove(KEY_1, value1);

        MAP.remove(KEY_3, value2);
    }

    @Test
    public void T022_VersionMap_entrySet() {
        final Version version1 = new Version(2, 0);

        final Version version2 = new Version(3, 0);

        final TestVersionClass value1 = new TestVersionClass(version1);

        final TestVersionClass value2 = new TestVersionClass(version2);

        final int expectedSize = 5;

        MAP.put(KEY_1, value1);

        MAP.put(KEY_3, value2);

        final Set<Map.Entry<String, TestVersionClass>> actualEntrySet = MAP.entrySet();

        Assert.assertNotNull(actualEntrySet, "Entry set from map");

        Assert.assertEquals(actualEntrySet.size(), expectedSize, "Size of entry set");

        MAP.remove(KEY_1, value1);

        MAP.remove(KEY_3, value2);
    }
}
