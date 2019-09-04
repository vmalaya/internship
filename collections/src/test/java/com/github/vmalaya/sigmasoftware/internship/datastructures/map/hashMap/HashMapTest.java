package com.github.vmalaya.sigmasoftware.internship.datastructures.map.hashMap;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * If you want unsynchronized or single threaded application
 * you can use {@link java.util.HashMap}
 */
public class HashMapTest {

    @Test
    public void should_put_element() {

        //given
        HashMap<Integer, String> hashMap = new HashMap<>();

        //when
        for (int i = 0; i < 5; i++) {
            hashMap.put(i, "String" + i);
        }

        //then
        Assert.assertTrue(hashMap.keySet().containsAll(Arrays.asList(0, 1, 2, 3, 4)));
    }

    @Test
    public void should_return_set_of_elements() {

        //given
        HashMap<Integer, String> hashMap = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            hashMap.put(i, "String" + i);
        }

        //when
        Set<Map.Entry<Integer, String>> entries = hashMap.entrySet();

        //then
        Assert.assertTrue(entries instanceof Set);
    }

    @Test
    public void should_remove_elements() {

        //given
        HashMap<Integer, String> hashMap = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            hashMap.put(i, "String" + i);
        }

        //when
        for (int i = 0; i < 5; i++) {
            hashMap.remove(i, "String" + i);
        }

        //then
        Assert.assertTrue(hashMap.isEmpty());
    }

    @Test
    public void should_replace_element() {

        //given
        HashMap<Integer, String> hashMap = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            hashMap.put(i, "String" + i);
        }

        //and
        HashMap<Integer, String> resultHashMap = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            if (i == 3) {
                resultHashMap.put(i, "NewValue");
            } else if (i == 4) {
                resultHashMap.put(i, "NewValue");
            } else {
                resultHashMap.put(i, "String" + i);
            }
        }

        //when
        hashMap.replace(3, "NewValue");
        hashMap.replace(4, "String4", "NewValue");

        //then
        Assert.assertArrayEquals(resultHashMap.values().toArray(),
                hashMap.values().toArray());
    }

    @Test
    public void should_get_value_by_key() {

        //given
        HashMap<Integer, String> hashMap = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            hashMap.put(i, "String" + i);
        }

        //when
        String value = hashMap.get(2);

        //then
        Assert.assertEquals("String2", value);
    }

    @Test
    public void should_compute_if_present_element() {

        //given
        HashMap<Integer, String> hashMap = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            hashMap.put(i, "String" + i);
        }

        //when
        hashMap.computeIfPresent(2, (key, val)
                -> val.toUpperCase());

        //then
        Assert.assertEquals("STRING2", hashMap.get(2));
    }

    @Test
    public void should_check_element_containment() {

    }
}
