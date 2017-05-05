package com.allets.backend.dashboard.server.utils;

import java.util.Arrays;
import java.util.TreeSet;

/**
 * Created by claude on 2016/2/25.
 */
public class ArrayUtil {
    public static String[] arrayUnique(String string) {
        while (string.contains(",,")) {
            string = string.replace(",,", ",");
        }
        if (string.startsWith(",")) {
            string = string.substring(1, string.length());
        }
        String[] stringsArray = string.split(",");
        TreeSet<String> set = new TreeSet<String>();
        set.addAll(Arrays.asList(stringsArray));
        return set.toArray(new String[0]);
    }
}
