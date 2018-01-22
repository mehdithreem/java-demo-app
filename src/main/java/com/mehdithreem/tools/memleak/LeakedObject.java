package com.mehdithreem.tools.memleak;

import com.mehdithreem.utilities.DataGenerator;

import java.util.List;

/**
 * Created by mehdithreem on 1/1/2018 AD.
 */
public class LeakedObject {
    List<Integer> integerArray;
    String stringData;

    public LeakedObject(Integer integerArrayCount, Integer stringDataLength) {
        this.integerArray = DataGenerator.generateRandomIntegers(integerArrayCount);
        this.stringData = DataGenerator.generateRandomString(stringDataLength);
    }
}
