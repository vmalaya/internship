package com.github.vmalaya.sigmasoftware.internship.datastructures.iterable.collection;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;

/**
 * Testing following classes as collection for checking element containment:
 * ArrayList
 * LinkedList
 * Vector
 * Stack
 * HashSet
 * <p>
 * LinkedHashSet
 * TreeSet
 * ConcurrentSkipListSet
 * RegularEnumSet
 * CopyOnWriteArraySet
 * <p>
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
public class ContainmentTest {

    @Test
    public void should_check_element_containment() {

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

        SynchronousQueue synchronousQueue = new SynchronousQueue(); //TODO: should be tested for containment an element

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
        collections.forEach(n -> n.add(true));

        DelayObject delayObject = new DelayObject("TEST", 1);
        delayQueue.add(delayObject);

        //then
        collections.forEach(n -> Assert.assertTrue(n.contains(true)));
        Assert.assertTrue(delayQueue.contains(delayObject));
    }

    @Test
    public void should_check_containment_all_elements_given_collection() {

        //given
        int capacity = 3;
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

        SynchronousQueue synchronousQueue = new SynchronousQueue(); //TODO: should be tested for containment a collection

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
        collections.forEach(n -> n.addAll(Arrays.asList(true, true, true)));

        List<DelayObject> delayObjectList = Arrays.asList(new DelayObject("TEST", 1),
                new DelayObject("TEST", 2),
                new DelayObject("TEST", 3));
        delayQueue.addAll(delayObjectList);

        //then
        collections.forEach(n -> Assert.assertTrue(n.containsAll(Arrays.asList(true))));
        collections.forEach(n -> Assert.assertFalse(n.containsAll(Arrays.asList(true, false))));
        Assert.assertTrue(delayQueue.containsAll(delayObjectList));
        Assert.assertFalse(delayQueue.containsAll(Arrays.asList(new DelayObject("TEST", 1),
                new DelayObject("TEST", 2),
                new DelayObject("TEST", 4))));
    }
}
