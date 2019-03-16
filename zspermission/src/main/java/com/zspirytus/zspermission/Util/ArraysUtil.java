package com.zspirytus.zspermission.Util;

public class ArraysUtil {

    public static String[] concat(String[] a, String[] b) {
        String[] c = new String[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, c.length - a.length);
        return c;
    }

}
