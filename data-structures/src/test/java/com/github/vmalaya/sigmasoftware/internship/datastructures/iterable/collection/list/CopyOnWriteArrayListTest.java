package com.github.vmalaya.sigmasoftware.internship.datastructures.iterable.collection.list;

import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A thread-safe version of the ArrayList collection,
 * in which all modifying operations (add, set, etc.)
 * are implemented by creating a fresh copy of the array.
 * So {@link java.util.concurrent.CopyOnWriteArraySet} is efficient if
 * you mostly need to iterate the ArrayList and don't modify it too often.
 */
public class CopyOnWriteArrayListTest {
    @Test(expected = UnsupportedOperationException.class)
    public void givenCopyOnWriteList_whenIterateOverItAndTryToRemoveElement_thenShouldThrowException() {

        //given
        final CopyOnWriteArrayList<Integer> numbers =
                new CopyOnWriteArrayList<>(Arrays.asList(1, 3, 5, 8));

        //when
        Iterator<Integer> iterator = numbers.iterator();
        while (iterator.hasNext()) {
            iterator.remove();
        }
    }
}
