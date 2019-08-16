package com.github.vmalaya.sigmasoftware.internship.datastructures.iterable.collection.list;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * All lists elements can be sorted:
 * List:
 *   ArrayList
 *   LinkedList
 *   Vector
 *     Stack
 */
public class ListSortTest {

    @Test
    public void should_sort_list() {

        //given
        List<? extends List<Object>> lists = Arrays.asList(new ArrayList<>(),
                new LinkedList<>(),
                new Vector<>(),
                new Stack<>(),
                new CopyOnWriteArrayList<>());
        lists.forEach(list -> list.addAll(Arrays.asList(2, 1, 4)));

        //and
        Comparator<Object> integerComparator = new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 instanceof Integer) {
                    if (o2 instanceof Integer) {
                        if ((Integer) o1 < (Integer) o2) {
                            return -1;
                        }
                        if ((Integer) o1 > (Integer) o2) {
                            return 1;
                        }
                    }
                }
                return 0;
            }

            @Override
            public boolean equals(Object obj) {
                return this.equals(obj);
            }
        };

        //and
        List<Integer> sortedList = Arrays.asList(1, 2, 4);

        //when
        lists.forEach(list -> list.sort(integerComparator));

        //then
        lists.forEach(list -> Assert.assertArrayEquals(sortedList.toArray(), list.toArray()));
    }
}
