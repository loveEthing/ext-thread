package com.github.loveething.thread;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BlockPolicy implements RejectedExecutionHandler {

    private int timeOutMilliSeconds = Integer.MAX_VALUE;

    public BlockPolicy() {
        super();
    }

    public BlockPolicy(int timeOutMilliSeconds) {
        if (timeOutMilliSeconds >= 0) {
            this.timeOutMilliSeconds = timeOutMilliSeconds;
        }
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        try {
            boolean offer = executor.getQueue().offer(r, timeOutMilliSeconds, TimeUnit.MILLISECONDS);
            String pool = executor instanceof CommonThreadPoolExecutor
                    ? ((CommonThreadPoolExecutor) executor).getThreadPoolName() : "threadpool";
            if (!offer) {
                throw new RejectedExecutionException("Task " + r.toString() +
                        " blocking timeout, rejected from " + pool);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
