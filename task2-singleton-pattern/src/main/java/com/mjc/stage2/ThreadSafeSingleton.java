package com.mjc.stage2;

public class ThreadSafeSingleton {
    public  static volatile ThreadSafeSingleton instance = null;
    private static final Object mutex = new Object();

    private ThreadSafeSingleton() {}

    public static ThreadSafeSingleton getInstance() {
        ThreadSafeSingleton result = instance;

        if (result == null) {
            synchronized (mutex){
                result = instance;
                if (result == null) {
                    instance = result = new ThreadSafeSingleton();
                }
            }
        }

        return result;
    }
}
