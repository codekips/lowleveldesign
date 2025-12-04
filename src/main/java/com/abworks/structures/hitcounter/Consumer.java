package com.abworks.structures.hitcounter;

public class Consumer implements Runnable{

    HitCounter hitCounter;
    private final int numHits;
    public Consumer(HitCounter hitCounter, int numHits){
        this.hitCounter = hitCounter;
        this.numHits = numHits;
    }

    @Override
    public void run() {
        for (int i=0; i<numHits; i++)
            hitCounter.hit();
    }
}
