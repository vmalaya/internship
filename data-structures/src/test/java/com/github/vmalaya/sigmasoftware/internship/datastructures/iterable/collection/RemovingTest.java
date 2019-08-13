package com.github.vmalaya.sigmasoftware.internship.datastructures.iterable.collection;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;

/**
 * Testing following classes as collection for removing:
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
public class RemovingTest {

    @Test
    public void should_remove_an_element() {

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
        PriorityBlockingQueue<Object> priorityBlockingQueue = new PriorityBlockingQueue<>();
        TreeSet<Object> treeSet = new TreeSet<>();
        CopyOnWriteArraySet<Object> copyOnWriteArraySet = new CopyOnWriteArraySet<>();

        //type of input element needs to implement Delayed
        DelayQueue<Delayed> delayQueue = new DelayQueue<>();

        SynchronousQueue synchronousQueue = new SynchronousQueue(); //TODO: should be tested for removing an element

        //input element for DelayQueue
        DelayObject delayObject = new DelayObject("TEST", 1);

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
        collections.forEach(this::add);
        delayQueue.add(delayObject);

        //when
        collections.forEach(this::remove);
        delayQueue.remove(delayObject);
    }

    @Test
    public void should_remove_a_collection() {

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
        PriorityBlockingQueue<Object> priorityBlockingQueue = new PriorityBlockingQueue<>();
        TreeSet<Object> treeSet = new TreeSet<>();
        CopyOnWriteArraySet<Object> copyOnWriteArraySet = new CopyOnWriteArraySet<>();

        //type of input element needs to implement Delayed
        DelayQueue<Delayed> delayQueue = new DelayQueue<>();

        SynchronousQueue synchronousQueue = new SynchronousQueue(); //TODO: should be tested for removing an element

        //input element for DelayQueue
        List<DelayObject> delayObjects = Arrays.asList(new DelayObject("TEST", 1));

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
        collections.forEach(this::addCollection);
        delayQueue.addAll(delayObjects);

        //when
        collections.forEach(this::removeCollection);
        delayQueue.remove(delayObjects);
    }

    @Test
    public void should_remove_if() {
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
        PriorityBlockingQueue<Object> priorityBlockingQueue = new PriorityBlockingQueue<>();
        TreeSet<Object> treeSet = new TreeSet<>();
        CopyOnWriteArraySet<Object> copyOnWriteArraySet = new CopyOnWriteArraySet<>();

        //type of input element needs to implement Delayed
        DelayQueue<Delayed> delayQueue = new DelayQueue<>();

        SynchronousQueue synchronousQueue = new SynchronousQueue(); //TODO: should be tested for removing an element

        //input element for DelayQueue
        DelayObject delayObject = new DelayObject("TEST", 1);

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
        collections.forEach(this::add);
        delayQueue.add(delayObject);

        //when
        collections.forEach(this::removeIf);
        delayQueue.removeIf(n -> (n.equals(null)));

    }

    private void removeIf(Collection collection) {
        collection.removeIf(n -> (n.equals(true)));
    }

    private boolean removeCollection(Collection collection) {
        Collection<Boolean> b = new ArrayList<>();
        return collection.removeAll(b);
    }

    private void addCollection(Collection collection) {
        Collection<Boolean> b = new ArrayList<>();
        collection.addAll(b);
    }

    private void add(Collection collection) {
        Boolean b = new Boolean(true);
        collection.add(b);
    }

    private void remove(Collection collection) {
        Boolean b = new Boolean(true);
        collection.remove(b);
    }
}
