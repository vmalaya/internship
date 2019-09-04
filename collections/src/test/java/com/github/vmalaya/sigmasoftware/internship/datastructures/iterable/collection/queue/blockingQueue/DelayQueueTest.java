package com.github.vmalaya.sigmasoftware.internship.datastructures.iterable.collection.queue.blockingQueue;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * A {@link java.util.concurrent.DelayQueue} is an unbounded blocking queue of Delayed elements.
 * An element from the queue can be taken only when
 * the delay for that particular element has expired.
 * DelayQueue is a specialized PriorityQueue that orders elements based on their delay time.
 * Input data for this queue should extend {@link java.util.concurrent.Delayed}
 */
public class DelayQueueTest {

    @Test
    public void should_do_method_of_collection_in_specific_way() {

        //given
        Object object = "data";
        //and
        DelayObject delayObject = new DelayObject(object, 2);
        //and
        DelayQueue<Delayed> delayQueue = new DelayQueue<>();
        //and
        Object delayObject1 = "delayObject1";
        Object delayObject2 = "delayObject2";
        List<DelayObject> delayObjects = Arrays.asList(new DelayObject(delayObject1, 3),
                                                       new DelayObject(delayObject2, 2));

        //when
        delayQueue.add(delayObject);
        delayQueue.remove(delayObject);
        delayQueue.addAll(delayObjects);
        delayQueue.removeAll(delayObjects);

        //then
        Assert.assertTrue(delayQueue.isEmpty());
    }

    @Test
    public void should_put() throws InterruptedException {

        // given
        ExecutorService executor = Executors.newFixedThreadPool(1);

        BlockingQueue<DelayObject> queue = new DelayQueue<>();
        int numberOfElementsToProduce = 2;
        int delayOfEachProducedMessageMilliseconds = 500;
        DelayQueueProducer producer = new DelayQueueProducer(
                queue, numberOfElementsToProduce, delayOfEachProducedMessageMilliseconds);

        // when
        executor.submit(producer);

        // then
        executor.awaitTermination(5, TimeUnit.SECONDS);
        executor.shutdown();

        Assert.assertEquals(numberOfElementsToProduce,
                queue.size());
    }

    @Test
    public void should_take_from_queue() throws InterruptedException {

        // given
        ExecutorService executor = Executors.newFixedThreadPool(2);

        BlockingQueue<DelayObject> queue = new DelayQueue<>();
        int numberOfElementsToProduce = 2;
        int delayOfEachProducedMessageMilliseconds = 500;
        DelayQueueConsumer consumer = new DelayQueueConsumer(
                queue, numberOfElementsToProduce);
        DelayQueueProducer producer = new DelayQueueProducer(
                queue, numberOfElementsToProduce, delayOfEachProducedMessageMilliseconds);

        // when
        executor.submit(producer);
        executor.submit(consumer);

        // then
        executor.awaitTermination(5, TimeUnit.SECONDS);
        executor.shutdown();

        Assert.assertEquals(consumer.numberOfConsumedElements.get(), numberOfElementsToProduce);
    }

    @Test
    public void should_not_take_element_if_time_delay_is_not_expired() throws InterruptedException {

        //given
        ExecutorService executor = Executors.newFixedThreadPool(2);
        BlockingQueue<DelayObject> queue = new DelayQueue<>();
        int numberOfElementsToProduce = 1;
        int delayOfEachProducedMessageMilliseconds = 10_000;
        DelayQueueConsumer consumer = new DelayQueueConsumer(queue, numberOfElementsToProduce);
        DelayQueueProducer producer
                = new DelayQueueProducer(queue, numberOfElementsToProduce, delayOfEachProducedMessageMilliseconds);

        //when
        executor.submit(producer);
        executor.submit(consumer);

        //then
        executor.awaitTermination(5, TimeUnit.SECONDS);
        executor.shutdown();

        Assert.assertEquals(0, consumer.numberOfConsumedElements.get());
    }

    @Test
    public void should_poll_from_the_queue() throws InterruptedException {

        //given
        ExecutorService executor = Executors.newFixedThreadPool(2);
        BlockingQueue<DelayObject> queue = new DelayQueue<>();
        int numberOfElementsToProduce = 1;
        int delayOfEachProducedMessageMilliseconds = 10_000;
        DelayQueuePollConsumer consumer = new DelayQueuePollConsumer(queue, numberOfElementsToProduce);
        DelayQueueProducer producer
                = new DelayQueueProducer(queue, numberOfElementsToProduce, delayOfEachProducedMessageMilliseconds);

        //when
        executor.submit(producer);
        executor.submit(consumer);

        //then
        executor.awaitTermination(5, TimeUnit.SECONDS);
        executor.shutdown();

        Assert.assertNull(consumer.getConsumedObjects().get(0));
    }
}
