package com.abworks.structures.hitcounter;

public abstract class HitCounter {

    protected long msToHit;
    ;
    public HitCounter(int secondsToCheck){
        this.msToHit = secondsToCheck * 1000L;
    }

    public abstract void hit();
 /*
 Get hits for the previous N seconds
  */
    public abstract int getHits();
}
