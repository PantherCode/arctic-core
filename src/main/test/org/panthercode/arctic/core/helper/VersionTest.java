package org.panthercode.arctic.core.helper;

import org.panthercode.arctic.core.helper.version.Version;
import org.panthercode.arctic.core.helper.version.VersionField;
import org.panthercode.arctic.core.helper.version.VersionInfo;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by architect on 03.03.17.
 * <p>
 * major.minor[.build[.revision]]
 */
public class VersionTest {

    private static final Version VERSION_1 = new Version();

    private static final Version VERSION_2 = new Version(1, 2);

    private static final Version VERSION_3 = new Version(1, 2, 0, 3);

    private static final Version VERSION_4 = new Version(1, 2, 3, 4);

    @VersionInfo(major = 1, minor = 2, build = 3, revision = 4)
    private static class TestVersionClass {
    }

    private static class TestClass {
    }

    @Test
    public void T001_Version_Constructor() {
        final int expectedMajorNumber = 1;

        final int expectedMinorNumber = 2;

        final int expectedBuildNumber = 3;

        final int expectedRevisionNumber = 4;

        final Version actualVersion = new Version(1, 2, 3, 4);

        Assert.assertEquals(actualVersion.majorNumber(), expectedMajorNumber, "Major number of actual version object");

        Assert.assertEquals(actualVersion.minorNumber(), expectedMinorNumber, "Minor number of actual version object");

        Assert.assertEquals(actualVersion.buildNumber(), expectedBuildNumber, "Build number of actual version object");

        Assert.assertEquals(actualVersion.revisionNumber(), expectedRevisionNumber, "Revision number of actual version object");
    }

    @Test
    public void T002_Version_Constructor_fromAnnotation() {
        final Version actualVersion = Version.fromAnnotation(TestVersionClass.class);

        Assert.assertEquals(actualVersion, VERSION_4, "Value of actual version object");
    }

    @Test
    public void T003_Version_Constructor_CopyConstructor() {
        final Version actualVersion = new Version(VERSION_3);

        Assert.assertEquals(actualVersion, VERSION_3, "Value of actual version object");
    }

    @Test
    public void T004_Version_upgrade() {
        final Version actualVersion = new Version(VERSION_4);

        final Version expectedVersion = new Version(2, 2, 3, 4);

        final int expectedMajorNumber = 2;

        actualVersion.upgrade();

        Assert.assertEquals(actualVersion, expectedVersion, "Value of actual version object");

        Assert.assertEquals(actualVersion.majorNumber(), expectedMajorNumber, "Major number of actual version object");
    }

    @Test
    public void T005_Version_update() {
        final Version actualVersion = new Version(VERSION_4);

        final Version expectedVersion = new Version(1, 3, 3, 4);

        final int expectedMinorNumber = 3;

        actualVersion.update();

        Assert.assertEquals(actualVersion, expectedVersion, "Value of actual version object");

        Assert.assertEquals(actualVersion.minorNumber(), expectedMinorNumber, "Minor number of actual version object");
    }

    @Test
    public void T006_Version_build() {
        final Version actualVersion = new Version(VERSION_4);

        final Version expectedVersion = new Version(1, 2, 4, 4);

        final int expectedBuildNumber = 4;

        actualVersion.build();

        Assert.assertEquals(actualVersion, expectedVersion, "Value of actual version object");

        Assert.assertEquals(actualVersion.buildNumber(), expectedBuildNumber, "Build number of actual version object");
    }

    @Test
    public void T007_Version_revision() {
        final Version actualVersion = new Version(VERSION_4);

        final Version expectedVersion = new Version(1, 2, 3, 5);

        final int expectedRevisionNumber = 5;

        actualVersion.review();

        Assert.assertEquals(actualVersion, expectedVersion, "Value of actual version object");

        Assert.assertEquals(actualVersion.revisionNumber(), expectedRevisionNumber, "Revision number of actual version object");
    }

    @Test
    public void T008_Version_reset() {
        final Version actualVersion = new Version(VERSION_4);

        final Version expectedVersion = new Version(VERSION_1);

        actualVersion.reset();

        Assert.assertEquals(actualVersion, expectedVersion, "Value of actual version object");
    }

    @Test
    public void T009_Version_set() {
        final int expectedMajorNumber = 1;

        final int expectedMinorNumber = 2;

        final int expectedBuildNumber = 3;

        final int expectedRevisionNumber = 4;

        final Version actualVersion = new Version();

        actualVersion.set(VersionField.MAJOR, expectedMajorNumber);

        actualVersion.set(VersionField.MINOR, expectedMinorNumber);

        actualVersion.set(VersionField.BUILD, expectedBuildNumber);

        actualVersion.set(VersionField.REVISION, expectedRevisionNumber);

        Assert.assertEquals(actualVersion, VERSION_4, "Actual value of version object");
    }

