package com.abworks.structures.sluggish;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 1. Map(Function<Integer, Integer>): SluggishArray -> This method would
 * accept a function which needs to be applied to all the array elements.
 * 2. getIndexOf(int value): Integer -> This method would find the asked
 * value in the array as if all the asked map functions has been applied
 * over the array. (Map functions can be chained).
 */

public class SluggishArray {

    List<Function<Integer, Integer>> fnList;
    List<Integer> array;
    public SluggishArray(List<Integer> arr){
        this.array = arr;
        this.fnList = new ArrayList<>();
    }
    public SluggishArray map(Function<Integer, Integer> mapFn){
        fnList.add(mapFn);
        return this;
    }

//    public Integer getIndexOf(int value){
//        int i=0;
//        for (Integer arrElem: array){
//            int result = apply(array);
//            array.set(i, result);
//        }
//        fnList.clear();
//    }

    /**
     * only do the required operations so that we could find the
     * first value in the array. Ex: 1,2,3,2 was the array
     * and the map was called to multiply each element by 2 and
     * the getIndexOf was called to find value of 4.
     * Then we should only apply the operation till index 1 (0-based).
     */

}
