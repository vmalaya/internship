package com.github.vmalaya.sigmasoftware.internship.datastructures.iterable;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;

/**
 * All collections are iterable:
 * List:
 *   ArrayList
 *   LinkedList
 *   Vector
 *     Stack
 * Set:
 *   HashSet
 *   LinkedHashSet
 *   TreeSet
 *   ConcurrentSkipListSet
 *   RegularEnumSet
 *   CopyOnWriteArraySet
 * Queue:
 *   ConcurrentLinkedQueue
 *   PriorityQueue
 *   LinkedBlockingDeque
 *   ArrayDeque
 *   ConcurrentLinkedDeque
 *   ArrayBlockingQueue
 *   LinkedTransferQueue
 *   LinkedBlockingQueue
 *   PriorityBlockingQueue
 *   LinkedTransferQueue
 */
public class IterableTest {

    @Test
    public void should_do_action_for_each_element() {

        //given
        int capacity = 1;
        List<? extends Collection<Object>> collections = Arrays.asList(new ArrayList<>(),
                new LinkedList<>(),
                new Vector<>(),
                new Stack<>(),
                new CopyOnWriteArrayList<>(),
                new ArrayBlockingQueue<>(capacity),
                new PriorityQueue<>(),
                new ConcurrentLinkedQueue<>(),
                new LinkedBlockingQueue<>(),
                new LinkedTransferQueue<>(),
                new ArrayDeque<>(),
                new ConcurrentLinkedDeque<>(),
                new LinkedBlockingDeque<>(),
                new HashSet<>(),
                new LinkedHashSet<>(),
                new ConcurrentSkipListSet<>(),
                new PriorityBlockingQueue<>(),
                new TreeSet<>(),
                new CopyOnWriteArraySet<>());

        //then
        collections.forEach(collection -> Assert.assertTrue(true));
    }

    @Test
    public void should_return_iterator() {

        //given
        int capacity = 1;
        List<? extends Collection<Object>> collections = Arrays.asList(new ArrayList<>(),
                new LinkedList<>(),
                new Vector<>(),
                new Stack<>(),
                new CopyOnWriteArrayList<>(),
                new ArrayBlockingQueue<>(capacity),
                new PriorityQueue<>(),
                new ConcurrentLinkedQueue<>(),
                new LinkedBlockingQueue<>(),
                new LinkedTransferQueue<>(),
                new ArrayDeque<>(),
                new ConcurrentLinkedDeque<>(),
                new LinkedBlockingDeque<>(),
                new HashSet<>(),
                new LinkedHashSet<>(),
                new ConcurrentSkipListSet<>(),
                new PriorityBlockingQueue<>(),
                new TreeSet<>(),
                new CopyOnWriteArraySet<>());

        //then
        collections.forEach(collection ->
                Assert.assertTrue(collection.iterator() instanceof Iterator));
    }

    @Test
    public void should_return_spliterator() {

        //given
        int capacity = 1;
        List<? extends Collection<Object>> collections = Arrays.asList(new ArrayList<>(),
                new LinkedList<>(),
                new Vector<>(),
                new Stack<>(),
                new CopyOnWriteArrayList<>(),
                new ArrayBlockingQueue<>(capacity),
                new PriorityQueue<>(),
                new ConcurrentLinkedQueue<>(),
                new LinkedBlockingQueue<>(),
                new LinkedTransferQueue<>(),
                new ArrayDeque<>(),
                new ConcurrentLinkedDeque<>(),
                new LinkedBlockingDeque<>(),
                new HashSet<>(),
                new LinkedHashSet<>(),
                new ConcurrentSkipListSet<>(),
                new PriorityBlockingQueue<>(),
                new TreeSet<>(),
                new CopyOnWriteArraySet<>());

        //then
        collections.forEach(collection ->
                Assert.assertTrue(collection.spliterator() instanceof Spliterator));
    }
}
