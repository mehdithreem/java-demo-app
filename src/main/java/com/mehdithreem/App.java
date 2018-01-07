package com.mehdithreem;

import com.mehdithreem.tools.memleak.MemoryLeaker;
import com.mehdithreem.tools.memleak.MemoryLeakerHandler;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import javax.servlet.ServletException;
import java.io.File;

import static java.awt.Desktop.getDesktop;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!!!" );
        launch(args);
    }

    public void startTomcat() throws ServletException {
        String rootDir = "src/main/resources/webapps/ROOT";
        Tomcat tomcat = new Tomcat();

        //The port that we should run on can be set into an environment variable
        //Look for that variable and default to 8080 if it isn't there.
        String webPort = System.getenv("PORT");
        if((webPort == null) || webPort.isEmpty()) {
            webPort = "8080";
        }

        tomcat.setPort(Integer.valueOf(webPort));

        StandardContext rootCtx = (StandardContext) tomcat.addWebapp("", new File(rootDir).getAbsolutePath());
        System.out.println("configuring app with basedir: " + new File("./" + rootDir).getAbsolutePath());

        // Declare an alternative location for your "WEB-INF/classes" dir
        // Servlet 3.0 annotation will work
        File additionWebInfClasses = new File("target/classes/com/mehdithreem/servlet/");
        WebResourceRoot resources = new StandardRoot(rootCtx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));
        rootCtx.setResources(resources);

        System.out.println(rootCtx.getDocBase());

        addSpark(tomcat);

        try {
            tomcat.start();
//            getDesktop().browse(new URI("http://localhost:8080"));
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }

    public void addSpark(Tomcat tomcat) throws ServletException {
        String sparkDir = "src/main/resources/webapps/spark";
        StandardContext sparkCtx = (StandardContext) tomcat.addWebapp("/spark", new File(sparkDir).getAbsolutePath());
        System.out.println("configuring app with basedir: " + new File("./" + sparkDir).getAbsolutePath());

        File additionWebInfClasses = new File("target/classes/com/mehdithreem/api/spark/");
        WebResourceRoot resources = new StandardRoot(sparkCtx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));

//        File staticFiles = new File("target/classes/webapps/spark/");
//        resources.addPreResources(new DirResourceSet(resources, "/", staticFiles.getAbsolutePath(), "/"));
//        sparkCtx.setResources(resources);

        System.out.println(sparkCtx.getDocBase());
    }

    public void start(Stage primaryStage) throws Exception {
        final MemoryLeakerHandler handler = new MemoryLeakerHandler(new MemoryLeaker("Leaker1"));

//                handler.getTarget().setPaused(!newValue);
//                handler.getTarget().setRate(newValue.doubleValue());

        handler.start();
        startTomcat();
    }
}
