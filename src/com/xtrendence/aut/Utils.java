package com.xtrendence.aut;

import java.util.*;

public class Utils {
    public static int iteratorSize(Iterator iterator) {
        int size = 0;
        while(iterator.hasNext()) {
            iterator.next();
            size += 1;
        }
        return size;
    }

    public static int sumIntegerArray(int[] array) {
        int sum = 0;
        for(int integer : array) {
            sum += integer;
        }
        return sum;
    }

    public static Restaurant[] listToArray(List<Restaurant> list) {
        Restaurant[] restaurants = new Restaurant[list.size()];
        for(int i = 0; i < list.size(); i++) {
            restaurants[i] = list.get(i);
        }
        return restaurants;
    }

    public static int[] sortArrayAscending(int[] array) {
        Arrays.sort(array);
        return array;
    }

    public static int[] sortArrayDescending(int[] array) {
        Arrays.sort(array);
        return reverse(array);
    }

    public static int[] reverse(int[] array) {
        for(int left = 0, right = array.length - 1; left < right; left++, right--) {
            int temp = array[left];
            array[left]  = array[right];
            array[right] = temp;
        }
        return array;
    }

    public static double distanceBetween(double[] from, double[] to) {
        double x1 = from[0];
        double x2 = to[0];
        double y1 = from[1];
        double y2 = to[1];
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }
}
