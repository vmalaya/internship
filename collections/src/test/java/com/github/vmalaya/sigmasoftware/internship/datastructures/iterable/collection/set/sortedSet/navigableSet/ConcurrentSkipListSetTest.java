package com.github.vmalaya.sigmasoftware.internship.datastructures.iterable.collection.set.sortedSet.navigableSet;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * {@link java.util.concurrent.ConcurrentSkipListSet} is useful when you need
 * a sorted container that will be accessed by multiple threads.
 * You can use it like the equivalents of TreeMap and TreeSet for concurrent code.
 */
public class ConcurrentSkipListSetTest {

    @Test
    public void should_sort_input_elements() {

        //given
        ConcurrentSkipListSet<Integer>
                set = new ConcurrentSkipListSet<Integer>();
        //and
        List<Integer> sortedValues = Arrays.asList(1, 2, 3, 4, 5, 6);

        //when
        for (int i = 6; i > 0; i--) {
            set.add(i);
        }

        //then
        Assert.assertArrayEquals(sortedValues.toArray(), set.toArray());
    }

    @Test
    public void should_return_descending_iterator() {

        //given
        ConcurrentSkipListSet<Integer>
                set = new ConcurrentSkipListSet<Integer>();
        for (int i = 6; i > 0; i--) {
            set.add(i);
        }

        //when
        Iterator<Integer> descendingIterator = set.descendingIterator();

        //then
        Assert.assertEquals(6, descendingIterator.next().intValue());
    }

    @Test
    public void should_perform_relational_operations() {

        //given
        ConcurrentSkipListSet<Integer>
                set = new ConcurrentSkipListSet<Integer>();
        for (int i = 6; i > 0; i--) {
            if (i != 5) {
                set.add(i);
            }
        }

        //when
        Integer ceiling = set.ceiling(5);
        Integer floor = set.floor(5);
        Integer lower = set.lower(5);
        Integer higher = set.higher(5);
        Integer pollFirst = set.pollFirst();
        Integer pollLast = set.pollLast();

        //then
        Assert.assertArrayEquals(Arrays.asList(6, 4, 4, 6, 1, 6).toArray(),
                Arrays.asList(ceiling, floor, lower, higher, pollFirst, pollLast).toArray());
    }

    @Test
    public void should_extract_sub_set() {

        //given
        ConcurrentSkipListSet<Integer>
                set = new ConcurrentSkipListSet<Integer>();
        for (int i = 0; i < 6; i++) {
            set.add(i);
        }

        //when
        NavigableSet<Integer> subSet = set.subSet(2, 5);

        //then
        Assert.assertArrayEquals(Arrays.asList(2, 3, 4).toArray(), subSet.toArray());
    }

    @Test
    public void should_extract_head_set() {

        //given
        ConcurrentSkipListSet<Integer>
                set = new ConcurrentSkipListSet<Integer>();
        for (int i = 0; i < 6; i++) {
            set.add(i);
        }
        //when
        NavigableSet<Integer> subSet = set.headSet(4);

        //then
        Assert.assertArrayEquals(Arrays.asList(0, 1, 2, 3).toArray(), subSet.toArray());
    }

    @Test
    public void should_extract_tail_set() {

        //given
        ConcurrentSkipListSet<Integer>
                set = new ConcurrentSkipListSet<Integer>();
        for (int i = 0; i < 6; i++) {
            set.add(i);
        }
        //when
        NavigableSet<Integer> subSet = set.tailSet(4);

        //then
        Assert.assertArrayEquals(Arrays.asList(4, 5).toArray(), subSet.toArray());
    }

    @Test
    public void should_return_descending_set() {

        //given
        ConcurrentSkipListSet<Integer>
                set = new ConcurrentSkipListSet<Integer>();
        for (int i = 0; i < 6; i++) {
            set.add(i);
        }
        //when
        NavigableSet<Integer> descendingSet = set.descendingSet();

        //then
        Assert.assertEquals(set.last(), descendingSet.first());
    }
}
