package org.rxjava.service.example;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author happy 2019-05-11 21:42
 */
public class JvmDemoTest {
    public static String toMemoryInfo() {
        int freeMemory;
        int totalMemory;
        String calcResult = null;

        Runtime runtime = Runtime.getRuntime();

        for (int i = 0; i < 100000; i++) {
            freeMemory = (int) (runtime.freeMemory() / 1024 / 1024);
            totalMemory = (int) (runtime.totalMemory() / 1024 / 1024);
            calcResult = freeMemory + "M/" + totalMemory + "M(free/total)";
        }

        return calcResult;
    }

    public static void main(String[] args) {
//        long b = System.currentTimeMillis();
//        System.out.println("memory info:" + toMemoryInfo());
//        System.out.println(System.currentTimeMillis() - b);
//        new ConcurrentHashMap<>();

    }
}
