package com.mehdithreem.api.spark;

import com.mehdithreem.App;
import com.mehdithreem.controller.GarbageGeneratorController;
import com.mehdithreem.controller.MemoryLeakerController;
import spark.ModelAndView;
import spark.Request;
import spark.servlet.SparkApplication;
import spark.template.velocity.VelocityTemplateEngine;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

/**
 * Created by mehdithreem on 1/7/2018 AD.
 */
public class Main implements SparkApplication {
    public void init() {
        exception(Exception.class, (e, req, res) -> e.printStackTrace());

        File staticFiles2 = new File(App.runtimeDirectory + "/webapps/spark/");
        staticFiles.externalLocation(staticFiles2.getAbsoluteFile().toString());

        get("/", (req, res) -> renderIndex(req));

        post("/memleak/start", (req, res) -> {
            MemoryLeakerController.getInstance().start();
            return res;
        });
        post("/memleak/pause", (req, res) -> {
            MemoryLeakerController.getInstance().pause();
            return res;
        });
        post("/memleak/stop", (req, res) -> {
            MemoryLeakerController.getInstance().stop();
            return res;
        });
        post("/memleak/rate", (req, res) -> {
            MemoryLeakerController.getInstance().setRate(Double.valueOf(req.queryParams("rate")));
            return res;
        });
        post("/memleak/size", (req, res) -> {
            MemoryLeakerController.getInstance().setSize(Integer.valueOf(req.queryParams("size")));
            return res;
        });

        post("/garbage/start", (req, res) -> {
            GarbageGeneratorController.getInstance().start();
            return res;
        });
        post("/garbage/pause", (req, res) -> {
            GarbageGeneratorController.getInstance().pause();
            return res;
        });
        post("/garbage/stop", (req, res) -> {
            GarbageGeneratorController.getInstance().stop();
            return res;
        });
        post("/garbage/rate", (req, res) -> {
            GarbageGeneratorController.getInstance().setRate(Double.valueOf(req.queryParams("rate")));
            return res;
        });
        post("/garbage/size", (req, res) -> {
            GarbageGeneratorController.getInstance().setSize(Integer.valueOf(req.queryParams("size")));
            return res;
        });
    }

    private static String renderIndex(Request req) {
        Map data = new HashMap();
        data.put("memleak", MemoryLeakerController.getInstance());
        data.put("garbage", GarbageGeneratorController.getInstance());
        return renderTemplate("templates/index.vm", data);
    }

    private static String renderTemplate(String template, Map model) {
        return new VelocityTemplateEngine().render(new ModelAndView(model, template));
    }
}
