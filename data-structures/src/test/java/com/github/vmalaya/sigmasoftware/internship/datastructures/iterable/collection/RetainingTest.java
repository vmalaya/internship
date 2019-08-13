package com.github.vmalaya.sigmasoftware.internship.datastructures.iterable.collection;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;

/**
 * Testing following classes as collection for retaining only
 * the elements in this collection that are contained in the
 * specified collection:
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
public class RetainingTest {

    @Test
    public void should_retain_only_contained_elements_in_collection() {

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
        SynchronousQueue synchronousQueue = new SynchronousQueue();//TODO: should be done retaining

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
                copyOnWriteArraySet
        );
        collections.forEach(n -> n.addAll(Arrays.asList(true, false)));
        delayQueue.addAll(Arrays.asList(new DelayObject("test", 1),
                new DelayObject("test", 2)));

        //when
        collections.forEach(n -> n.retainAll(Arrays.asList(true)));
        delayQueue.removeAll(Arrays.asList(new DelayObject("test", 1)));

        //then
        collections.forEach(n -> Assert.assertFalse(n.contains(false)));
        Assert.assertFalse(delayQueue.contains(new DelayObject("test", 2)));
    }
}
