package org.panthercode.arctic.core.reflect;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by architect on 20.04.17.
 */
public class MyClassLoader extends URLClassLoader {
    public MyClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public MyClassLoader(URL[] urls) {
        super(urls);
    }

    public MyClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    public static void main(String[] args) throws Exception {
        Path path = Paths.get("/home/architect/Workspace/playground/target/playground-1.0.jar");

        URL[] urls = new URL[]{new URL("jar:file:" + path.toString() + "!/")};

        MyClassLoader loader = new MyClassLoader(urls, ClassLoader.getSystemClassLoader());

        for (String c : ReflectionUtils.extractClassNamesFromJar(path)) {
            System.out.println(c);
            System.out.println(loader.loadClass(c).getCanonicalName());
        }

        loader.loadClass("testinger.Blubbs");

        for (URL url : loader.getURLs()) {
            System.out.println(url);
        }
    }
}
