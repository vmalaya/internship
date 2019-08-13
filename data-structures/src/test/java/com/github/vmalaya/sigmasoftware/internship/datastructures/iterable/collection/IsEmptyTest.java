package com.github.vmalaya.sigmasoftware.internship.datastructures.iterable.collection;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;

/**
 * Testing following classes as collection for checking collection emptiness:
 * ArrayList
 * LinkedList
 * Vector
 * Stack
 * HashSet
 *
 * LinkedHashSet
 * TreeSet
 * ConcurrentSkipListSet
 * RegularEnumSet
 * CopyOnWriteArraySet
 *
 * ConcurrentLinkedQueue
 * PriorityQueue
 * LinkedBlockingDeque
 * ArrayDeque
 * ConcurrentLinkedDeque
 * ArrayBlockingQueue
 * LinkedTransferQueue
 * LinkedBlockingQueue
 * DelayQueue
 * PriorityBlockingQueue
 * SynchronousQueue
 * LinkedTransferQueue
 */
public class IsEmptyTest {

    @Test
    public void should_check_for_emptiness() {

        //given
        int capacity = 1;
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
        SynchronousQueue synchronousQueue = new SynchronousQueue();

        //when
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
                copyOnWriteArraySet,
                delayQueue,
                synchronousQueue
        );

        //then
        collections.forEach(n -> Assert.assertTrue(n.isEmpty()));
    }
}
