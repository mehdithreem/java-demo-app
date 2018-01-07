package com.mehdithreem.tools.memleak;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mehdithreem on 1/1/2018 AD.
 */
public class DataGenerator {
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuwyz0123456789";

    static String generateRandomString(Integer count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    static List<Integer> generateRandomIntegers(Integer lenght) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(10);
        list.add(20);
        return list;
    }
}
