package org.panthercode.arctic.core.reflect;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by architect on 08.03.17.
 */
@Test(sequential = true)
public class ClassBuilderTest {

    public static class TestPersonClass {

        private int age;

        private String name;

        public TestPersonClass(int age, String name) {
            this.age = age;

            this.name = name;
        }

        public int getAge() {
            return this.age;
        }

        public String getName() {
            return this.name;
        }
    }

    @Test
    public void T001_ClassBuilder_create() throws Exception {
        final int expectedAge = 42;

        final String expectedName = "Herbert";

        final TestPersonClass value = ClassBuilder.create(TestPersonClass.class)
                .setArguments(expectedAge, expectedName).build();

        Assert.assertEquals(value.getName(), expectedName, "Actual name");

        Assert.assertEquals(value.getAge(), expectedAge, "Actual age");
    }

    @Test(expectedExceptions = NoSuchMethodException.class)
    public void T002_ClassBuilder_createFail_invalidParameters() throws Exception {
        final int expectedAge = 42;

        ClassBuilder.create(TestPersonClass.class).setArguments(expectedAge).build();
    }
}
