package com.mehdithreem.tools.garbage;

import com.mehdithreem.tools.Doer;

import java.util.*;

/**
 * Created by mehdithreem on 1/10/2018 AD.
 */
public class GarbageGenerator implements Doer {
    private List<LittleGarbage> garbageMap;
    private Integer counter = 0;
    private Double cumulativeRate = 0.0;
    private Boolean paused = true;
    Double rate = 300.0;
    Integer objectSize = 1024;
    String name;

    public GarbageGenerator(String name) {
        this.garbageMap = new LinkedList<>();
        this.name = name;
        this.counter = 0;
    }

    public Integer doTimes() {
        this.cumulativeRate += rate;
        Integer result = (int) Math.floor(this.cumulativeRate);
        this.cumulativeRate = this.cumulativeRate - result;
        return result;
    }

    @Override
    public void doJob() {
        Integer count = this.doTimes();
        System.out.println("rate: " + rate.toString() + ", objSize: " + objectSize.toString() +
                ", paused: " + paused.toString() + ", doTimes: " + count.toString() + ", listLen: " + String.valueOf(garbageMap.size()));
        if (paused)
            return;

//        List<Integer> insertedKeys = new ArrayList<Integer>();

        for (Integer i = 0; i < count; i++) {
            garbageMap.add(new LittleGarbage(objectSize));
//            insertedKeys.add(counter);
            counter++;
        }

//        for(Integer i = 0; i < garbageMap.size(); i++) {
//            garbageMap.remove(insertedKeys.get(i));
//            garbageMap.
//        }

        while (!garbageMap.isEmpty()) {
            garbageMap.remove(0);
        }

//        for(LittleGarbage g : garbageMap) {
//            garbageMap.remove(g);
//        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setRate(Double createRate) {
        this.rate = createRate;
    }

    public void setObjectSize(Integer objectSize) {
        this.objectSize = objectSize;
    }

    public void setPaused(Boolean paused) {
        this.paused = paused;
    }

    public Integer getObjectSize() {
        return objectSize;
    }

    public Double getRate() {
        return rate;
    }

    public Boolean getPaused() {
        return paused;
    }
}
