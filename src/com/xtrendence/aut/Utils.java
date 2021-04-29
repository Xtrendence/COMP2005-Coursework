package com.xtrendence.aut;

import java.util.Iterator;

public class Utils {
    public static int iteratorSize(Iterator iterator) {
        int size = 0;
        while(iterator.hasNext()) {
            size += 1;
        }
        return size;
    }
}
