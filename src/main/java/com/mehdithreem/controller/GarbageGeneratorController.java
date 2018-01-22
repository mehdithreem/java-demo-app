package com.mehdithreem.controller;

import com.mehdithreem.tools.Handler;
import com.mehdithreem.tools.garbage.GarbageGenerator;

/**
 * Created by mehdithreem on 1/10/2018 AD.
 */
public class GarbageGeneratorController {
    private static GarbageGeneratorController ourInstance = new GarbageGeneratorController();

    public static GarbageGeneratorController getInstance() {
        return ourInstance;
    }

    private GarbageGeneratorController() {
    }

    private Handler<GarbageGenerator> handler = null;

    private void createHandler() {
        if (handler == null) {
            handler = new Handler<GarbageGenerator>(new GarbageGenerator("TheHuman"));
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
