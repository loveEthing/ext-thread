package com.sunlight;

import java.util.concurrent.*;

public class ExtThreadPoolExecutor extends ThreadPoolExecutor {

    private ThreadLocal<Long> timeLogger = new ThreadLocal<>();
    private BlockingDeque<Long> executeTimeDeque = new LinkedBlockingDeque<>(10000);
    private static long totalExecuteime;
    private String threadPoolName;

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        timeLogger.set(System.currentTimeMillis());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        long start = timeLogger.get();
        long end = System.currentTimeMillis();
        long executeTime = end - start;
        boolean success = executeTimeDeque.offerLast(executeTime);
        if (!success) {
            executeTimeDeque.pollFirst();
            executeTimeDeque.offerFirst(executeTime);
        }
        totalExecuteime += executeTime;
    }

    private long getAvgExecuteTime() {
        return getCompletedTaskCount() == 0 ? 0 : totalExecuteime / getCompletedTaskCount();
    }

    public ThreadPoolMetadata getThreadPoolMetaData() {
        ThreadPoolMetadata threadPoolMetadata = new ThreadPoolMetadata();
        threadPoolMetadata.setCorePoolSize(getCorePoolSize());
        threadPoolMetadata.setMaxPoolSize(getMaximumPoolSize());
        threadPoolMetadata.setKeepAliveTime(getKeepAliveTime(TimeUnit.MILLISECONDS));
        threadPoolMetadata.setAllowCoreThreadTimeout(allowsCoreThreadTimeOut());
        threadPoolMetadata.setActiveCount(getActiveCount());
        threadPoolMetadata.setCurrentPoolSize(getPoolSize());
        threadPoolMetadata.setLargestPoolSize(getLargestPoolSize());
        threadPoolMetadata.setCompletedTaskCount(getCompletedTaskCount());
        threadPoolMetadata.setWaitingTaskCount(getQueue().size());
        threadPoolMetadata.setAvgExecuteTime(getAvgExecuteTime());
        threadPoolMetadata.setRejectedExecutionHandler(getRejectedExecutionHandler());
        return threadPoolMetadata;
    }

    public Long nextExecuteTime() {
        return executeTimeDeque.pollFirst();
    }

    public ExtThreadPoolExecutor(String threadPoolName, int corePoolSize, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, corePoolSize, 0, TimeUnit.SECONDS, workQueue);
        this.threadPoolName = threadPoolName;
    }

    public ExtThreadPoolExecutor(String threadPoolName, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.threadPoolName = threadPoolName;
    }

    public ExtThreadPoolExecutor(String threadPoolName, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        this.threadPoolName = threadPoolName;
    }

    public ExtThreadPoolExecutor(String threadPoolName, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        this.threadPoolName = threadPoolName;
    }

    public ExtThreadPoolExecutor(String threadPoolName, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        this.threadPoolName = threadPoolName;
    }

    public String getThreadPoolName() {
        return threadPoolName;
    }

    public BlockingDeque<Long> getExecuteTimeDeque() {
        return executeTimeDeque;
    }
}
