package com.github.vmalaya.sigmasoftware.internship.datastructures.iterable.collection.queue.blockingQueue;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;


/**
 * You need {@link java.util.concurrent.SynchronousQueue} if you want hand-off between threads.
 * It is one of the most faster  synchronization object, so it could help you
 * to improve application performance.
 * SynchronousQueue helps to control communication between threads.
 */
public class SynchronousQueueTest {

    @Test
    public void should_put_and_take_element_to_show_blocking() throws InterruptedException {

        //given
        ExecutorService executor = Executors.newFixedThreadPool(2);
        final SynchronousQueue<Integer> queue = new SynchronousQueue<>();

        //and
        ArrayList<Integer> consumedList = new ArrayList<>();
        ArrayList<Integer> puttedList = new ArrayList<>();

        //and
        Runnable producer = () -> {
            try {
                for (int i = 0; i < 2; i++) {
                    System.out.println("Saving an element: " + i + " to the exchange point");
                    queue.put(i);
                    puttedList.add(i);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        };

        Runnable consumer = () -> {
            try {

                for (int i = 0; i < 2; i++) {
                    consumedList.add(queue.take());
                    System.out.println("Consumed an element: " + consumedList.get(i) + " from the exchange point");
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        };

        //when
        executor.execute(producer);
        executor.execute(consumer);

        //then
        executor.awaitTermination(500, TimeUnit.MILLISECONDS);
        executor.shutdown();
        Assert.assertEquals(puttedList, consumedList);
    }

}
