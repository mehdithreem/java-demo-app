package com.mehdithreem.tools;

/**
 * Created by mehdithreem on 1/10/2018 AD.
 */
public class Handler<T extends Doer> implements Runnable {
    private Thread t;
    private T target;
    private Boolean shouldEnd;

    public Handler(T target) {
        this.target = target;
        this.shouldEnd = false;
        System.out.println("Creating " +  target.getName());
    }

    public T getTarget() {
        return target;
    }

    public void setTarget(T target) {
        this.target = target;
    }

    public boolean isStarted() { return t != null; }
    public void stop() {
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
            System.out.println(target.getName() + " interrupted.");
        }
        System.out.println(target.getName() + " end.");
    }

    public void start () {
        System.out.println("Starting " +  target.getName());
        if (t == null) {
            t = new Thread (this, target.getName() + "Thread");
            t.start ();
        }
    }
}
