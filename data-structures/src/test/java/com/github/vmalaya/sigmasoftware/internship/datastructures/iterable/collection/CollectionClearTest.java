package com.github.vmalaya.sigmasoftware.internship.datastructures.iterable.collection;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;

/**
 * All collections can remove all elements:
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
public class CollectionClearTest {

    @Test
    public void should_remove_all_elements() {

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

        //and
        collections.forEach(collection -> collection.add("string"));

        //when
        collections.forEach(Collection::clear);

        //then
        collections.forEach(n -> Assert.assertTrue(n.isEmpty()));
    }
}
