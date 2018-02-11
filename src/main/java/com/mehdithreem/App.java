package com.mehdithreem;

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
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static java.awt.Desktop.getDesktop;

public class App {
//    public static String runtimeDirectory = "./demoapp";
    public static String runtimeDirectory = "target/classes";
    public static String defaultPort = "8080";

    public static void main(String a[]) throws Exception {
        startTomcat();
    }

    public static void startTomcat() throws ServletException, IOException, URISyntaxException {
//        System.out.println("Preparing ...");
//
//        String jarFile = new File(App.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).toString();
//        unzipJar(runtimeDirectory, jarFile);

        System.out.println("Starting Tomcat ...");

        String rootDir = runtimeDirectory + "/webapps/ROOT";
        Tomcat tomcat = new Tomcat();

        //The port that we should run on can be set into an environment variable
        //Look for that variable and default to 8080 if it isn't there.
        String webPort = System.getenv("PORT");
        if ((webPort == null) || webPort.isEmpty()) {
            webPort = defaultPort;
        }
        tomcat.setPort(Integer.valueOf(webPort));

        // ROOT context
        StandardContext rootCtx = (StandardContext) tomcat.addWebapp("", new File(rootDir).getAbsolutePath());

        File additionWebInfClasses = new File(runtimeDirectory + "/com/mehdithreem/servlet/");
        WebResourceRoot resources = new StandardRoot(rootCtx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));
        rootCtx.setResources(resources);

        // spark context
        addSpark(tomcat);

        try {
            tomcat.start();
//            getDesktop().browse(new URI("http://localhost:8080"));
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }

    private static void addSpark(Tomcat tomcat) throws ServletException {
        String sparkDir = runtimeDirectory + "/webapps/spark";
        StandardContext sparkCtx = (StandardContext) tomcat.addWebapp("/spark", new File(sparkDir).getAbsolutePath());
        System.out.println("configuring app with basedir: " + new File("./" + sparkDir).getAbsolutePath());

        File additionWebInfClasses = new File(runtimeDirectory + "/com/mehdithreem/api/spark/");
        WebResourceRoot resources = new StandardRoot(sparkCtx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));
    }

    private static void unzipJar(String destinationDir, String jarPath) throws IOException {
        File file = new File(jarPath);
        JarFile jar = new JarFile(file);

        // fist get all directories,
        // then make those directory on the destination Path
        for (Enumeration<JarEntry> enums = jar.entries(); enums.hasMoreElements(); ) {
            JarEntry entry = enums.nextElement();

            String fileName = destinationDir + File.separator + entry.getName();
            File f = new File(fileName);

            if (!fileName.startsWith(destinationDir + "/com/mehdithreem") && !fileName.startsWith(destinationDir + "/webapps")) {
                continue;
            }

            if (fileName.endsWith("/")) {
                f.mkdirs();
            }

        }

        System.out.println("folders created");

        //now create all files
        for (Enumeration<JarEntry> enums = jar.entries(); enums.hasMoreElements(); ) {
            JarEntry entry = (JarEntry) enums.nextElement();

            String fileName = destinationDir + File.separator + entry.getName();
            File f = new File(fileName);

            if (!fileName.startsWith(destinationDir + "/com/mehdithreem") && !fileName.startsWith(destinationDir + "/webapps")) {
                continue;
            }

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
}
