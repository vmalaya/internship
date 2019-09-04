package com.github.vmalaya.sigmasoftware.internship.datastructures.iterable.collection.queue.blockingQueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 *  Objects should be acted upon after a given delay.
 */
public class DelayGeneric<T> implements Delayed {
    private T data;
    private long startTime;

    public DelayGeneric(T data, long delayInMilliseconds) {
        this.data = data;
        this.startTime = System.currentTimeMillis() + delayInMilliseconds;;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = startTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if (this.startTime < ((DelayGeneric)o).startTime) return -1;
        return this.startTime == ((DelayGeneric) o).startTime ? 0 : 1;
    }

    @Override
    public String toString() {
        return "DelayObject{" +
                "data='" + data + '\'' +
                ", startTime=" + startTime +
                '}';
    }
}
