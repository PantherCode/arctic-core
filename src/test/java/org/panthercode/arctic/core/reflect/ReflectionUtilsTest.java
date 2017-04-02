package org.panthercode.arctic.core.reflect;

import org.panthercode.arctic.core.concurrent.semaphore.Semaphore;
import org.panthercode.arctic.core.processing.modules.RootModule;
import org.panthercode.arctic.core.processing.priority.Priority;
import org.panthercode.arctic.core.resources.AbstractCriticalResource;
import org.panthercode.arctic.core.resources.AbstractResource;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by architect on 08.03.17.
 */
public class ReflectionUtilsTest {

    public class TestClass1 {
        public void testFunction(String value) {
        }
    }

    @RootModule
    public class TestClass2 {
        public String testFunction(String value) {
            return value;
        }
    }

    private abstract class TestClass3 extends AbstractResource {
    }

    @RootModule
    private abstract class TestClass4 extends AbstractCriticalResource {
        public TestClass4(Semaphore<Priority> semaphore) {
            super(semaphore);
        }
    }

    private static final List<Class<?>> CLASS_LIST = new ArrayList<>();

    @BeforeClass
    public void before() {
        CLASS_LIST.add(TestClass1.class);

        CLASS_LIST.add(TestClass2.class);

        CLASS_LIST.add(TestClass3.class);

        CLASS_LIST.add(TestClass4.class);
    }

    @Test
    public void T001_ReflectionUtils_filterClassList() {
        final int expectedSize1 = 2;

        final int expectedSize2 = 1;

        final List<Class<?>> filteredList1 = ReflectionUtils.filterClassList(CLASS_LIST, AbstractResource.class);

        final List<Class<?>> filteredList2 = ReflectionUtils.filterClassList(CLASS_LIST, AbstractCriticalResource.class);

        Assert.assertNotNull(filteredList1, "Actual value of list");

        Assert.assertEquals(filteredList1.size(), expectedSize1, "Size of actual list");

        Assert.assertEquals(filteredList2.size(), expectedSize2, "Size of actual list");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void T002_ReflectionUtils_filterClassListFail_nullParameter() {
        ReflectionUtils.filterClassList(null, null);
    }

    @Test
    public void T003_ReflectionUtils_filterClassListByAnnotation() {
        final int expectedSize = 2;

        final List<Class<?>> filteredList = ReflectionUtils.filterClassListByAnnotation(CLASS_LIST, RootModule.class);

        Assert.assertNotNull(filteredList, "Actual value of list");

        Assert.assertEquals(filteredList.size(), expectedSize, "Size of actual list");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void T004_ReflectionUtils_filterClassListByAnnotationFail_nullParameter() {
        ReflectionUtils.filterClassListByAnnotation(null, null);
    }

    @Test
    public void T005_ReflectionUtils_invokeMethod() throws Exception {
        final TestClass1 object = new TestClass1();

        final String methodName = "testFunction";

        final String value = "Hello  World";

        ReflectionUtils.invokeMethod(object, methodName, value);
    }

    @Test
    public void T006_ReflectionUtils_invokeMethod() throws Exception {
        final TestClass2 object = new TestClass2();

        final String methodName = "testFunction";

        final String expectedValue = "Hello  World";

        final String actualValue = ReflectionUtils.invokeMethod(object, methodName, String.class, expectedValue);

        Assert.assertEquals(actualValue, expectedValue, "Actual value");
    }

    @Test
    public void T007_ReflectionUtils_isAnnotated() {
        Assert.assertTrue(ReflectionUtils.isAnnotated(TestClass2.class, RootModule.class), "Class is annotated");
    }

    @Test
    public void T008_ReflectionUtils_isAnnotatedFail() {
        Assert.assertFalse(ReflectionUtils.isAnnotated(TestClass1.class, RootModule.class), "Class is annotated");
    }
}
