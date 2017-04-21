package org.panthercode.arctic.core.event;

import org.panthercode.arctic.core.event.impl.DefaultEvent;
import org.panthercode.arctic.core.event.impl.DefaultEventFactory;
import org.panthercode.arctic.core.event.impl.EventBus;
import org.panthercode.arctic.core.event.impl.arguments.AbstractEventArgs;
import org.panthercode.arctic.core.runtime.message.Message;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by architect on 27.03.17.
 */
public class DefaultEventTest {
/*
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
            Assert.assertEquals(e.content(), CONTENT, "Actual body of event");
        }
    };

    private static final EventHandler<TestEventArgs> EVENT_HANDLER2 = new EventHandler<TestEventArgs>() {
        @Override
        public void handle(Object source, TestEventArgs e) {
            Assert.assertEquals(e.content(), CONTENT, "Actual body of event");
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
            Assert.assertEquals(e.getMessage().content().content(), CONTENT, "Actual body of event");

            Assert.assertTrue(e.getMessage().isFailed(), "Message is failed");
        }
    };

    private static final DefaultEventFactory<TestEventArgs> FACTORY = new DefaultEventFactory<TestEventArgs>(EVENT_BUS, MESSAGE_HANDLER, EXCEPTION_HANDLER);

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

        Event<TestEventArgs> event = new DefaultEvent<>(FACTORY);

        Assert.assertEquals(event.size(), expectedSize1, "Actual size of event responseHandler");

        Assert.assertTrue(event.addHandler(EVENT_HANDLER1), "Actual result of method");

        Assert.assertTrue(event.addHandler(EVENT_HANDLER2), "Actual result of method");

        Assert.assertEquals(event.size(), expectedSize2, "Actual size of event responseHandler");
    }

    @Test
    public void T02_DefaultEvent_addHandlerFail() {
        int expectedSize1 = 0;

        int expectedSize2 = 1;

        Event<TestEventArgs> event = new DefaultEvent<>(FACTORY);

        Assert.assertFalse(event.addHandler(INVALID_EVENT_HANDLER), "Actual result of method");

        Assert.assertEquals(event.size(), expectedSize1, "Actual size of event responseHandler");

        Assert.assertTrue(event.addHandler(EVENT_HANDLER1), "Actual result of method");

        Assert.assertFalse(event.addHandler(EVENT_HANDLER1), "Actual result of method");

        Assert.assertEquals(event.size(), expectedSize2, "Actual size of event responseHandler");
    }

    @Test
    public void T03_DefaultEvent_removeHandler() {
        int expectedSize1 = 0;

        int expectedSize2 = 1;

        Event<TestEventArgs> event = new DefaultEvent<>(FACTORY);

        Assert.assertEquals(event.size(), expectedSize1, "Actual size of event responseHandler");

        Assert.assertTrue(event.addHandler(EVENT_HANDLER1), "Actual result of method");

        Assert.assertEquals(event.size(), expectedSize2, "Actual size of event responseHandler");

        Assert.assertTrue(event.removeHandler(EVENT_HANDLER1), "Actual result of method");

        Assert.assertEquals(event.size(), expectedSize1, "Actual size of event responseHandler");
    }

    @Test
    public void T04_DefaultEvent_removeHandlerFail() {
        int expectedSize = 1;

        Event<TestEventArgs> event = new DefaultEvent<>(FACTORY);

        Assert.assertTrue(event.addHandler(EVENT_HANDLER1), "Actual result of method");

        Assert.assertEquals(event.size(), expectedSize, "Actual size of event responseHandler");

        Assert.assertFalse(event.removeHandler(INVALID_EVENT_HANDLER), "Actual result of method");

        Assert.assertEquals(event.size(), expectedSize, "Actual size of event responseHandler");

        Assert.assertFalse(event.removeHandler(EVENT_HANDLER2), "Actual result of method");

        Assert.assertEquals(event.size(), expectedSize, "Actual size of event responseHandler");

        Assert.assertTrue(event.removeHandler(EVENT_HANDLER1), "Actual result of method");

        Assert.assertFalse(event.removeHandler(EVENT_HANDLER1), "Actual result of method");
    }

    @Test
    public void T05_DefaultEvent_hasHandler() {
        Event<TestEventArgs> event = new DefaultEvent<>(FACTORY);

        Assert.assertTrue(event.addHandler(EVENT_HANDLER1), "Actual result of method");

        Assert.assertTrue(event.hasHandler(EVENT_HANDLER1), "Event contains responseHandler");

        Assert.assertFalse(event.hasHandler(EVENT_HANDLER2), "Event contains responseHandler");
    }

    @Test
    public void T06_DefaultEvent_isEmpty() {
        Event<TestEventArgs> event = new DefaultEvent<>(FACTORY);

        Assert.assertTrue(event.isEmpty(), "Event is empty");

        Assert.assertTrue(event.addHandler(EVENT_HANDLER1), "Actual result of method");

        Assert.assertFalse(event.isEmpty(), "Event is empty");

        Assert.assertTrue(event.removeHandler(EVENT_HANDLER1), "Actual result of method");

        Assert.assertTrue(event.isEmpty(), "Event is empty");
    }

    @Test
    public void T07_DefaultEvent_send() {
        TestEventArgs eventArgs = new TestEventArgs(CONTENT);

        Event<TestEventArgs> event = new DefaultEvent<>(FACTORY);

        Assert.assertTrue(event.addHandler(EVENT_HANDLER1), "Actual result of method");

        Assert.assertTrue(event.addHandler(EVENT_HANDLER2), "Actual result of method");

        event.send(SOURCE, eventArgs);
    }
    */
}