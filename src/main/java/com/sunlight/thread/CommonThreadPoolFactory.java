package com.sunlight.thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CommonThreadPoolFactory {

    private final static String DEFAULT_POOL_NAME = "Unnamed-threadpool";
    private final static AtomicInteger poolNumber = new AtomicInteger(1);

    private final static String DEFAULT_THREAD_NAME_PREFIX = "Unnamed-";

    private final AtomicInteger threadPoolNumber = new AtomicInteger(1);

    public static CommonExecutorService newSingleThreadExecutor() {
        return newFixedThreadExecutor(1);
    }

    public static CommonExecutorService newSingleThreadExecutor(String poolName,String threadNamePrefix) {
        return newFixedThreadExecutor(1, poolName, threadNamePrefix);
    }

    public static CommonExecutorService newSingleThreadExecutor(int maxWaitingTask) {
        return newFixedThreadExecutor(1, maxWaitingTask);
    }

    public static CommonExecutorService newSingleThreadExecutor(int maxWaitingTask,String poolName,String threadNamePrefix) {
        return newFixedThreadExecutor(1, maxWaitingTask, poolName, threadNamePrefix);
    }

    public static CommonExecutorService newFixedThreadExecutor(int nThreads) {
        return newFixedThreadExecutor(nThreads, DEFAULT_POOL_NAME, DEFAULT_THREAD_NAME_PREFIX);
    }

    public static CommonExecutorService newFixedThreadExecutor(int nThreads,int maxWaitingTask) {
        return newFixedThreadExecutor(nThreads, maxWaitingTask, DEFAULT_POOL_NAME, DEFAULT_THREAD_NAME_PREFIX);
    }

    public static CommonExecutorService newFixedThreadExecutor(int nThreads, String poolName,String threadNamePrefix) {
        return newFixedThreadExecutor(nThreads, Integer.MAX_VALUE ,poolName, threadNamePrefix);
    }

    public static CommonExecutorService newFixedThreadExecutor(int nThreads, int maxWaitingTask, String poolName, String threadNamePrefix) {
        return newFixedThreadExecutor(nThreads, maxWaitingTask, poolName, threadNamePrefix, new ThreadPoolExecutor.AbortPolicy());
    }

    public static CommonExecutorService newFixedThreadExecutor(int nThreads, int maxWaitingTask, String poolName, String threadNamePrefix, RejectedExecutionHandler handler) {
        return newDynamicThreadExecutor(nThreads, maxWaitingTask, nThreads, 0, poolName, threadNamePrefix, handler);
    }

    /**
     * 动态线程池, 任务队列容量为0 , 拒绝策略为 直接丢弃
     * @param nThreads
     * @param maxThreads
     * @param idleKeepAliveSeconds
     * @param poolName
     * @param threadNamePrefix
     * @return
     */
    public static CommonExecutorService newDynamicThreadExecutor(int nThreads, int maxThreads,int idleKeepAliveSeconds,
                                                                 String poolName,String threadNamePrefix) {
        return newDynamicThreadExecutor(nThreads, 0, maxThreads, idleKeepAliveSeconds, poolName, threadNamePrefix);
    }

    /**
     * 动态线程池 , 拒绝策略为 直接丢弃
     * @param nThreads
     * @param maxWaitingTask 任务队列容量
     * @param maxThreads
     * @param idleKeepAliveSeconds
     * @param poolName
     * @param threadNamePrefix
     * @return
     */
    public static CommonExecutorService newDynamicThreadExecutor(int nThreads, int maxWaitingTask, int maxThreads,int idleKeepAliveSeconds,
                                                                 String poolName,String threadNamePrefix) {
        return newDynamicThreadExecutor(nThreads, maxWaitingTask, maxThreads, idleKeepAliveSeconds, poolName, threadNamePrefix, new ThreadPoolExecutor.AbortPolicy());
    }

    public static CommonExecutorService newDynamicThreadExecutor(int nThreads,int maxWaitingTask,
                                                                 int maxThreads,int idleKeepAliveSeconds,
                                                                 String poolName,String threadNamePrefix,
                                                                 RejectedExecutionHandler handler) {
        if (nThreads <= 0) {
            throw new IllegalArgumentException("nThreads必须大于0");
        }
        if (maxWaitingTask < 0) {
            throw new IllegalArgumentException("maxWaitingTask不能小于0");
        }
        if (poolName == null || threadNamePrefix == null) {
            throw new IllegalArgumentException("poolName和threadNamePrefix不能为空");
        }
        if (handler == null) {
            throw new IllegalArgumentException("RejectedExecutionHandler不能为空");
        }
        BlockingQueue<Runnable> blockingQueue;
        if (maxWaitingTask == 0) {
            blockingQueue = new SynchronousQueue();
        } else {
            blockingQueue = new LinkedBlockingDeque<>(maxWaitingTask);
        }
        return new CommonThreadPoolExecutor(poolName + poolNumber.incrementAndGet(),
                nThreads,
                maxThreads,idleKeepAliveSeconds,
                blockingQueue,
                new CommonThreadFactory(threadNamePrefix),
                handler);
    }

    /**
     * 阻塞的任务队列(不存储任务)线程池,线程池满时,新任务阻塞(非公平阻塞队列)
     * @param nThreads
     * @param poolName
     * @param threadNamePrefix
     * @return
     */
    public static CommonExecutorService newBlockingThreadExecutor(int nThreads,String poolName,String threadNamePrefix) {
        return newBlockingThreadExecutor(nThreads, -1, poolName, threadNamePrefix);
    }

    public static CommonExecutorService newBlockingThreadExecutor(int nThreads, int blockTimeOutMilliSeconds, String poolName, String threadNamePrefix) {
        return newBlockingThreadExecutor(nThreads, 0,blockTimeOutMilliSeconds, poolName, threadNamePrefix);
    }

    public static CommonExecutorService newBlockingThreadExecutor(int nThreads, int maxWaitingTask,int blockTimeOutMilliSeconds, String poolName, String threadNamePrefix) {
        if (nThreads <= 0) {
            throw new IllegalArgumentException("nThreads必须大于0");
        }
        if (maxWaitingTask < 0) {
            throw new IllegalArgumentException("maxWaitingTask不能小于0");
        }
        if (poolName == null || threadNamePrefix == null) {
            throw new IllegalArgumentException("poolName和threadNamePrefix不能为空");
        }
        BlockingQueue<Runnable> blockingQueue;
        if (maxWaitingTask == 0) {
            blockingQueue = new SynchronousQueue();
        } else {
            blockingQueue = new LinkedBlockingDeque<>(maxWaitingTask);
        }
        return new CommonThreadPoolExecutor(poolName + poolNumber.incrementAndGet(),
                nThreads,
                nThreads, 0,
                blockingQueue,
                new CommonThreadFactory(threadNamePrefix),
                new BlockPolicy(blockTimeOutMilliSeconds));
    }
}
