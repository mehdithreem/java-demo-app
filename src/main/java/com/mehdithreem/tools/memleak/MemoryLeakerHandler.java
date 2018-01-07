package com.mehdithreem.tools.memleak;

/**
 * Created by mehdithreem on 1/1/2018 AD.
 */
public class MemoryLeakerHandler implements Runnable {
    private Thread t;
    private MemoryLeaker target;
    private Boolean shouldEnd;

    public MemoryLeakerHandler(MemoryLeaker target) {
        this.target = target;
        this.shouldEnd = false;
        System.out.println("Creating " +  target.getName());
    }

    public MemoryLeaker getTarget() {
        return target;
    }

    public void setTarget(MemoryLeaker target) {
        this.target = target;
    }

    public void end() {
        this.shouldEnd = true;
    }

    public void run() {
        try {
            while (!this.shouldEnd) {
                // Let the thread sleep for a while.
                long startTime = System.nanoTime();
                this.target.doJob();
                long executionTime = (System.nanoTime() - startTime) / 1000000;

                if (executionTime < 1000) {
                    Thread.sleep(1000 - executionTime);
                } else {
                    System.out.println("Warning: more than 1 second");
                }
            }
        } catch (InterruptedException e) {
            System.out.println("MemoryLeakThread " +  target.getName() + " interrupted.");
        }
        System.out.println("MemoryLeakThread " +  target.getName() + " end.");
    }

    public void start () {
        System.out.println("Starting " +  target.getName() );
        if (t == null) {
            t = new Thread (this, target.getName());
            t.start ();
        }
    }
}
