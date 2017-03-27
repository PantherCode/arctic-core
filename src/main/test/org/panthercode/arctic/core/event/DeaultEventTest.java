package org.panthercode.arctic.core.event;

import org.panthercode.arctic.core.event.impl.DefaultEvent;
import org.panthercode.arctic.core.event.impl.EventBus;
import org.panthercode.arctic.core.event.impl.arguments.AbstractEventArgs;
import org.panthercode.arctic.core.runtime.Message;
import org.panthercode.arctic.core.runtime.MessageConsumeFailure;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by architect on 27.03.17.
 */
public class DeaultEventTest {

    private static final EventBus EVENT_BUS = new EventBus();

    private static final String CONTENT = "Hello World";

    private static final Object SOURCE = new Object();

    private static class TestEventArgs extends AbstractEventArgs {

        private String content;

        public TestEventArgs(String content) {
            this.content = content;
        }

        public String content() {
            return this.content;
        }
    }

    private static final EventHandler<TestEventArgs> EVENT_HANDLER1 = new EventHandler<TestEventArgs>() {
        @Override
        public void handle(Object source, TestEventArgs e) {
            Assert.assertEquals(e.content(), CONTENT, "Actual content of event");
        }
    };

    private static final EventHandler<TestEventArgs> EVENT_HANDLER2 = new EventHandler<TestEventArgs>() {
        @Override
        public void handle(Object source, TestEventArgs e) {
            Assert.assertEquals(e.content(), CONTENT, "Actual content of event");
        }
    };

    private static final EventHandler<TestEventArgs> INVALID_EVENT_HANDLER = null;

    private static final Handler<Message<TestEventArgs>> MESSAGE_HANDLER = new Handler<Message<TestEventArgs>>() {
        @Override
        public void handle(Message<TestEventArgs> e) {
            Assert.assertTrue(e.isConsumed(), "Message is consumed");

            Assert.assertFalse(e.isFailed(), "Message is failed");
        }
    };

    private static final Handler<MessageConsumeFailure<TestEventArgs>> EXCEPTION_HANDLER = new Handler<MessageConsumeFailure<TestEventArgs>>() {
        @Override
        public void handle(MessageConsumeFailure<TestEventArgs> e) {
            Assert.assertEquals(e.getMessage().content().content(), CONTENT, "Actual content of event");

            Assert.assertTrue(e.getMessage().isFailed(), "Message is failed");
        }
    };

    @BeforeClass
    public void before() {
        EVENT_BUS.activate();
    }

    @AfterClass
    public void after() {
        EVENT_BUS.deactivate();
    }

    @Test
    public void T01_DefaultEvent_addHandler() {
        int expectedSize1 = 0;

        int expectedSize2 = 2;

        Event<TestEventArgs> event = new DefaultEvent<>(EVENT_BUS, MESSAGE_HANDLER, EXCEPTION_HANDLER);

        Assert.assertEquals(event.size(), expectedSize1, "Actual size of event consumeHandler");

        Assert.assertTrue(event.addHandler(EVENT_HANDLER1), "Actual result of method");

        Assert.assertTrue(event.addHandler(EVENT_HANDLER2), "Actual result of method");

        Assert.assertEquals(event.size(), expectedSize2, "Actual size of event consumeHandler");
    }

    @Test
    public void T02_DefaultEvent_addHandlerFail() {
        int expectedSize1 = 0;

        int expectedSize2 = 1;

        Event<TestEventArgs> event = new DefaultEvent<>(EVENT_BUS, MESSAGE_HANDLER, EXCEPTION_HANDLER);

        Assert.assertFalse(event.addHandler(INVALID_EVENT_HANDLER), "Actual result of method");

        Assert.assertEquals(event.size(), expectedSize1, "Actual size of event consumeHandler");

        Assert.assertTrue(event.addHandler(EVENT_HANDLER1), "Actual result of method");

        Assert.assertFalse(event.addHandler(EVENT_HANDLER1), "Actual result of method");

        Assert.assertEquals(event.size(), expectedSize2, "Actual size of event consumeHandler");
    }

    @Test
    public void T03_DefaultEvent_removeHandler() {
        int expectedSize1 = 0;

        int expectedSize2 = 1;

        Event<TestEventArgs> event = new DefaultEvent<>(EVENT_BUS, MESSAGE_HANDLER, EXCEPTION_HANDLER);

        Assert.assertEquals(event.size(), expectedSize1, "Actual size of event consumeHandler");

        Assert.assertTrue(event.addHandler(EVENT_HANDLER1), "Actual result of method");

        Assert.assertEquals(event.size(), expectedSize2, "Actual size of event consumeHandler");

        Assert.assertTrue(event.removeHandler(EVENT_HANDLER1), "Actual result of method");

        Assert.assertEquals(event.size(), expectedSize1, "Actual size of event consumeHandler");
    }

    @Test
    public void T04_DefaultEvent_removeHandlerFail() {
        int expectedSize = 1;

        Event<TestEventArgs> event = new DefaultEvent<>(EVENT_BUS, MESSAGE_HANDLER, EXCEPTION_HANDLER);

        Assert.assertTrue(event.addHandler(EVENT_HANDLER1), "Actual result of method");

        Assert.assertEquals(event.size(), expectedSize, "Actual size of event consumeHandler");

        Assert.assertFalse(event.removeHandler(INVALID_EVENT_HANDLER), "Actual result of method");

        Assert.assertEquals(event.size(), expectedSize, "Actual size of event consumeHandler");

        Assert.assertFalse(event.removeHandler(EVENT_HANDLER2), "Actual result of method");

        Assert.assertEquals(event.size(), expectedSize, "Actual size of event consumeHandler");

        Assert.assertTrue(event.removeHandler(EVENT_HANDLER1), "Actual result of method");

        Assert.assertFalse(event.removeHandler(EVENT_HANDLER1), "Actual result of method");
    }

    @Test
    public void T05_DefaultEvent_hasHandler() {
        Event<TestEventArgs> event = new DefaultEvent<>(EVENT_BUS, MESSAGE_HANDLER, EXCEPTION_HANDLER);

        Assert.assertTrue(event.addHandler(EVENT_HANDLER1), "Actual result of method");

        Assert.assertTrue(event.hasHandler(EVENT_HANDLER1), "Event contains consumeHandler");

        Assert.assertFalse(event.hasHandler(EVENT_HANDLER2), "Event contains consumeHandler");
    }

    @Test
    public void T06_DefaultEvent_isEmpty() {
        Event<TestEventArgs> event = new DefaultEvent<>(EVENT_BUS, MESSAGE_HANDLER, EXCEPTION_HANDLER);

        Assert.assertTrue(event.isEmpty(), "Event is empty");

        Assert.assertTrue(event.addHandler(EVENT_HANDLER1), "Actual result of method");

        Assert.assertFalse(event.isEmpty(), "Event is empty");

        Assert.assertTrue(event.removeHandler(EVENT_HANDLER1), "Actual result of method");

        Assert.assertTrue(event.isEmpty(), "Event is empty");
    }

    @Test
    public void T07_DefaultEvent_send() {
        TestEventArgs eventArgs = new TestEventArgs(CONTENT);

        Event<TestEventArgs> event = new DefaultEvent<>(EVENT_BUS, MESSAGE_HANDLER, EXCEPTION_HANDLER);

        Assert.assertTrue(event.addHandler(EVENT_HANDLER1), "Actual result of method");

        Assert.assertTrue(event.addHandler(EVENT_HANDLER2), "Actual result of method");

        event.send(SOURCE, eventArgs);
    }
}
