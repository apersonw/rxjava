package org.rxjava.service.demo.test;

import org.rxjava.service.demo.ClassSingleton;

public class LazyInnerClassSingletonTest {
    public static void main(String[] args) {
        try {
            ClassSingleton instance = ClassSingleton.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
