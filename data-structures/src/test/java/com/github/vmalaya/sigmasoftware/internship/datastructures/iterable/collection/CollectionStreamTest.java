package com.github.vmalaya.sigmasoftware.internship.datastructures.iterable.collection;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;

/**
 * All collections can return stream:
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
public class CollectionStreamTest {

    @Test
    public void should_return_stream() {

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
                Assert.assertTrue(collection.stream() instanceof Stream));
    }

    @Test
    public void should_return_parallel_stream() {

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
                Assert.assertTrue(collection.parallelStream().isParallel()));
    }
}
