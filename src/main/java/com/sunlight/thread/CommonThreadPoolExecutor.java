package com.sunlight.thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;

public class CommonThreadPoolExecutor extends ThreadPoolExecutor implements CommonExecutorService{

    private ThreadLocal<Long> timeLogger = new ThreadLocal<>();
    private String threadPoolName;
    private LongAdder totalExecuteime = new LongAdder(); //执行任务的总时间，用来计算平均时间

    private volatile ThreadPoolMetadata lastThreadPoolMetadata;
    private long metadataLastUpdate;

    private int metadataUpdateInterval = 100;  //线程池基本数据的更新时间间隔,默认100ms
    private ReentrantLock METADATA_LOCK = new ReentrantLock();

    private LongAdder rejectedTaskCount = new LongAdder();  //被拒绝的任务数量

    private int alarmThreshold = 1000; //任务执行时间告警阈值
    private LongAdder overAlarmThresholdTaskCount = new LongAdder(); //超过执行时间告警阈值的任务数量

    private LongAdder exceptionCount = new LongAdder(); //任务执行 异常数量

    /**
     * 100ms 更新一次
     * @return
     */
    @Override
    public ThreadPoolMetadata getThreadPoolMetaData() {
        boolean needUpdate = false;
        if (lastThreadPoolMetadata == null
            || (System.currentTimeMillis() - metadataLastUpdate) >= metadataUpdateInterval) {
            needUpdate = true;
        }
        if (needUpdate && METADATA_LOCK.tryLock()) {
            lastThreadPoolMetadata = newThreadPoolMetadata();
            metadataLastUpdate = System.currentTimeMillis();
        }
        return lastThreadPoolMetadata == null ? newThreadPoolMetadata() : lastThreadPoolMetadata;
    }

    @Override
    public int getCorePoolSize(){
        return super.getCorePoolSize();
    }

    @Override
    public int getActiveTaskCount() {
        return super.getActiveCount();
    }

    @Override
    public String getThreadPoolName() {
        return threadPoolName;
    }

    @Override
    public int getCurrentPoolSize() {
        return super.getPoolSize();
    }

    @Override
    public int getWaitTaskCount() {
        return super.getQueue().size();
    }

    private ThreadPoolMetadata newThreadPoolMetadata() {
        ThreadPoolMetadata threadPoolMetadata = new ThreadPoolMetadata();
        threadPoolMetadata.setPoolName(this.threadPoolName);
        threadPoolMetadata.setCorePoolSize(getCorePoolSize());
        threadPoolMetadata.setMaxPoolSize(getMaximumPoolSize());
        threadPoolMetadata.setKeepAliveTime(getKeepAliveTime(TimeUnit.MILLISECONDS));
        threadPoolMetadata.setAllowCoreThreadTimeout(allowsCoreThreadTimeOut());
        threadPoolMetadata.setActiveCount(getActiveCount());
        threadPoolMetadata.setCurrentPoolSize(getPoolSize());
        threadPoolMetadata.setLargestPoolSize(getLargestPoolSize());
        threadPoolMetadata.setCompletedTaskCount(getCompletedTaskCount());
        threadPoolMetadata.setWaitingTaskCount(getQueue().size());
        threadPoolMetadata.setRejectedTaskCount(getRejectedTaskCount());
        threadPoolMetadata.setAvgExecuteTime(getAvgExecuteTime());
        threadPoolMetadata.setAlarmThreshold(alarmThreshold);
        threadPoolMetadata.setOverAlarmThresholdTaskCount(getOverAlarmThresholdTaskCount());
        threadPoolMetadata.setExceptionCount(getExceptionCount());
        return threadPoolMetadata;
    }

    public CommonThreadPoolExecutor(String threadPoolName, int corePoolSize, int maximumPoolSize, long keepAliveTimeSeconds, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTimeSeconds, TimeUnit.SECONDS, workQueue, threadFactory, handler);
        this.threadPoolName = threadPoolName;
    }

    /**
     * 设置 ThreadPoolMetaData的更新时间间隔
     * 时间单位为毫秒
     * 默认为 100 毫秒
     * @param metadataUpdateInterval
     */
    public void setMetadataUpdateInterval(int metadataUpdateInterval) {
        int temp = metadataUpdateInterval;
        if (temp <= 0) {
            temp = 100;
        }
        this.metadataUpdateInterval = temp;
    }

    /**
     * 设置任务执行时间告警阈值
     * 时间单位为毫秒
     * 默认为 1000 毫秒
     * @param alarmThreshold
     */
    public void setAlarmThreshold(int alarmThreshold) {
        this.alarmThreshold = alarmThreshold;
    }

    public long getAvgExecuteTime() {
        return getCompletedTaskCount() == 0 ? 0 : totalExecuteime.longValue() / getCompletedTaskCount();
    }

    public int getRejectedTaskCount() {
        return rejectedTaskCount.intValue();
    }

    public int getAlarmThreshold() {
        return alarmThreshold;
    }

    public int getOverAlarmThresholdTaskCount() {
        return overAlarmThresholdTaskCount.intValue();
    }

    public int getExceptionCount() {
        return exceptionCount.intValue();
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        timeLogger.set(System.currentTimeMillis());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        if (t != null) {
            exceptionCount.increment();
        }
        long start = timeLogger.get();
        long end = System.currentTimeMillis();
        long executeTime = end - start;
        if (executeTime > alarmThreshold) {
            overAlarmThresholdTaskCount.increment();
        }
        totalExecuteime.add(executeTime);
    }

    @Override
    public void executeTask(Runnable command) throws RejectedTaskException{
        try {
            execute(command);
        } catch (RejectedExecutionException exception) {
            throw new RejectedTaskException(exception.getCause());
        }
    }

    @Override
    public Future<?> submitTask(Runnable task) throws RejectedTaskException {
        try {
            return submit(task);
        } catch (RejectedExecutionException exception) {
            throw new RejectedTaskException(exception.getCause());
        }
    }

    @Override
    public <T> Future<T> submitTask(Runnable task, T result) throws RejectedTaskException {
        try {
            return submit(task, result);
        } catch (RejectedExecutionException exception) {
            throw new RejectedTaskException(exception.getCause());
        }
    }

    @Override
    public <T> Future<T> submitTask(Callable<T> task) throws RejectedTaskException {
        try {
            return submit(task);
        } catch (RejectedExecutionException exception) {
            throw new RejectedTaskException(exception.getCause());
        }
    }

    @Override
    public void execute(Runnable command){
        try {
            super.execute(command);
        } catch (RejectedExecutionException exception) {
            rejectedTaskCount.increment();
            throw new RejectedExecutionException(exception.getCause());
        }
    }
    @Override
    public Future<?> submit(Runnable task) {
        try {
            return super.submit(task);
        } catch (RejectedExecutionException exception) {
            rejectedTaskCount.increment();
            throw new RejectedExecutionException(exception.getCause());
        }
    }
    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        try {
            return super.submit(task, result);
        } catch (RejectedExecutionException exception) {
            rejectedTaskCount.increment();
            throw new RejectedExecutionException(exception.getCause());
        }
    }
    @Override
    public <T> Future<T> submit(Callable<T> task) {
        try {
            return super.submit(task);
        } catch (RejectedExecutionException exception) {
            rejectedTaskCount.increment();
            throw new RejectedExecutionException(exception.getCause());
        }
    }

    protected void finalize() {
        super.shutdown();
    }
}
