package com.objectfrontier.training.java.jdbc;

public class DateCheck {

    public static void main(String[] args) {
        DateCheck obj = new DateCheck();
        obj.run();

    }

    private void run() {

        log("%s", Person.getDate(1996, 8, 6));
    }

    private static void log(String format, Object... vals) {
        System.out.format(format, vals);
    }
}
