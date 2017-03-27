package org.panthercode.arctic.core.event;

import org.panthercode.arctic.core.event.impl.EventBus;
import org.panthercode.arctic.core.runtime.Message;
import org.panthercode.arctic.core.runtime.MessageConsumeFailure;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by architect on 26.03.17.
 */
public class EventBusTest {

    private static String CONTENT = "Hello World";

    private class TestMessage implements Message<String> {

        private String content;

        private boolean isConsumed = false;

        private boolean isFailed = false;

        TestMessage(String content) {
            this.content = content;
        }

        @Override
        public String content() {
            return this.content;
        }

        @Override
        public boolean isConsumed() {
            return this.isConsumed;
        }

        @Override
        public boolean isFailed() {
            return this.isFailed;
        }

        @Override
        public void consume() {
            Assert.assertEquals(this.content, CONTENT, "actual message content");
        }

        @Override
        public Handler<Message<String>> consumedHandler() {
            return null;
        }

        @Override
        public Handler<MessageConsumeFailure<String>> exceptionHandler() {
            return null;
        }
    }

    private class DelayTestMessage implements Message<String> {

        private String content;

        private boolean isConsumed = false;

        private boolean isFailed = false;

        DelayTestMessage(String content) {
            this.content = content;
        }

        @Override
        public String content() {
            return this.content;
        }

        @Override
        public boolean isConsumed() {
            return this.isConsumed;
        }

        @Override
        public boolean isFailed() {
            return this.isFailed;
        }

        @Override
        public void consume() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Assert.assertEquals(this.content, CONTENT, "actual message content");
        }

        @Override
        public Handler<Message<String>> consumedHandler() {
            return null;
        }

        @Override
        public Handler<MessageConsumeFailure<String>> exceptionHandler() {
            return null;
        }
    }

    @Test
    public void T01_EventBus_Constructor() {
        EventBus eventBus = new EventBus();

        Assert.assertFalse(eventBus.isActive(), "event bus is active");

        Assert.assertTrue(eventBus.canActivate(), "event bus can activate");

        Assert.assertTrue(eventBus.canDeactivate(), "event bus can deactivate");
    }

    @Test
    public void T02_EventBus_activate() throws InterruptedException {
        EventBus eventBus = new EventBus();

        eventBus.activate();

        Thread.sleep(1000);

        Assert.assertTrue(eventBus.isActive(), "event bus is active");

        eventBus.deactivate();

        Thread.sleep(1000);

        Assert.assertFalse(eventBus.isActive(), "event bus is active");
    }

    @Test
    public void T03_EventBus_process() {
        EventBus eventBus = new EventBus();

        eventBus.activate();

        eventBus.process(new TestMessage(CONTENT));

        eventBus.deactivate();
    }

    @Test
    public void T04_EventBus_processFail(){
        EventBus eventBus = new EventBus();

        eventBus.activate();

        eventBus.process(new TestMessage(CONTENT));


    }

    @Test
    public void T05_EventBus_canDeactivateFail() throws Exception {
        EventBus eventBus = new EventBus();

        eventBus.activate();

        eventBus.process(new DelayTestMessage(CONTENT));

        Assert.assertFalse(eventBus.canDeactivate(), "event bus can deactivate");

        Thread.sleep(2000);

        eventBus.deactivate();
    }
}
