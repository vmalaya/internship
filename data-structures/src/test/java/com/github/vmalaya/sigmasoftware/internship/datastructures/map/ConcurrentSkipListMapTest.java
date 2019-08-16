package com.github.vmalaya.sigmasoftware.internship.datastructures.map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NavigableMap;
import java.util.concurrent.*;

/**
 * If you need faster in-order traversal and can afford the extra cost for insertion,
 * use the {@link java.util.concurrent.ConcurrentSkipListMap}.
 */
public class ConcurrentSkipListMapTest {

    @Test
    public void should_have_stable_order() throws InterruptedException {

        //given
        NavigableMap<Integer, String> skipListMap = new ConcurrentSkipListMap<>();

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; i++) {
            executorService.execute(() -> {
                ThreadLocalRandom random = ThreadLocalRandom.current();
                for (int j = 0; j < 10000; j++) {
                    skipListMap.put(random.nextInt(), "value");
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        //when
        Iterator<Integer> skipListIter = skipListMap.keySet().iterator();
        int previous = skipListIter.next();

        //then
        while (skipListIter.hasNext()) {
            int current = skipListIter.next();
            Assert.assertTrue(previous < current);
        }
    }
}
