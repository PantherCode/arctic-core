package org.panthercode.arctic.core.event;

import org.panthercode.arctic.core.event.impl.EventMessage;
import org.panthercode.arctic.core.event.impl.arguments.AbstractEventArgs;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by architect on 26.03.17.
 */
public class EventMessageTest {

    private static class TestEventArgs extends AbstractEventArgs {

        private String data;

        TestEventArgs(String data) {
            this.data = data;
        }

        public String data() {
            return this.data;
        }
    }

    private static final String EVENT_ARGS_DATA = "Hello World";

    private static final Object SOURCE = new Object();

    private static final TestEventArgs EVENT_ARGS = new TestEventArgs(EVENT_ARGS_DATA);

    private static final EventHandler<TestEventArgs> EVENT_HANDLER = new EventHandler<TestEventArgs>() {
        @Override
        public void handle(Object source, TestEventArgs e) {
            String expectedString = "Hello World";

            Assert.assertNotNull(source, "Actual value of source");

            Assert.assertNotNull(e, "Actual value of event args");

            Assert.assertEquals(source, SOURCE, "Actual source of event");

            Assert.assertEquals(e.data(), expectedString, "Actual event content");
        }
    };

    private static final Handler<EventMessage<TestEventArgs>> MESSAGE_HANDLER = new Handler<EventMessage<TestEventArgs>>() {
        @Override
        public void handle(EventMessage<TestEventArgs> e) {
            Assert.assertNotNull(e, "Actual event message");

            Assert.assertEquals(e.source(), SOURCE, "Actual source of event");

            Assert.assertEquals(e.eventHandler(), EVENT_HANDLER, "Actual event handler");

            Assert.assertEquals(e.content(), EVENT_ARGS, "Actual event args");

            Assert.assertTrue(e.isConsumed(), "Message is consumed");

            Assert.assertFalse(e.isFailed(), "Message is failed");
        }
    };

    private static final EventMessage<TestEventArgs> EVENT_MESSAGE = new EventMessage<TestEventArgs>(null, null, null);

    @Test
    public void T01_DefaultEventMessage_Constructor() {
        Assert.assertEquals(EVENT_MESSAGE.eventHandler(), EVENT_HANDLER, "Actual event handler");

        Assert.assertEquals(EVENT_MESSAGE.content(), EVENT_HANDLER, "Actual event args");

        Assert.assertEquals(EVENT_MESSAGE.source(), SOURCE, "Actual source of event");

        Assert.assertEquals(EVENT_MESSAGE.handler(), MESSAGE_HANDLER, "Actual message handler");

        Assert.assertTrue(EVENT_MESSAGE.isConsumed(), "Message is consumed");

        Assert.assertFalse(EVENT_MESSAGE.isFailed(), "Message is failed");
    }
}
