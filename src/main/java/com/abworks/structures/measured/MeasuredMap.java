package com.abworks.structures.measured;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Machine Coding) Coding round 2:
 * Was asked to design the Map structure which has put(string key, string value),
 * get(string key) along with some instrumentation methods
 * ex- measure_put_load(), measure_get_load().
 * When called these methods should return average calls
 * being made in 5min window.
 */

public class MeasuredMap {

    Map<String, String> baseMap;
    List<Long> getCallInstants;
    List<Long> putCallInstants;

    public MeasuredMap (){
        this.baseMap = new HashMap<>();
        this.getCallInstants = new ArrayList<>();
        this.putCallInstants = new ArrayList<>();
    }

    public String  get(String key){
        getCallInstants.add(System.currentTimeMillis());
        return baseMap.get(key);
    }
    public void put (String key, String value){
        putCallInstants.add(System.currentTimeMillis());
        baseMap.put(key, value);
    }
    public double measureGetLoad(int timeInSeconds){
        int getCalls = getNumInstancesAfterTime(getCallInstants, timeInSeconds);
        return (double) getCalls / timeInSeconds;
    }
    public double measurePutLoad(int timeInSeconds){
        int putCalls = getNumInstancesAfterTime(putCallInstants, timeInSeconds);
        return (double) putCalls / timeInSeconds;
    }
    public int getNumInstancesAfterTime(List<Long> callInstants, int secondsBefore){
        long startTime = Instant.now().minusSeconds(secondsBefore).toEpochMilli();
        int index = getMinIndexGreater(startTime, callInstants);
        return callInstants.size() - index;

    }

    private int getMinIndexGreater(long startTime, List<Long> callInstants) {
        int l = 0 , h = callInstants.size() - 1;
        if (callInstants.get(l) > startTime)
            return l;
        if (callInstants.get(h) < startTime)
            return h;

        while (l < h){
            int mid = (l+h)/2;
            if (callInstants.get(mid) >= startTime){ // the first one is to the left
                h = mid;
            }
            else {
                // < starttime. first one is to the right
                l = mid+1;
            }
        }
        return h;
    }


    public static void main(String[] args) {
        MeasuredMap measuredMap = new MeasuredMap();
        measuredMap.put("ab","cd");
        measuredMap.put("aw" +
                "b","cd");


    }


}
