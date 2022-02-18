package me.abdullah.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorThreads {

    private static final ExecutorService cachedThreadService = Executors.newCachedThreadPool();
    private static final ExecutorService threadPool4 = Executors.newFixedThreadPool(4);

    public static ExecutorService getCachedThreadService(){
        return cachedThreadService;
    }

    public static ExecutorService getThreadPool4(){
        return threadPool4;
    }
}
