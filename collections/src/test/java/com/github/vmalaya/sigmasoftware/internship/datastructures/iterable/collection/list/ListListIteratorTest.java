package com.github.vmalaya.sigmasoftware.internship.datastructures.iterable.collection.list;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * All lists can return ListIterator instance:
 * List:
 *   ArrayList
 *   LinkedList
 *   Vector
 *     Stack
 */
public class ListListIteratorTest {

    @Test
    public void should_return_list_iterator() {

        //given
        List<? extends List<Object>> lists = Arrays.asList(new ArrayList<>(),
                new LinkedList<>(),
                new Vector<>(),
                new Stack<>(),
                new CopyOnWriteArrayList<>());

        //then
        lists.forEach(list -> Assert.assertTrue(list.listIterator() instanceof ListIterator));
    }

    @Test
    public void should_return_list_iterator_by_index() {

        //given
        int index = 1;

        //and
        List<? extends List<Object>> lists = Arrays.asList(new ArrayList<>(),
                new LinkedList<>(),
                new Vector<>(),
                new Stack<>(),
                new CopyOnWriteArrayList<>());
        lists.forEach(list -> list.addAll(Arrays.asList(2, 1, 4)));

        //then
        lists.forEach(list -> Assert.assertEquals(list.get(index), list.listIterator(index).next()));
    }
}
