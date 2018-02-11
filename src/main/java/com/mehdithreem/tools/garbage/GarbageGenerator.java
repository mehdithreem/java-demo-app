package com.mehdithreem.tools.garbage;

import com.mehdithreem.tools.Doer;

import java.util.*;

/**
 * Created by mehdithreem on 1/10/2018 AD.
 */
public class GarbageGenerator implements Doer {
    private List<LittleGarbage> garbageCan;
    private Integer counter = 0;
    private Double cumulativeRate = 0.0;
    private Boolean paused = true;
    Double rate = 300.0;
    Integer objectSize = 1024;
    String name;

    public GarbageGenerator(String name) {
        this.garbageCan = new LinkedList<>();
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
                ", paused: " + paused.toString() + ", doTimes: " + count.toString() + ", listLen: " + String.valueOf(garbageCan.size()));
        if (paused)
            return;

        for (Integer i = 0; i < count; i++) {
            garbageCan.add(new LittleGarbage(objectSize));
            counter++;
        }

            garbageCan.clear();
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
