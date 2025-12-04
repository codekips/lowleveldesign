package com.abworks.structures.hitcounter;

import com.abworks.structures.hitcounter.impl.HitCounterImpl;

public class Driver {
    public static void main(String[] args) throws InterruptedException {
        HitCounter hitCounter = new HitCounterImpl(3);
        Consumer c1 = new Consumer(hitCounter, 10000);
        Consumer c2 = new Consumer(hitCounter, 5000);
        Consumer c3 = new Consumer(hitCounter, 10000);

        long start = System.currentTimeMillis();

        for (int i = 1; i<=3; i++) {
            Thread t1 = new Thread(c1);
            Thread t2 = new Thread(c2);
            Thread t3 = new Thread(c3);
            t1.start();
            t2.start();
            t3.start();

            t1.join();
            t2.join();
            t3.join();
            System.out.println("getHits = " + hitCounter.getHits());
            Thread.sleep(3000);
        }
        long end = System.currentTimeMillis();

        System.out.println(hitCounter.getHits() + String.format("hit is %d ms", (end-start)));
    }
}
