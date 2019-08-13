package com.github.vmalaya.sigmasoftware.internship.datastructures.iterable.collection;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;

public class ClearingTest {

    @Test
    public void should_remove_all_elements() {

        //given
        int capacity = 2;
        ArrayList<Object> arrayList = new ArrayList<>();
        LinkedList<Object> linkedList = new LinkedList<>();
        Vector<Object> vector = new Vector<>();
        Stack<Object> stack = new Stack<>();
        CopyOnWriteArrayList<Object> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        ArrayBlockingQueue<Object> arrayBlockingQueue = new ArrayBlockingQueue<>(capacity);
        PriorityQueue<Object> priorityQueue = new PriorityQueue<>();
        ConcurrentLinkedQueue<Object> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        LinkedBlockingQueue<Object> linkedBlockingQueue = new LinkedBlockingQueue<>();
        LinkedTransferQueue<Object> linkedTransferQueue = new LinkedTransferQueue<>();
        ArrayDeque<Object> arrayDeque = new ArrayDeque<>();
        ConcurrentLinkedDeque<Object> concurrentLinkedDeque = new ConcurrentLinkedDeque<>();
        LinkedBlockingDeque<Object> linkedBlockingDeque = new LinkedBlockingDeque<>();
        HashSet<Object> hashSet = new HashSet<>();
        LinkedHashSet<Object> linkedHashSet = new LinkedHashSet<>();
        ConcurrentSkipListSet<Object> concurrentSkipListSet = new ConcurrentSkipListSet<>();
        PriorityBlockingQueue priorityBlockingQueue = new PriorityBlockingQueue<>();
        TreeSet treeSet = new TreeSet<>();
        CopyOnWriteArraySet copyOnWriteArraySet = new CopyOnWriteArraySet<>();
        DelayQueue<Delayed> delayQueue = new DelayQueue<>();
        SynchronousQueue synchronousQueue = new SynchronousQueue();//TODO: clear()

        List<? extends Collection> collections = Arrays.asList(arrayList,
                linkedList,
                vector,
                stack,
                copyOnWriteArrayList,
                arrayBlockingQueue,
                priorityQueue,
                concurrentLinkedQueue,
                linkedBlockingQueue,
                linkedTransferQueue,
                arrayDeque,
                concurrentLinkedDeque,
                linkedBlockingDeque,
                hashSet,
                linkedHashSet,
                concurrentSkipListSet,
                priorityBlockingQueue,
                treeSet,
                copyOnWriteArraySet
        );
        collections.forEach(n -> n.addAll(Arrays.asList(true, false)));
        delayQueue.add(new DelayObject("TEST", 1));

        //when
        collections.forEach(n -> n.clear());
        delayQueue.clear();

        //then
        collections.forEach(n -> Assert.assertTrue(n.isEmpty()));
        Assert.assertTrue(delayQueue.isEmpty());
    }
}
