package com.github.vmalaya.sigmasoftware.internship.datastructures.iterable.collection;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;

/**
 * All collections can be converted to Array:
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
public class CollectionToArrayTest {

    @Test
    public void should_convert_to_array_of_objects() {

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
        Object[] objects = collections.toArray();

        //then
        Assert.assertTrue(objects instanceof Object);
    }

    @Test
    public void should_convert_to_array_of_given_type() {

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
        Collection[] objects = collections.toArray(new Collection[0]);

        //then
        Assert.assertTrue(objects instanceof Collection[]);
    }
}
