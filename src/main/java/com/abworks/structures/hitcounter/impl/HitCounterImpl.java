package com.abworks.structures.hitcounter.impl;

import com.abworks.structures.hitcounter.HitCounter;

import java.util.LinkedList;


public class HitCounterImpl extends HitCounter {

    LinkedList<Long> hitTimeStamps;
    private final Object lock;
    private final Object deleteLock;
    public HitCounterImpl(int secondsToCheck) {
        super(secondsToCheck);
        hitTimeStamps = new LinkedList<>();
        lock = new Object();
        deleteLock = new Object();
    }

    @Override
    public void hit() {
        synchronized (lock) {
            hitTimeStamps.add(System.currentTimeMillis());
        }
    }

    @Override
    public int getHits() {
        long currTime = System.currentTimeMillis();
        long firstTime = currTime - this.msToHit;

        int currLength = 0;
        synchronized (lock) {
            currLength = hitTimeStamps.size();
        }
        int removed = sequentialRemove(firstTime);
        return currLength - removed;
    }

    private int sequentialRemove(long minTime){
        int removed = 0;
        synchronized (deleteLock) {
            while (!hitTimeStamps.isEmpty() && hitTimeStamps.peek() < minTime) {
                hitTimeStamps.poll();
                removed++;
            }
        }
        return removed;
    }
//    private int search(long firstTime, int maxSize) {
//
//        int l = 0, h = maxSize;
//        while (l<h){
//            int m = (l+h)/2;
//            long midTime = hitTimeStamps.
//            if (midTime > firstTime){
//                // solution on the left
//                h = m - 1;
//            }
//            else if (midTime < firstTime){
//                // solution on the right
//                l = m + 1;
//            }
//            else {
//                // equal, so the first instance would be in the left
//                h = m;
//            }
//        }
//        return l;
//    }
}