    @Test
    public void T010_Version_set() {
        final int expectedMajorNumber = 1;

        final int expectedMinorNumber = 2;

        final int expectedBuildNumber = 3;

        final int expectedRevisionNumber = 4;

        final Version actualVersion = new Version();

        actualVersion.set(expectedMajorNumber, expectedMinorNumber, expectedBuildNumber, expectedRevisionNumber);

        Assert.assertEquals(actualVersion, VERSION_4, "Actual value of version object");
    }

    @Test
    public void T011_Version_isAnnotated() {
        Assert.assertTrue(Version.isAnnotated(TestVersionClass.class), "Class is annotated");
    }

    @Test
    public void T012_Version_isAnnotatedFail() {
        Assert.assertFalse(Version.isAnnotated(TestClass.class), "Class is annotated");
    }

    @Test
    public void T013_Version_fromAnnotation() {
        final Version actualVersion = Version.fromAnnotation(TestVersionClass.class);

        Assert.assertNotNull(actualVersion, "Actual value of version object");

        Assert.assertEquals(actualVersion, VERSION_4, "Actual value of version object");
    }

    @Test
    public void T014_Version_fromAnnotationFail() {
        final Version actualVersion = Version.fromAnnotation(TestClass.class);

        Assert.assertEquals(actualVersion, VERSION_1, "Actual value of version object");
    }

    @Test
    public void T015_Version_parse() {
        final String versionString1 = "0.0";

        final String versionString2 = "1.2";

        final String versionString3 = "1.2.0.3";

        final String versionString4 = "1.2.3.4";

        final String versionString5 = "1.2.3.4.5";

        final String versionString6 = "";

        final String versionString7 = null;

        final Version actualVersion1 = Version.parse(versionString1);

        final Version actualVersion2 = Version.parse(versionString2);

        final Version actualVersion3 = Version.parse(versionString3);

        final Version actualVersion4 = Version.parse(versionString4);

        final Version actualVersion5 = Version.parse(versionString5);

        final Version actualVersion6 = Version.parse(versionString6);

        final Version actualVersion7 = Version.parse(versionString7);

        Assert.assertEquals(actualVersion1, VERSION_1, "Actual value of version object");

        Assert.assertEquals(actualVersion2, VERSION_2, "Actual value of version object");

        Assert.assertEquals(actualVersion3, VERSION_3, "Actual value of version object");

        Assert.assertEquals(actualVersion4, VERSION_4, "Actual value of version object");

        Assert.assertEquals(actualVersion5, VERSION_4, "Actual value of version object");

        Assert.assertEquals(actualVersion6, VERSION_1, "Actual value of version object");

        Assert.assertEquals(actualVersion7, VERSION_1, "Actual value of version  object");
    }

    @Test(expectedExceptions = NumberFormatException.class)
    public void T016_Version_parseFail_invalidNumber() {
        final String versionString = "1.a";

        final Version actualVersion = Version.parse(versionString);
    }

    @Test(expectedExceptions = NumberFormatException.class)
    public void T017_Version_parseFail_wrongDelimiter() {
        final String versionString = "1,0";

        final Version actualVersion = Version.parse(versionString);
    }

    @Test
    public void T018_Version_toString() {
        final String expectedVersionString1 = "0.0";

        final String expectedVersionString2 = "1.2";

        final String expectedVersionString3 = "1.2.0.3";

        final String expectedVersionString4 = "1.2.3.4";

        Assert.assertEquals(VERSION_1.toString(), expectedVersionString1, "Version as string");
        Assert.assertEquals(VERSION_2.toString(), expectedVersionString2, "Version as string");
        Assert.assertEquals(VERSION_3.toString(), expectedVersionString3, "Version as string");
        Assert.assertEquals(VERSION_4.toString(), expectedVersionString4, "Version as string");
    }

    @Test
    public void T019_Version_copy() {
        final Version actualVersion = VERSION_3.copy();

        Assert.assertEquals(actualVersion, VERSION_3, "Value of actual version object");
    }

    @Test
    public void T020_Version_equals() {
        final TestClass invalidValue = new TestClass();

        final Version validValue = VERSION_4.copy();

        Assert.assertTrue(VERSION_4.equals(VERSION_4), "Both objects are equal");

        Assert.assertFalse(VERSION_4.equals(invalidValue), "Both objects are equal");

        Assert.assertTrue(VERSION_4.equals(validValue), "Both objects are equal");

        Assert.assertFalse(VERSION_4.equals(VERSION_3), "Both objects are equal");
    }

    @Test
    public void T021_Version_equals() {
        final int lessThan = -1;

        final int equals = 0;

        final int greaterThan = 1;

        final Version value = null;

        Assert.assertEquals(VERSION_1.compareTo(VERSION_4), lessThan, "Value of comparison");

        Assert.assertEquals(VERSION_4.compareTo(VERSION_4), equals, "Value of comparison");

        Assert.assertEquals(VERSION_4.compareTo(VERSION_1), greaterThan, "Value of comparison");

        Assert.assertEquals(VERSION_1.compareTo(value), greaterThan, "Value of comparison");
    }
}
