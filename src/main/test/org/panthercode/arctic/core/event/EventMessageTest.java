package org.panthercode.arctic.core.event;

import org.panthercode.arctic.core.event.impl.EventMessage;
import org.panthercode.arctic.core.event.impl.arguments.AbstractEventArgs;
import org.panthercode.arctic.core.runtime.Message;
import org.panthercode.arctic.core.runtime.MessageConsumeFailure;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by architect on 26.03.17.
 */
public class EventMessageTest {

    private static String CONTENT = "Hello World";

    private static Object SOURCE = new Object();

    private static class TestEventArgs extends AbstractEventArgs {

        private String content;

        public TestEventArgs(String content) {
            this.content = content;
        }

        public String content() {
            return this.content;
        }
    }

    private static final EventHandler<TestEventArgs> EVENT_HANDLER = new EventHandler<TestEventArgs>() {
        @Override
        public void handle(Object source, TestEventArgs e) {
            Assert.assertEquals(e.content(), CONTENT, "Actual content of event");
        }
    };

    private static final Handler<Message<TestEventArgs>> MESSAGE_HANDLER = new Handler<Message<TestEventArgs>>() {
        @Override
        public void handle(Message<TestEventArgs> e) {
            Assert.assertTrue(e.isConsumed(), "Message is consumed");

            Assert.assertFalse(e.isFailed(), "Message is failed");
        }
    };

    private static final EventHandler<TestEventArgs> EXCEPTION_EVENT_HANDLER = new EventHandler<TestEventArgs>() {
        @Override
        public void handle(Object source, TestEventArgs e) {
            Assert.assertEquals(e.content(), CONTENT, "Actual content of event");

            throw new RuntimeException();
        }
    };

    private static final Handler<Message<TestEventArgs>> EXCEPTION_MESSAGE_HANDLER = new Handler<Message<TestEventArgs>>() {
        @Override
        public void handle(Message<TestEventArgs> e) {
            Assert.assertTrue(e.isConsumed(), "Message is consumed");

            Assert.assertTrue(e.isFailed(), "Message is failed");
        }
    };

    private static final Handler<MessageConsumeFailure<TestEventArgs>> EXCEPTION_HANDLER = new Handler<MessageConsumeFailure<TestEventArgs>>() {
        @Override
        public void handle(MessageConsumeFailure<TestEventArgs> e) {
            Assert.assertEquals(e.getMessage().content().content(), CONTENT, "Actual content of event");

            Assert.assertTrue(e.getMessage().isFailed(), "Message is failed");
        }
    };

    @Test
    public void T01_EventMessage_Constructor() {
        TestEventArgs eventArgs = new TestEventArgs(CONTENT);

        EventMessage<TestEventArgs> eventMessage = new EventMessage<>(SOURCE, eventArgs, EVENT_HANDLER, MESSAGE_HANDLER, EXCEPTION_HANDLER);

        Assert.assertEquals(eventMessage.source(), SOURCE, "Actual value of source");

        Assert.assertEquals(eventMessage.eventHandler(), EVENT_HANDLER, "Actual value of event consumeHandler");

        Assert.assertEquals(eventMessage.consumeHandler(), MESSAGE_HANDLER, "Actual value of message consumeHandler");

        Assert.assertFalse(eventMessage.isConsumed(), "Message is consumed");

        Assert.assertFalse(eventMessage.isFailed(), "Message is failed");
    }

    @Test
    public void T02_EventMessage_consume() {
        TestEventArgs eventArgs = new TestEventArgs(CONTENT);

        EventMessage<TestEventArgs> eventMessage = new EventMessage<>(SOURCE, eventArgs, EVENT_HANDLER, MESSAGE_HANDLER, EXCEPTION_HANDLER);

        eventMessage.consume();

        Assert.assertTrue(eventMessage.isConsumed(), "Message is consumed");

        Assert.assertFalse(eventMessage.isFailed(), "Message is failed");
    }

    @Test
    public void T03_EventMessage_consumeFail() {
        TestEventArgs eventArgs = new TestEventArgs(CONTENT);

        EventMessage<TestEventArgs> eventMessage = new EventMessage<>(SOURCE, eventArgs, EXCEPTION_EVENT_HANDLER, EXCEPTION_MESSAGE_HANDLER, EXCEPTION_HANDLER);

        eventMessage.consume();

        Assert.assertTrue(eventMessage.isConsumed(), "Message is consumed");

        Assert.assertTrue(eventMessage.isFailed(), "Message is failed");
    }
}
