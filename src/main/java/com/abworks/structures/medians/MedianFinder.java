package com.abworks.structures.medians;

import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;

public class MedianFinder {
    PriorityQueue<Integer> left ; //max heap
    PriorityQueue<Integer> right ;  //min heap
    double currMedian = 0;

    public MedianFinder() {
        left = new PriorityQueue<>(Comparator.reverseOrder());
        right = new PriorityQueue<>();
    }

    public void addNum(int num) {
        if (left.isEmpty() && right.isEmpty())
            left.add(num);
        else if (num<currMedian)
                left.add(num);
            else
                right.add(num);

    }

    public double findMedian() {
        if (Math.abs(left.size() - right.size()) >= 2) {
            rebalance();
        }
        if (left.size() == right.size()) {
            if (left.isEmpty()) return 0;
            currMedian = (left.peek() + right.peek()) / 2.0d;
        }
        else {
            var larger = left.size() > right.size() ? left: right;
            currMedian = larger.peek();
        }
        return currMedian;
    }

    private void rebalance() {
        var smaller = left.size() > right.size() ? right: left;
        var larger = smaller == left ? right: left;
        while (!(Math.abs(smaller.size() - larger.size())<=1)){
            smaller.offer(larger.poll());
        }
    }

    public static void main(String[] args) {
        MedianFinder medianFinder = new MedianFinder();
        medianFinder.addNum(1);
        medianFinder.addNum(2);
        double median = medianFinder.findMedian();
        assert median == 1.5;
        medianFinder.addNum(3);
        median = medianFinder.findMedian();
        assert median == 2;
        medianFinder.addNum(4);
        medianFinder.addNum(5);
        medianFinder.addNum(6);
        medianFinder.addNum(7);

        medianFinder.addNum(-1);
        medianFinder.addNum(-2);
        median = medianFinder.findMedian();
        assert median == 3;
        System.out.println(median);

        medianFinder.addNum(8);

        median = medianFinder.findMedian();
        System.out.println(median);
        assert median == 4.5;




    }


}
