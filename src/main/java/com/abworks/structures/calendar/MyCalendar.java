package com.abworks.structures.calendar;

import java.util.Map;
import java.util.TreeMap;

public class MyCalendar {

    TreeMap<Integer, Integer> meetings;

    public MyCalendar() {
        meetings = new TreeMap<>();
    }

    public boolean book(int startTime, int endTime) {
        if (meetings.containsKey(startTime))
            return false;
        Map.Entry<Integer, Integer> prev = meetings.ceilingEntry(startTime);
        Map.Entry<Integer, Integer> next = meetings.floorEntry(startTime);
        int[] newMeeting = new int[]{startTime, endTime};
        if (prev != null) {
            if (overlaps(new int[]{prev.getKey(), prev.getValue()}, newMeeting))
                return false;
        }
        if (next != null) {
            if (overlaps(new int[]{next.getKey(), next.getValue()}, newMeeting))
                return false;
        }
        meetings.put(startTime, endTime);
        return true;
    }

    private boolean overlaps(int[] interval1, int[] interval2) {
        return interval1[0] < interval2[1] && interval1[1] > interval2[0];
    }

    public static void main(String[] args) {
        MyCalendar calendarI = new MyCalendar();
        System.out.println(calendarI.book(47,50));
        System.out.println(calendarI.book(33,41));
        System.out.println(calendarI.book(39,45));
        System.out.println(calendarI.book(33,42));
        System.out.println(calendarI.book(25,32));
        System.out.println(calendarI.book(26,35));
        System.out.println(calendarI.book(19,25));//f: should be true
        System.out.println(calendarI.book(3,8));
        System.out.println(calendarI.book(8,13));
        System.out.println(calendarI.book(18,27));

    }
}

/**
 * Your MyCalendar object will be instantiated and called as such:
 * MyCalendar obj = new MyCalendar();
 * boolean param_1 = obj.book(startTime,endTime);
 */


//exp: true,true,false,false,true,false,true,true,true,false
//na2: true,true,false,false,true,false,true,true,true,false
//nac: true,true,false,false,true,false,false,true,true,false
//act: true,true,false,false,true,false,false,true,false,false
