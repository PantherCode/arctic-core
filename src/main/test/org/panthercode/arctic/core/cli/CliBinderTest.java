package org.panthercode.arctic.core.cli;

import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by architect on 06.03.17.
 */
@Test(sequential = true)
public class CliBinderTest {

    public class TestServerClass {

        private String host;

        private int port;

        private boolean useSsh;

        public TestServerClass() {
        }

        @CliParameter(name = "host", shortName = 'h', defaultValue = "localhost", hasValue = true, description = "host address")
        public void setHost(String host) {
            this.host = host;
        }

        public String getHost() {
            return this.host;
        }

        @CliParameter(name = "port", shortName = 'p', defaultValue = "1337", hasValue = true, description = "used server port", type = Integer.class)
        public void setPort(int port) {
            this.port = port;
        }

        public int getPort() {
            return this.port;
        }

        @CliParameter(name = "ssh", shortName = 's', defaultValue = "false", description = "use ssh", type = Boolean.class)
        public void setUseSsh(boolean useSsh) {
            this.useSsh = useSsh;
        }

        public boolean getUseSsh() {
            return this.useSsh;
        }
    }

    public class TestNameClass {
        private String name;

        public TestNameClass() {
        }

        @CliParameter(name = "name", shortName = 'n', hasValue = true)
        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    public class InvalidNameClass {
        public InvalidNameClass() {
        }

        @CliParameter(name = "name", shortName = 'n', hasValue = true)
        public void setName(String name) {
        }
    }

    public class InvalidTestClass {

        public InvalidTestClass() {
        }

        @CliParameter(name = "name")
        public void setField1(String value) {
        }

        @CliParameter(name = "name")
        public void setField2(String value) {
        }
    }

    public class InvalidParameterTestClass {
        public InvalidParameterTestClass() {
        }

        @CliParameter(name = "name", hasValue = true)
        public void setField(int value) {
        }
    }

    public class NotPrimitiveParameterTestClass {
        public NotPrimitiveParameterTestClass() {
        }

        @CliParameter(name = "name", hasValue = true, type = TestServerClass.class)
        public void setField(TestServerClass value) {
        }
    }

    public class EmptyParameterNameTestClass {
        public EmptyParameterNameTestClass() {
        }

        @CliParameter(name = "", hasValue = true)
        public void setField(int value) {
        }
    }

    @Test
    public void T001_CliBinder_from() throws Exception {
        final String expectedHost = "www.internet.com";

        final Integer expectedPort = 1234;

        final Boolean expectedSsh = true;

        final String[] args1 = {"--host", expectedHost, "--port", expectedPort.toString(), "--ssh"};

        final String[] args2 = {"-h", expectedHost, "-p", expectedPort.toString(), "--s"};

        final CliBinder binder1 = CliBinder.create().bind(TestServerClass.class).parse(args1);

        final CliBinder binder2 = CliBinder.create().bind(TestServerClass.class).parse(args2);

        final TestServerClass value1 = binder1.from(TestServerClass.class, new TestServerClass());

        final TestServerClass value2 = binder2.from(TestServerClass.class, new TestServerClass());

        Assert.assertNotEquals(value1, "Actual value");

        Assert.assertEquals(value1.getHost(), expectedHost, "Actual host value of instance");

        Assert.assertEquals(value1.getPort(), (int) expectedPort, "Actual port value of instance");

        Assert.assertTrue(value1.getUseSsh(), "Actual ssh value of instance");

        Assert.assertEquals(value2.getHost(), expectedHost, "Actual host value of instance");

        Assert.assertEquals(value2.getPort(), (int) expectedPort, "Actual port value of instance");

        Assert.assertTrue(value2.getUseSsh(), "Actual ssh value of instance");
    }

    @Test
    public void T002_CliBinder_from_defaultValue() throws Exception {
        final String expectedHost = "www.internet.com";

        final int expectedPort = 1337;

        final String[] args = {"--host", expectedHost};

        final CliBinder binder = CliBinder.create().bind(TestServerClass.class).parse(args);

        final TestServerClass value = binder.from(TestServerClass.class, new TestServerClass());

        Assert.assertEquals(value.getHost(), expectedHost, "Actual host value of instance");

        Assert.assertEquals(value.getPort(), expectedPort, "Actual port value of instance");

        Assert.assertFalse(value.getUseSsh(), "Actual ssh value of instance");
    }

    @Test
    public void T003_CliBinder_from_multipleClasses() throws Exception {
        final String expectedHost = "www.internet.com";

        final String expectedName = "Herbert";

        final String[] args = {"--host", expectedHost, "--name", expectedName};

        final CliBinder binder = CliBinder.create().bind(TestServerClass.class).bind(TestNameClass.class).parse(args);

        final TestServerClass value1 = binder.from(TestServerClass.class, new TestServerClass());

        final TestNameClass value2 = binder.from(TestNameClass.class, new TestNameClass());

        Assert.assertEquals(value1.getHost(), expectedHost, "Actual host value of instance");

        Assert.assertEquals(value2.getName(), expectedName, "Actual name value of instance");
    }

    @Test(expectedExceptions = NoSuchMethodException.class)
    public void T004_CliBinder_fromFail_invalidParameterType() throws Exception {
        final String[] args = {"--name", "123"};

        final CliBinder binder = CliBinder.create().bind(InvalidParameterTestClass.class).parse(args);

        binder.from(InvalidParameterTestClass.class, new InvalidParameterTestClass());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T005_CliBinder_fromFail_notPrimitiveParameterType() throws Exception {
        final CliBinder binder = CliBinder.create().bind(NotPrimitiveParameterTestClass.class).parse(null);

        binder.from(NotPrimitiveParameterTestClass.class, new NotPrimitiveParameterTestClass());
    }


    @Test(expectedExceptions = NullPointerException.class)
    public void T006_CliBinder_fromFail_nullValue() throws Exception {
        final CliBinder binder = CliBinder.create().parse(null);

        binder.from(InvalidNameClass.class, null);
    }

    @Test(expectedExceptions = UnrecognizedOptionException.class)
    public void T007_CliBinder_parseFail_invalidParameter() throws Exception {
        final String[] invalidArgs = {"-- host", "www.internet.com", "--invalidParameter"};

        CliBinder.create().bind(TestServerClass.class).parse(invalidArgs);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T008_CliBinder_parseFail_nameCollision() throws Exception {
        final String[] args = {};

        CliBinder.create().bind(InvalidTestClass.class).parse(args);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T009_CliBinder_parseFail_multiplyClassParameterNameCollision() throws Exception {
        final String[] args = {};

        CliBinder.create().bind(TestNameClass.class).bind(InvalidNameClass.class).parse(args);
    }

    @Test(expectedExceptions = MissingArgumentException.class)
    public void T010_CliBinder_parseFail_missingParameter() throws Exception {
        final String[] invalidArgs = {"--host"};

        CliBinder.create().bind(TestServerClass.class).parse(invalidArgs);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void T011_CliBinder_parseFail_emptyParameterName() throws Exception {
        CliBinder.create().bind(EmptyParameterNameTestClass.class).parse(null);
    }
}
