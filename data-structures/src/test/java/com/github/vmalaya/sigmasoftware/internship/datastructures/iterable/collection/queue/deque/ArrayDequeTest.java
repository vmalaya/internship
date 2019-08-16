package com.github.vmalaya.sigmasoftware.internship.datastructures.iterable.collection.queue.deque;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Arrays;

/**
 * {@link java.util.ArrayDeque} implementation can be used as a Stack.
 * It is fast for random access each element
 * This implementation is faster then LinkedList
 */
public class ArrayDequeTest {

    @Test
    public void should_add_first_and_last() {

        //given
        ArrayDeque arrayDeque = new ArrayDeque();

        //and
        Integer first = 1;
        Integer last = 2;

        //when
        arrayDeque.addFirst(first);
        arrayDeque.addLast(last);

        //then
        Assert.assertSame(first, arrayDeque.getFirst());
        Assert.assertSame(last, arrayDeque.getLast());
    }

    @Test
    public void should_remove_first_occurrence() {

        //given
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.addAll(Arrays.asList(1, 2, 3, 4, 2));

        //when
        arrayDeque.removeFirstOccurrence(2);

        //then
        Assert.assertArrayEquals(Arrays.asList(1, 3, 4, 2).toArray(), arrayDeque.toArray());
    }

    @Test
    public void should_remove_last_occurrence() {

        //given
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.addAll(Arrays.asList(1, 2, 3, 4, 2));

        //when
        arrayDeque.removeLastOccurrence(2);

        //then
        Assert.assertArrayEquals(Arrays.asList(1, 2, 3, 4).toArray(), arrayDeque.toArray());
    }

    @Test
    public void should_push_and_pop_element() {

        //given
        ArrayDeque arrayDeque = new ArrayDeque();

        //when
        arrayDeque.push(1);
        arrayDeque.pop();

        //then
        Assert.assertTrue(arrayDeque.isEmpty());
    }
}
