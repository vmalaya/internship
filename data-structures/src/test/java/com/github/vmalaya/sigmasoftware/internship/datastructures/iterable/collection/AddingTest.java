package com.github.vmalaya.sigmasoftware.internship.datastructures.iterable.collection;

import com.github.vmalaya.sigmasoftware.internship.iterable.collection.DelayObject;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;

/**
 * Testing following classes as collection for adding:
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
public class AddingTest {

    @Test
    public void should_add_an_element() {

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

        //type of input element needs to implement Delayed
        DelayQueue<Delayed> delayQueue = new DelayQueue<>();

        SynchronousQueue synchronousQueue = new SynchronousQueue(); //TODO: should be tested for adding an element

        //when
        Arrays.asList(arrayList,
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
        ).forEach(this::add);

        delayQueue.add(new DelayObject("TEST", 1));
    }

    @Test
    public void should_add_given_collection() {

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

        //type of input element needs to implement Delayed
        DelayQueue<Delayed> delayQueue = new DelayQueue<>();

        SynchronousQueue synchronousQueue = new SynchronousQueue(); //TODO: should be tested for adding a collection

        //when
        Arrays.asList(arrayList,
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
        ).forEach(this::addCollection);

        delayQueue.addAll(new ArrayList<DelayObject>());
    }

    private void add(Collection collection) {
        Boolean b = new Boolean(true);
        collection.add(b);
    }

    private void addCollection(Collection collection) {
        Collection newCollection = new ArrayList();
        collection.addAll(newCollection);
    }
}
