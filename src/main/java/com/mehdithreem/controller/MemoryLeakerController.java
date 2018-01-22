package com.mehdithreem.controller;

import com.mehdithreem.tools.Handler;
import com.mehdithreem.tools.memleak.MemoryLeaker;

/**
 * Created by mehdithreem on 1/10/2018 AD.
 */
public class MemoryLeakerController {
    private static MemoryLeakerController ourInstance = new MemoryLeakerController();

    public static MemoryLeakerController getInstance() {
        return ourInstance;
    }

    private MemoryLeakerController() {
    }

    private Handler<MemoryLeaker> handler = null;

    private void createHandler() {
        if (handler == null) {
            handler = new Handler<MemoryLeaker>(new MemoryLeaker("TheLeaker"));
        }
    }

    public void start() {
        if (handler == null) {
            createHandler();
        } else if (!handler.isStarted()) {
            handler.start();
        }

        handler.getTarget().setPaused(false);
    }

    public void pause() {
        if (handler == null) {
            return;
        }
        handler.getTarget().setPaused(true);
    }

    public void stop() {
        if (handler == null) {
            return;
        }
        handler.stop();
        handler = null;
    }

    public void setRate(Double newRate) {
        if (handler == null) {
            return;
        }
        handler.getTarget().setRate(newRate);
    }

    public Double getRate() {
        if (handler == null) {
            createHandler();
        }
        return handler.getTarget().getRate();
    }

    public void setSize(Integer newSize) {
        if (handler == null) {
            return;
        }
        handler.getTarget().setObjectSize(newSize);
    }

    public Integer getSize() {
        if (handler == null) {
            createHandler();
        }
        return handler.getTarget().getObjectSize();
    }
}
