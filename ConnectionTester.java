package com.objectfrontier.training.java.jdbc;

/**
 * @author Lokesh.
 * @since Sep 21, 2018
 */
public class ConnectionTester {

    public static void main(String[] args) {
        ConnectionTester obj = new ConnectionTester();

        try {
            obj.run(args);
        } catch (Throwable t) {
            log(t);
        }
    }

    private void run(String[] args) {

    }

    private static void log(Throwable t) {
        t.printStackTrace(System.err);
    }

    private static void log(String format, Object... vals) {
        System.out.format(format, vals);
    }
}
