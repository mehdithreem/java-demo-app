package com.mehdithreem;

import com.mehdithreem.tools.memleak.MemoryLeaker;
import com.mehdithreem.tools.memleak.MemoryLeakerHandler;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import javax.servlet.ServletException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static java.awt.Desktop.getDesktop;

/**
 * Hello world!
 *
 */
public class App
{
    public static void startTomcat() throws ServletException, IOException, URISyntaxException {
        System.out.println("start");
        String jarFile = new File(App.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).toString();
        unzipJar("./demoappjava", jarFile);

        System.out.println("done");

        String rootDir = "./demoappjava/webapps/ROOT";
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
        File additionWebInfClasses = new File("./demoappjava/com/mehdithreem/servlet/");
        WebResourceRoot resources = new StandardRoot(rootCtx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));
        rootCtx.setResources(resources);

        addSpark(tomcat);

        try {
            tomcat.start();
//            getDesktop().browse(new URI("http://localhost:8080"));
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }

    public static void unzipJar(String destinationDir, String jarPath) throws IOException {
        File file = new File(jarPath);
        JarFile jar = new JarFile(file);

        // fist get all directories,
        // then make those directory on the destination Path
        for (Enumeration<JarEntry> enums = jar.entries(); enums.hasMoreElements();) {
            JarEntry entry = (JarEntry) enums.nextElement();

            String fileName = destinationDir + File.separator + entry.getName();
            File f = new File(fileName);

            if (!fileName.startsWith(destinationDir +"/com/mehdithreem") && !fileName.startsWith(destinationDir +"/webapps")) {
                continue;
            }

            System.out.println(fileName);

            if (fileName.endsWith("/")) {
                f.mkdirs();
            }

        }

        System.out.println("folders created");

        //now create all files
        for (Enumeration<JarEntry> enums = jar.entries(); enums.hasMoreElements();) {
            JarEntry entry = (JarEntry) enums.nextElement();

            String fileName = destinationDir + File.separator + entry.getName();
            File f = new File(fileName);

            if (!fileName.startsWith(destinationDir +"/com/mehdithreem") && !fileName.startsWith(destinationDir +"/webapps")) {
                continue;
            }

            System.out.println(fileName);

            if (!fileName.endsWith("/")) {
                InputStream is = jar.getInputStream(entry);
                FileOutputStream fos = new FileOutputStream(f);

                // write contents of 'is' to 'fos'
                while (is.available() > 0) {
                    fos.write(is.read());
                }

                fos.close();
                is.close();
            }
        }
    }

    public static void addSpark(Tomcat tomcat) throws ServletException {
        String sparkDir = "./demoappjava/webapps/spark";
        StandardContext sparkCtx = (StandardContext) tomcat.addWebapp("/spark", new File(sparkDir).getAbsolutePath());
        System.out.println("configuring app with basedir: " + new File("./" + sparkDir).getAbsolutePath());

        File additionWebInfClasses = new File("./demoappjava/com/mehdithreem/api/spark/");
        WebResourceRoot resources = new StandardRoot(sparkCtx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));

//        File staticFiles = new File("target/classes/webapps/spark/");
//        resources.addPreResources(new DirResourceSet(resources, "/", staticFiles.getAbsolutePath(), "/"));
//        sparkCtx.setResources(resources);

        System.out.println(sparkCtx.getDocBase());
    }

    public static void main(String a[]) throws Exception {
        final MemoryLeakerHandler handler = new MemoryLeakerHandler(new MemoryLeaker("Leaker1"));

//                handler.getTarget().setPaused(!newValue);
//                handler.getTarget().setRate(newValue.doubleValue());

        handler.start();
        startTomcat();
    }

}
