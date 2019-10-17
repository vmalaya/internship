package com.dao;

public class GreetingDao {
    private static  int counter = 0;

    public static int  count() {
       counter = counter + 1;
       return counter;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        GreetingDao.counter = counter;
    }
}
