package com.xtrendence.aut;

import java.util.Iterator;
import java.util.List;

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
}
