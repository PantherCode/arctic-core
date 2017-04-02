package org.panthercode.arctic.core.settings;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by architect on 08.03.17.
 */
public class ContextTest {

    private static final int INTEGER_VALUE = 1;

    private static final byte BYTE_VALUE = 2;

    private static final String STRING_VALUE = "Hello World";

    private static final String KEY_1 = "Key 1";

    private static final String KEY_2 = "Key 2";

    private static final String KEY_3 = "Key 3";

    private static final Context CONTEXT = new Context();

    @BeforeTest
    public void before(){
        CONTEXT.put(KEY_1, INTEGER_VALUE);

        CONTEXT.put(KEY_2, BYTE_VALUE);

        CONTEXT.put(KEY_3, STRING_VALUE);
    }

    @Test
    public void T001_Context_get(){
        final String unknownKey = "unknown";

        Assert.assertEquals((int) CONTEXT.get(KEY_1, Integer.class), INTEGER_VALUE, "Actual value");

        Assert.assertEquals(CONTEXT.getInteger(KEY_1), INTEGER_VALUE, "Actual value");

        Assert.assertEquals(CONTEXT.getIntegerOrDefault(unknownKey,INTEGER_VALUE), INTEGER_VALUE, "Actual value");

        Assert.assertEquals((byte) CONTEXT.get(KEY_2, Byte.class), BYTE_VALUE, "Actual value");

        Assert.assertEquals(CONTEXT.getByte(KEY_2), BYTE_VALUE, "Actual value");

        Assert.assertEquals(CONTEXT.getByteOrDefault(unknownKey,BYTE_VALUE), BYTE_VALUE, "Actual value");

        Assert.assertEquals(CONTEXT.get(KEY_3, String.class), STRING_VALUE, "Actual value");

        Assert.assertEquals(CONTEXT.getString(KEY_3), STRING_VALUE, "Actual value");

        Assert.assertEquals(CONTEXT.getStringOrDefault(unknownKey,STRING_VALUE), STRING_VALUE, "Actual value");
    }
}
