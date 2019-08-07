package com.github.vmalaya.sigmasoftware.internship.datastructures.list.arraylist;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(JUnit4.class)
public class AddingToArrayListTest {

    @Test
    public void should_add_element_to_array_list(){

        //given
        ArrayList arrayList = new ArrayList();
        String element = "str_new";

        // where
       arrayList.add(element);

        //then
        Assert.assertTrue(arrayList.contains(element));
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void should_fail_adding_element_by_given_index (){

        //given
        ArrayList arrayList = new ArrayList();
        String element = "str_new";
        int index = 5;

        //where

        arrayList.add(index, element);

    }

    @Test
    public void should_add_element_by_given_index(){
        //given
        ArrayList arrayList = new ArrayList();
        arrayList.add("str1");
        arrayList.add("str2");
        String element = "str_new";
        int index = 2;

        //where
        arrayList.add(index, element);

        //then
        Assert.assertTrue(arrayList.contains(element));
    }


}
