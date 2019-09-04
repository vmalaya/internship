package com.github.vmalaya.sigmasoftware.internship.datastructures.iterable.collection.set.enumSet;

import org.junit.Assert;
import org.junit.Test;

import java.util.EnumSet;

/**
 * Can not be used to store any other object except Enum.
 * If you need store enum in set, please, choose this structure/
 */
public class EnumSetTest {

    enum Colors {
        BLUE, RED, GREEN, BLACK
    }

    @Test
    public void should_return_new_enum_set_without_specific_elements() {

        //given
        EnumSet<Colors> colors = EnumSet.allOf(Colors.class);

        //when
        EnumSet<Colors> resultedSet = EnumSet.complementOf(EnumSet.of(Colors.BLUE));

        //then
        Assert.assertFalse(resultedSet.contains(Colors.BLUE));
    }

    @Test
    public void should_copy() {

        //given
        EnumSet<Colors> enumSet = EnumSet.allOf(Colors.class);

        //when
        EnumSet<Colors> copyOfEnumSet = EnumSet.copyOf(enumSet);

        //then
        Assert.assertArrayEquals(enumSet.toArray(), copyOfEnumSet.toArray());
    }

    @Test
    public void should_create_sub_set() {

        //given
        EnumSet<Colors> enumSet = EnumSet.allOf(Colors.class);

        //when
        EnumSet<Colors> subSet = EnumSet.range(Colors.RED, Colors.BLACK);

        //then
        Assert.assertFalse(subSet.contains(Colors.BLUE));
    }

}
