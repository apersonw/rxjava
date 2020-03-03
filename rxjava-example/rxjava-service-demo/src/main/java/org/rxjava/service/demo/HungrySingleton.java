package org.rxjava.service.demo;

public class HungrySingleton {
    private static final HungrySingleton HUNGRY_SINGLETON = new HungrySingleton();
    private HungrySingleton(){}

    public static HungrySingleton getInstance() {
        return HUNGRY_SINGLETON;
    }
}
