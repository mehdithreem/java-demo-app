package com.mehdithreem.utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mehdithreem on 1/1/2018 AD.
 */
public class DataGenerator {
//    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuwyz0123456789";
    private static final String BASE_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuwyz0123456789";
    private static final int BASE_LEN = 60;
    private static long counter = 0;

    static public String generateRandomString(int count) {
        int remaining = count;
        StringBuilder builder = new StringBuilder();
        while (BASE_LEN <= remaining) {
//            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());

            builder.append(BASE_STRING);
            remaining -= BASE_LEN;
        }

        builder.append(BASE_STRING.substring(0, remaining));
        builder.append(counter++);
        return builder.toString();
    }

    static public List<Integer> generateRandomIntegers(Integer lenght) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(10);
        list.add(20);
        return list;
    }
}
