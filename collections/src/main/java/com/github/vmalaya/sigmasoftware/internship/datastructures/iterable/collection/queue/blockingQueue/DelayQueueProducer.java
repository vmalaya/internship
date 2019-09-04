package com.github.vmalaya.sigmasoftware.internship.datastructures.iterable.collection.queue.blockingQueue;

import java.util.concurrent.BlockingQueue;

public class DelayQueueProducer implements Runnable {

    private BlockingQueue<DelayObject> queue;
    private Integer numberOfElementsToProduce;
    private Integer delayOfEachProducedMessageMilliseconds;

    public DelayQueueProducer(BlockingQueue<DelayObject> queue, Integer numberOfElementsToProduce, Integer delayOfEachProducedMessageMilliseconds) {
        this.queue = queue;
        this.numberOfElementsToProduce = numberOfElementsToProduce;
        this.delayOfEachProducedMessageMilliseconds = delayOfEachProducedMessageMilliseconds;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfElementsToProduce; i++) {
            DelayObject object
                    = new DelayObject(
                    String.valueOf(i), delayOfEachProducedMessageMilliseconds);
            System.out.println("Put object: " + object);
            try {
                queue.put(object);
                Thread.sleep(500);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}