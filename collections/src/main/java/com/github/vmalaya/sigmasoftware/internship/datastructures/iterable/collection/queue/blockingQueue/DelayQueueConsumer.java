package com.github.vmalaya.sigmasoftware.internship.datastructures.iterable.collection.queue.blockingQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class DelayQueueConsumer implements Runnable {

    private BlockingQueue<DelayObject> queue;
    private Integer numberOfElementsToTake;
    public AtomicInteger numberOfConsumedElements;


    public DelayQueueConsumer(BlockingQueue<DelayObject> queue, Integer numberOfElementsToProduce) {
        this.queue = queue;
        this.numberOfElementsToTake = numberOfElementsToProduce;
        numberOfConsumedElements = new AtomicInteger();
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfElementsToTake; i++) {
            try {
                DelayObject object = queue.take();
                numberOfConsumedElements.incrementAndGet();
                System.out.println("Consumer poll: " + object);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
