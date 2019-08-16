package com.github.vmalaya.sigmasoftware.internship.datastructures.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * It is efficient way of having objects sorted by some key.
 * You can use {@link java.util.TreeMap} if  random access
 * is important for you application.
 */
public class TreeMapTest {

    @Test
    public void should_put_all_elements_from_given_map() {

        //given
        TreeMap<Integer, String> treeMap = new TreeMap<>();

        //and
        HashMap<Integer, String> hashMap = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            hashMap.put(i, "String" + i);
        }

        //when
        treeMap.putAll(hashMap);

        //then
        Assert.assertArrayEquals(treeMap.entrySet().toArray(), hashMap.entrySet().toArray());
    }

    @Test
    public void should_check_key_containing() {

        //given
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(1, "String");

        //then
        Assert.assertTrue(treeMap.containsKey(1));
    }
}
