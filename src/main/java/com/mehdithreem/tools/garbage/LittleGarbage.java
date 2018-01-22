package com.mehdithreem.tools.garbage;

import com.mehdithreem.utilities.DataGenerator;

/**
 * Created by mehdithreem on 1/10/2018 AD.
 */
public class LittleGarbage {
    String stringData;

    public LittleGarbage(Integer stringDataLength) {
        this.stringData = DataGenerator.generateRandomString(stringDataLength);
    }
}
