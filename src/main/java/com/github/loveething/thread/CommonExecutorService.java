package com.github.loveething.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public interface CommonExecutorService extends ExecutorService {

    String getThreadPoolName();

    ThreadPoolMetadata getThreadPoolMetaData();

    int getCorePoolSize();

    int getActiveTaskCount();

    int getCurrentPoolSize();

    int getWaitTaskCount();

    void executeTask(Runnable command) throws RejectedTaskException;

    Future<?> submitTask(Runnable task) throws RejectedTaskException;

    <T> Future<T> submitTask(Runnable task, T result) throws RejectedTaskException;

    <T> Future<T> submitTask(Callable<T> task) throws RejectedTaskException;

}
