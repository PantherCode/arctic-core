package org.panthercode.arctic.core.helper;

import org.panthercode.arctic.core.helper.identity.Identity;
import org.panthercode.arctic.core.helper.identity.IdentityInfo;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by architect on 07.03.17.
 */
@Test(sequential = true)
public class IdentityTest {

    private static final String NAME = "Testname";

    private static final String GROUP = "Testgroup";

    private static final String DEFAULT = "default";

    private static final String UNKNOWN = "unknown";

    @IdentityInfo(name = NAME, group = GROUP)
    private class TestIdentityClass {
    }

    private class TestClass {
    }

    @Test
    public void T001_Identity_generate() {
        final Identity identity1 = Identity.generate();

        final Identity identity2 = Identity.generate(NAME);

        final Identity identity3 = Identity.generate(NAME, GROUP);

        Assert.assertNotNull(identity1, "Identity object");

        Assert.assertEquals(identity1.getName(), UNKNOWN, "Name of identity object");

        Assert.assertEquals(identity1.getGroup(), DEFAULT, "Group name of identity object");

        Assert.assertEquals(identity2.getName(), NAME, "Name of identity object");

        Assert.assertEquals(identity2.getGroup(), DEFAULT, "Group name of identity object");

        Assert.assertEquals(identity3.getName(), NAME, "Name of identity object");

        Assert.assertEquals(identity3.getGroup(), GROUP, "Group name of identity object");
    }

    @Test
    public void T002_Identity_generate_nullValue() {
        final Identity identity1 = Identity.generate(null);

        final Identity identity2 = Identity.generate(null, null);

        Assert.assertNotNull(identity1, "Identity object");

        Assert.assertEquals(identity1.getName(), UNKNOWN, "Name of identity object");

        Assert.assertEquals(identity1.getGroup(), DEFAULT, "Group name of identity object");

        Assert.assertEquals(identity2.getName(), UNKNOWN, "Name of identity object");

        Assert.assertEquals(identity2.getGroup(), DEFAULT, "Group name of identity object");
    }

    @Test
    public void T003_Identity_setName() {
        final Identity identity = Identity.generate();

        identity.setName(NAME);

        Assert.assertEquals(identity.getName(), NAME, "Name of identity object");

        identity.setName(null);

        Assert.assertEquals(identity.getName(), UNKNOWN, "Name of identity object");
    }

    @Test
    public void T004_Identity_setGroup() {
        final Identity identity = Identity.generate();

        identity.setGroup(GROUP);

        Assert.assertEquals(identity.getGroup(), GROUP, "Group name of identity object");

        identity.setGroup(null);

        Assert.assertEquals(identity.getGroup(), DEFAULT, "Group name of identity object");
    }

    @Test
    public void T005_Identity_asShortString() {
        final String expectedString = "name = " + NAME + ", group = " + GROUP;

        final Identity identity = Identity.generate(NAME, GROUP);

        Assert.assertEquals(identity.asShortString(), expectedString, "Identity object as string");
    }

    @Test
    public void T006_Identity_copy() {
        final Identity identity1 = Identity.generate(NAME, GROUP);

        final Identity identity2 = identity1.copy();

        Assert.assertEquals(identity1.getName(), identity2.getName(), "Name of identity object");

        Assert.assertEquals(identity1.getGroup(), identity2.getGroup(), "Group name of identity object");
    }

    @Test
    public void T007_Identity_isAnnotated() {
        Assert.assertTrue(Identity.isAnnotated(TestIdentityClass.class), "Class is annotated");

        Assert.assertFalse(Identity.isAnnotated(TestClass.class), "Class is annotated");
    }

    @Test
    public void T008_Identity_fromAnnotation() {
        final Identity identity = Identity.fromAnnotation(TestIdentityClass.class);

        Assert.assertNotNull(identity, "Value of identity object");

        Assert.assertEquals(identity.getName(), NAME, "Name of identity object");

        Assert.assertEquals(identity.getGroup(), GROUP, "Group name of identity object");
    }

    @Test
    public void T009_Identity_fromAnnotationFail() {
        final Identity identity = Identity.fromAnnotation(TestClass.class);

        Assert.assertNotNull(identity, "Value of identity object");

        Assert.assertEquals(identity.getName(), UNKNOWN, "Name of identity object");

        Assert.assertEquals(identity.getGroup(), DEFAULT, "Group name of identity object");
    }

    @Test
    public void T010_Identity_match() {
        Identity identity1 = Identity.generate();

        Identity identity2 = Identity.generate(NAME, GROUP);

        Identity identity3 = identity2.copy();

        Identity identity4 = null;

        Assert.assertTrue(identity1.match(identity1), "Result of comparision between identity objects");

        Assert.assertFalse(identity1.match(identity2), "Result of comparision between identity objects");

        Assert.assertTrue(identity2.match(identity3), "Result of comparision between identity objects");

        Assert.assertFalse(identity3.match(identity4), "Result of comparision between identity objects");
    }

    @Test
    public void T011_Identity_equals() {
        Identity identity1 = Identity.generate();

        Identity identity2 = Identity.generate(NAME, GROUP);

        Identity identity3 = identity2.copy();

        Identity identity4 = null;

        Assert.assertTrue(identity1.equals(identity1), "Result of comparision between identity objects");

        Assert.assertFalse(identity1.equals(identity2), "Result of comparision between identity objects");

        Assert.assertTrue(identity2.equals(identity3), "Result of comparision between identity objects");

        Assert.assertFalse(identity3.equals(identity4), "Result of comparision between identity objects");
    }
}
