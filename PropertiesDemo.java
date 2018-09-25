package com.objectfrontier.training.java.jdbc;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author Lokesh.
 * @since Sep 21, 2018
 */
public class PropertiesDemo {

    public static void main(String[] args) {
        PropertiesDemo obj = new PropertiesDemo();

        try {
            obj.run(args);
        } catch (Throwable t) {
            log(t);
        }
    }

    private void run(String[] args) {

        Properties properties = new Properties();
        try (InputStream fileStream = getClass().getResourceAsStream("mysqlCredentials.txt")) {
            properties.load(fileStream);
            log("%s%n",properties.getProperty("user"));
            log("%s%n",properties.getProperty("password"));
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private static void log(Throwable t) {
        t.printStackTrace(System.err);
    }

    private static void log(String format, Object... vals) {
        System.out.format(format, vals);
    }
}
