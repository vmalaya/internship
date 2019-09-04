package com.github.vmalaya.sigmasoftware.internship.datastructures.iterable.collection.queue.blockingQueue;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class DelayQueuePollConsumer implements Runnable {
    private BlockingQueue<DelayObject> queue;
    private Integer numberOfElementsToTake;
    public AtomicInteger numberOfConsumedElements;
    private ArrayList consumedObjects;


    public DelayQueuePollConsumer(BlockingQueue<DelayObject> queue, Integer numberOfElementsToProduce) {
        this.queue = queue;
        this.numberOfElementsToTake = numberOfElementsToProduce;
        numberOfConsumedElements = new AtomicInteger();
        consumedObjects = new ArrayList();
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfElementsToTake; i++) {
            try {
                DelayObject object = queue.poll(200, TimeUnit.MILLISECONDS);
                consumedObjects.add(object);
                numberOfConsumedElements.incrementAndGet();
                System.out.println("Consumer take: " + object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList getConsumedObjects() {
        return consumedObjects;
    }

    public void setConsumedObjects(ArrayList consumedObjects) {
        this.consumedObjects = consumedObjects;
    }
}
