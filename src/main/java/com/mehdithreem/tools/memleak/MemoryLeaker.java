package com.mehdithreem.tools.memleak;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mehdithreem on 1/1/2018 AD.
 */
public class MemoryLeaker  {
    private List<LeakedObject> leakedObjects;
    private Double cumulativeRate = 0.0;
    private Boolean paused = true;
    Double rate = 1.0;
    String name;

    public MemoryLeaker(String name) {
        this.leakedObjects = new LinkedList<LeakedObject>();
        this.name = name;
    }

    public Integer doTimes() {
        this.cumulativeRate += rate;
        Integer result = (int) Math.floor(this.cumulativeRate);
        this.cumulativeRate = this.cumulativeRate - result;
        return result;
    }

    public void doJob() {
        if (paused)
            return;

        Integer count = this.doTimes();
        for(Integer i = 0; i < count; i++) {
            leakedObjects.add(new LeakedObject(0, 1024*1024));
        }
    }

    public void setRate(Double createRate) {
        this.rate = createRate;
    }
    public void setPaused(Boolean paused) {
        this.paused = paused;
    }

    public String getName() {
        return name;
    }
    public Double getRate() {
        return rate;
    }
    public Boolean getPaused() {
        return paused;
    }
}
