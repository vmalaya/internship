package com.github.vmalaya.sigmasoftware.internship.datastructures.iterable.collection.list;

import org.junit.Assert;
import org.junit.Test;
import sun.java2d.pipe.AAShapePipe;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * {@link java.util.LinkedList} allows sequential access of elements.
 * You can re-use exciting iterator to insert and remove elements.
 * One Of the main benefit: fast adding or removing from the head of the list.
 * It may be a good alternative to {@link java.util.ArrayDeque} when you add or remove from the head.
 */
public class LinkedListTest {

    @Test
    public void should_add_first_and_last() {

        //given
        LinkedList linkedList = new LinkedList();

        //and
        String first = "first";
        String last = "last";

        //where
        linkedList.addFirst(first);
        linkedList.addLast(last);

        //then
        Assert.assertArrayEquals(Arrays.asList(first, last).toArray(), linkedList.toArray());
    }

    @Test
    public void should_remove_first_and_last() {

        //given
        LinkedList linkedList = new LinkedList();
        linkedList.addAll(Arrays.asList("first", "middle", "last"));

        //where
        linkedList.removeFirst();
        linkedList.removeLast();

        //then
        Assert.assertFalse(linkedList.containsAll(Arrays.asList("first", "last")));
    }

    @Test
    public void should_add_specified_element_as_a_tail() {

        //given
        LinkedList linkedList = new LinkedList();
        linkedList.addAll(Arrays.asList("first", "middle", "last"));

        //and
        String element = "tail";

        //where
        linkedList.offer(element);

        //then
        Assert.assertTrue(linkedList.getLast().equals(element));
    }

    @Test
    public void should_retrieve_but_do_not_remove_the_head() {

        //given
        LinkedList linkedList = new LinkedList();
        linkedList.addAll(Arrays.asList("first", "middle", "last"));

        //when
        Object first = linkedList.peek();

        //then
        Assert.assertTrue(linkedList.contains(first));
    }

    @Test
    public void should_retrieve_and_remove_the_head() {

        //given
        LinkedList linkedList = new LinkedList();
        linkedList.addAll(Arrays.asList("first", "middle", "last"));

        //when
        Object first = linkedList.poll();

        //then
        Assert.assertFalse(linkedList.contains(first));
    }
}
