package com.github.vmalaya.sigmasoftware.internship.datastructures.iterable.collection.list;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * All lists can add element all collection by given index:
 * List:
 *   ArrayList
 *   LinkedList
 *   Vector
 *     Stack
 */
public class ListAddTest {

    @Test
    public void should_add_element_by_given_index() {

        //given
        int index = 1;

        //and
        String element = "string";

        //and
        List<? extends List<Object>> lists = Arrays.asList(new ArrayList<>(),
                new LinkedList<>(),
                new Vector<>(),
                new Stack<>(),
                new CopyOnWriteArrayList<>());
        lists.forEach(list -> list.addAll(Arrays.asList("string1", "string2")));

        //where
        lists.forEach(list -> list.add(index, element));

        //then
        lists.forEach(list -> Assert.assertEquals(element, list.get(index)));
    }

    @Test
    public void should_add_collection_by_index() {

        //given
        int index = 1;

        //and
        String s = "string";
        List<String> collection = Arrays.asList(s);

        //and
        List<? extends List<Object>> lists = Arrays.asList(new ArrayList<>(),
                new LinkedList<>(),
                new Vector<>(),
                new Stack<>(),
                new CopyOnWriteArrayList<>());
        lists.forEach(list -> list.addAll(Arrays.asList("string1", "string2")));

        //where
        lists.forEach(list -> list.addAll(index, collection));

        //then
        lists.forEach(list -> Assert.assertEquals(s, list.get(index)));
    }
}
