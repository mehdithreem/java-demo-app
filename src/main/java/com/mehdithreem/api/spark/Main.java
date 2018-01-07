package com.mehdithreem.api.spark;

import spark.ModelAndView;
import spark.Request;
import spark.servlet.SparkApplication;
import spark.template.velocity.VelocityTemplateEngine;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.staticFiles;

/**
 * Created by mehdithreem on 1/7/2018 AD.
 */
public class Main implements SparkApplication {
    public void init() {
//        exception(Exception.class, (e, req, res) -> e.printStackTrace());

        File staticFiles2 = new File("target/classes/webapps/spark/");
        staticFiles.externalLocation(staticFiles2.getAbsoluteFile().toString());

        get("/", (req, res) -> renderIndex(req));
        get("/hello", (req, res) -> "Hello from spark!");
    }

    private static String renderIndex(Request req) {
        return renderTemplate("templates/index.vm", new HashMap(){{ put("todo", "hel"); }});
    }

    private static String renderTemplate(String template, Map model) {
        return new VelocityTemplateEngine().render(new ModelAndView(model, template));
    }
}
