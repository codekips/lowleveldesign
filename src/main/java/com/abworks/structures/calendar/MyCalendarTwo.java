//package com.abworks.structures.calendar;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.TreeMap;
//
//public class MyCalendarTwo {
//    private TreeMap<Integer, List<Integer>> primaryBookings;
//    private TreeMap<Integer, Integer> doubleBookings;
//
//    public MyCalendarTwo() {
//        primaryBookings = new TreeMap<>();
//        doubleBookings = new TreeMap<>();
//    }
//
//    boolean hasOverlap(TreeMap<Integer, Integer> bookingsSrc, int start, int end){
//        var prev = bookingsSrc.floorEntry(start);
//        var next = bookingsSrc.ceilingEntry(start);
//        if (prev != null){
//            if (intervalOverlap(prev.getKey(),prev.getValue(),start,end))
//                return true;
//        }
//        if (next != null){
//            if (intervalOverlap(next.getKey(),next.getValue(),start,end))
//                return true;
//        }
//        return false;
//
//    }
//
//    private boolean intervalOverlap(Integer s1, Integer e1, int s2, int e2) {
//        return (s1<e2)&&(e1>s2);
//    }
//
//    public boolean book(int startTime, int endTime) {
//        // cannot filter out on basis of start time now.
//        if (hasOverlap(doubleBookings, startTime, endTime)){
//            // no overlap so far.
//            // add to primary
//            return false;
//        }
//        else {
//            int[] overlapTime = getOverlap(primaryBookings, startTime, endTime);
//            if (overlapTime != null){
//                doubleBookings.put(overlapTime[0], overlapTime[1]);
//            }
//            primaryBookings.computeIfAbsent(startTime, k -> new ArrayList<>()).add(endTime);
//        }
//        return true;
//    }
//
//    private int[] getOverlap(TreeMap<Integer, List<Integer>> primaryBookings, int startTime, int endTime) {
//
//    }
//
//    public static void main(String[] args) {
//        MyCalendarTwo mycalendarTwo = new MyCalendarTwo();
//        System.out.println(mycalendarTwo.book(10, 20));
//        System.out.println(mycalendarTwo.book(50, 60));
//        System.out.println(mycalendarTwo.book(10, 40));
//        System.out.println(mycalendarTwo.book(5, 15));
//        System.out.println(mycalendarTwo.book(5, 10));
//        System.out.println(mycalendarTwo.book(25, 55));
//    }
//}
//
//
////act: true,true,true,false,true,false
////exp: true,true,true,false,true,true