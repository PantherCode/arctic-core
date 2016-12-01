package org.panthercode.arctic.core.helper.identity;

import org.panthercode.arctic.core.helper.identity.annotation.IdentityInfo;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by architect on 01.12.16.
 */
public class IdentityTest {

    public static final String TEST_NAME = "Testname";

    public static final String TEST_GROUP = "Testgroup";

    public static final String UNKNOWN = "unknown";

    public static final String DEFAULT = "default";

    @IdentityInfo()
    public static class AnonymousTestClass {
    }

    @IdentityInfo(name = TEST_NAME, group = TEST_GROUP)
    public static class TestClass {
    }

    @Test
    public void T01_Identity_generate() {
        Identity identity = Identity.generate(TEST_NAME, TEST_GROUP);

        Assert.assertEquals(identity.getName(), TEST_NAME, "Name of identity");

        Assert.assertEquals(identity.getGroup(), TEST_GROUP, "Group name of identity");
    }

    @Test
    public void T02_Identity_generate_anonymous() {
        Identity identity = Identity.generate();

        Assert.assertEquals(identity.getName(), UNKNOWN, "Name of identity");

        Assert.assertEquals(identity.getGroup(), DEFAULT, "Group name of identity");
    }

    @Test
    public void T03_Identity_isAnnotated() {
        TestClass testClass = new TestClass();

        boolean isAnnotated = Identity.isAnnotated(testClass);

        Assert.assertTrue(isAnnotated, "Class is annotated.");
    }

    @Test
    public void T04_Identity_generate_fromAnnotation() {
        TestClass testClass = new TestClass();

        Identity identity = Identity.fromAnnotation(testClass);

        Assert.assertEquals(identity.getName(), TEST_NAME, "Name of identity");

        Assert.assertEquals(identity.getGroup(), TEST_GROUP, "Group name of identity");
    }

    @Test
    public void T05_Identity_generate_fromAnnotationAnonymous() {
        AnonymousTestClass testClass = new AnonymousTestClass();

        Identity identity = Identity.fromAnnotation(testClass);

        Assert.assertEquals(identity.getName(), UNKNOWN, "Name of identity");

        Assert.assertEquals(identity.getGroup(), DEFAULT, "Group name of identity");
    }

    @Test
    public void T06_Identity_match() {
        TestClass testClass = new TestClass();

        Identity expectedIdentity = Identity.fromAnnotation(testClass);

        Identity actualIdentity = Identity.generate(TEST_NAME, TEST_GROUP);

        boolean result = expectedIdentity.match(actualIdentity);

        Assert.assertTrue(result, "Identities  have same name and group name");
    }

    @Test
    public void T07_Identity_copy() {
        TestClass testClass = new TestClass();

        Identity expectedIdentity = Identity.fromAnnotation(testClass);

        Identity actualIdentity = expectedIdentity.copy();

        boolean result = expectedIdentity.equals(actualIdentity);

        Assert.assertTrue(result, "Identities are equal.");
    }

    @Test
    public void T08_Identity_setName() {
        Identity identity = Identity.generate();

        Assert.assertEquals(identity.getName(), UNKNOWN, "Name of identity");

        identity.setName(TEST_NAME);

        Assert.assertEquals(identity.getName(), TEST_NAME, "Name of identity");
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void T09_Identity_setNameFailed_freeze() {
        Identity identity = Identity.generate();

        Assert.assertTrue(identity.canModify(), "Identity can modify.");

        identity.freeze();

        Assert.assertFalse(identity.canModify(), "Identity can modify");

        identity.setName(TEST_NAME);
    }

    @Test
    public void T10_Identity_setName_unfreeze() {
        Identity identity = Identity.generate();

        Assert.assertTrue(identity.canModify(), "Identity can modify.");

        identity.freeze();

        Assert.assertFalse(identity.canModify(), "Identity can modify");

        identity.unfreeze();

        identity.setName(TEST_NAME);

        Assert.assertEquals(identity.getName(), TEST_NAME, "Name of identity");
    }
}
