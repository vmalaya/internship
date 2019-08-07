package com.github.vmalaya.sigmasoftware.internship.datastructures.list.arraylist;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;

@RunWith(JUnit4.class)
public class InitializationTest {

    @Test
    public void should_create_empty_array_list (){

        // given
        List arrayList;
        int initialCapacity = 10;

        // when
        arrayList = new ArrayList();
        int size = arrayList.size();

        // then
        Assert.assertEquals(0,size);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should__fail_init_array_list_by_given_capacity (){

        //given
        List arrayList;
        int initCapacity = -1;

        // when
        arrayList = new ArrayList(initCapacity);
    }

    @Test
    public void should_create_array_list_from_given_collection (){

        //given

        Collection collection = Arrays.asList(new Integer(1), new Integer(2), new Integer(3));
        List arrayList;

        // where
        arrayList = new ArrayList(collection);

        //then

        Assert.assertTrue(arrayList.containsAll(collection));

    }
}
