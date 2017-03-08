package org.panthercode.arctic.core.collections.helper;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.collections.VersionMap;
import org.panthercode.arctic.core.helper.version.Version;
import org.panthercode.arctic.core.helper.version.Versionable;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by architect on 06.03.17.
 */
@Test(sequential = true)
public class DefaultAllocatorTest {

    private static final String KEY_1 = "Key 1";

    private static final String KEY_2 = "Key 2";

    private static final Version VERSION_1 = new Version(1, 0);

    private static final Version VERSION_2 = new Version(1, 2, 3, 4);

    private static final Version VERSION_3 = new Version(2, 0);

    private static class TestClass implements Versionable {

        private final Version version;

        public TestClass(Version version) {
            this.version = ArgumentUtils.checkNotNull(version, "version");
        }

        @Override
        public Version version() {
            return this.version;
        }
    }

    private static final TestClass TEST_CLASS_1 = new TestClass(VERSION_1);

    private static final TestClass TEST_CLASS_2 = new TestClass(VERSION_2);

    private static final TestClass TEST_CLASS_3 = new TestClass(VERSION_3);

    private static final VersionMap<String, TestClass> MAP = new VersionMap<>();

    private static final Allocator<String, TestClass> ALLOCATOR = new DefaultAllocator<>(MAP);

    @BeforeClass
    public void before() {
        MAP.put(KEY_1, TEST_CLASS_1);

        MAP.put(KEY_2, TEST_CLASS_2);

        MAP.put(KEY_2, TEST_CLASS_3);
    }

    @Test
    public void T001_DefaultAllocator_contains() {
        final String invalidKey = "unknown";

        final Version invalidVersion = new Version();

        Assert.assertTrue(ALLOCATOR.contains(KEY_1), "Map contains given key");

        Assert.assertFalse(ALLOCATOR.contains(invalidKey), "Map contains given key");

        Assert.assertTrue(ALLOCATOR.contains(KEY_2, VERSION_2), "Map contains given element");

        Assert.assertFalse(ALLOCATOR.contains(KEY_2, invalidVersion), "Map contains given element");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void T002_DefaultAllocator_containsFail_null() {
        ALLOCATOR.contains(KEY_1, null);
    }

    @Test
    public void T003_DefaultAllocator_allocate() throws Exception {
        Assert.assertEquals(ALLOCATOR.allocate(KEY_2), TEST_CLASS_3, "Allocated object from map");

        Assert.assertEquals(ALLOCATOR.allocate(KEY_2, VERSION_2), TEST_CLASS_2, "Allocation object from map");
    }

    @Test(expectedExceptions = AllocationException.class)
    public void T004_DefaultAllocation_allocateFail_invalidKey() throws Exception {
        final String invalidKey = "unknown";

        ALLOCATOR.allocate(invalidKey);
    }

    @Test(expectedExceptions = AllocationException.class)
    public void T005_DefaultAllocator_allocateFail_invalidVersion() throws Exception {
        final Version invalidVersion = new Version();

        ALLOCATOR.allocate(KEY_2, invalidVersion);
    }
}
