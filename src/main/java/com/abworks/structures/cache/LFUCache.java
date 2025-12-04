package com.abworks.structures.cache;

import jdk.jfr.Frequency;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * Design and implement a data structure for a Least Frequently Used (LFU) cache.
 * Implement the LFUCache class:
 * LFUCache(int capacity) Initializes the object with the capacity of the data structure.
 * int get(int key) Gets the value of the key if the key exists in the cache. Otherwise, returns -1.
 * void put(int key, int value) Update the value of the key if present, or inserts the key if not already present. When the cache reaches its capacity, it should invalidate and remove the least frequently used key before inserting a new item. For this problem, when there is a tie (i.e., two or more keys with the same frequency), the least recently used key would be invalidated.
 * To determine the least frequently used key, a use counter is maintained for each key in the cache. The key with the smallest use counter is the least frequently used key.
 * child has point to parent. remove child. go to parent. if children are empty, then this freq
 * can be completely removed
 * also
 *
 * When a key is first inserted into the cache, its use counter is set to 1 (due to the put operation). The use counter for a key in the cache is incremented either a get or put operation is called on it.
 *
 * The functions get and put must each run in O(1) average time complexity.
 */

// list of buckets
    // remove: 1st elem of first bucket
    // get: find the node. Move node from current place, increment and add to next bucket.
    // if null, add a new bucket in the next elem
//    put: check frequency:
//


public class LFUCache {

    class Node {
        public Node(int key, int val, int freq) {
            this.key = key;
            this.val = val;
            this.freq = freq;
        }

        int key;
        int val;
        int freq;
    }

    Map<Integer, LinkedHashSet<Node>> freqToNodesetMap;
    Map<Integer, Node> keyNodeMap;
    int cap;
    int minFreq = 0;

    public LFUCache(int capacity) {
        this.cap = capacity;
        keyNodeMap = new HashMap<>();
        freqToNodesetMap = new HashMap<>();
    }

    public int get(int key) {
        if (!keyNodeMap.containsKey(key))
            return -1;
        Node existing = keyNodeMap.get(key);
        int val = existing.val;
        incrementUsage(existing);
        return val;
    }

    public void put(int key, int value) {
        if (!keyNodeMap.containsKey(key)){
            if (keyNodeMap.size() == cap)
                evict();
            minFreq = 1;
            Node newNode = new Node(key, value, 1);
            keyNodeMap.put(key, newNode);
            freqToNodesetMap.computeIfAbsent(1, k-> new LinkedHashSet<>()).add(newNode);
        }
        else {
            Node existing = keyNodeMap.get(key);
            existing.val = value;
            incrementUsage(existing);
        }
    }

    private void evict() {
        int freqToRemove = minFreq;
        Node firstNode = freqToNodesetMap.get(freqToRemove).iterator().next();
        freqToNodesetMap.get(freqToRemove).remove(firstNode);
        if (freqToNodesetMap.get(freqToRemove).isEmpty())
            freqToNodesetMap.remove(freqToRemove);
        int nodeKey = firstNode.key;
        keyNodeMap.remove(nodeKey);
    }

    private void incrementUsage(Node existing) {
        int currFreq = existing.freq;
        freqToNodesetMap.get(currFreq).remove(existing);
        if (freqToNodesetMap.get(currFreq).isEmpty()) {
            freqToNodesetMap.remove(currFreq);
            if (currFreq == minFreq)
                minFreq ++;
        }
        Node newFreq = existing;
        newFreq.freq = existing.freq + 1;
        freqToNodesetMap.computeIfAbsent(currFreq+1, k -> new LinkedHashSet<>()).add(newFreq);
    }



}
