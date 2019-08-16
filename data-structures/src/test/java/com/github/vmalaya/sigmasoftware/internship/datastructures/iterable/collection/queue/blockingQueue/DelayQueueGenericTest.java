package com.github.vmalaya.sigmasoftware.internship.datastructures.iterable.collection.queue.blockingQueue;

import org.junit.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A {@link DelayQueue} is an unbounded blocking queue of Delayed elements.
 * An element from the queue can be taken only when
 * the delay for that particular element has expired.
 * DelayQueue is a specialized PriorityQueue that orders elements based on their delay time.
 * Input data for this queue should extend {@link Delayed}
 */
public class DelayQueueGenericTest {

    @Test
    public void should_do_method_of_collection_in_specific_way() {

        //given
        Instant now = Instant.now();
        //
        DelayGeneric<Instant> delayGeneric = new DelayGeneric<>(now, 2);
        //and
        DelayQueue<DelayGeneric<Instant>> delayQueue = new DelayQueue<>();
        //and
        List<DelayGeneric<Instant>> delayGenerics = Arrays.asList(
                new DelayGeneric<>(Instant.now(), 3),
                new DelayGeneric<>(Instant.now(), 2)
        );

        //when
        delayQueue.add(delayGeneric);
        //the
        assertThat(delayQueue).isNotEmpty()
                              .hasSize(1);

        //when
        delayQueue.remove(delayGeneric);
        //then
        assertThat(delayQueue).isEmpty();

        //when
        delayQueue.addAll(delayGenerics);
        //then
        assertThat(delayQueue).isNotEmpty()
                              .hasSizeGreaterThanOrEqualTo(2);
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

        assertThat(consumer.numberOfConsumedElements.get())
                .isEqualTo(numberOfElementsToProduce);
    }

    @Test
    public void should_not_take_element_if_time_delay_is_not_expired() throws InterruptedException {

        //given
        ExecutorService executor = Executors.newFixedThreadPool(2);
        BlockingQueue<DelayObject> queue = new DelayQueue<>();
        int numberOfElementsToProduce = 1;
        int delay = 10_000;
        DelayQueueConsumer consumer = new DelayQueueConsumer(queue, numberOfElementsToProduce);
        DelayQueueProducer producer = new DelayQueueProducer(queue, numberOfElementsToProduce, delay);

        //when
        executor.submit(producer);
        executor.submit(consumer);

        //then
        executor.awaitTermination(5, TimeUnit.SECONDS);
        executor.shutdown();

        assertThat(consumer.numberOfConsumedElements.get()).isEqualTo(0);
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
        //and
        executor.submit(consumer);
        //and
        executor.awaitTermination(5, TimeUnit.SECONDS);
        //and
        executor.shutdown();

        //then
        Iterator iterator = consumer.getConsumedObjects().iterator();
        //and
        assertThat(iterator.hasNext()).isTrue();
        //and
        assertThat(iterator.next()).isNull();
    }
}
