package com.sunlight;

import java.util.concurrent.RejectedExecutionHandler;

public class ThreadPoolMetadata {

    private int corePoolSize;  //核心线程池大小
    private int maxPoolSize;   //最大线程池大小
    private long keepAliveTime; //线程最大空闲时间
    private boolean allowCoreThreadTimeout; //是否给 核心线程 安排 最大空闲时间

    private int activeCount; //当前活动的线程数(执行中的任务数)
    private int currentPoolSize; //当前线程池的大小
    private int largestPoolSize; //曾达到过的最大线程池大小
    private long completedTaskCount; // 完成过的任务数量
    private int waitingTaskCount; //等待执行的任务数

    private long avgExecuteTime;  //线程池任务执行的平均时间

    private RejectedExecutionHandler rejectedExecutionHandler; // 任务拒绝策略


    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public boolean isAllowCoreThreadTimeout() {
        return allowCoreThreadTimeout;
    }

    public void setAllowCoreThreadTimeout(boolean allowCoreThreadTimeout) {
        this.allowCoreThreadTimeout = allowCoreThreadTimeout;
    }

    public int getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(int activeCount) {
        this.activeCount = activeCount;
    }

    public int getCurrentPoolSize() {
        return currentPoolSize;
    }

    public void setCurrentPoolSize(int currentPoolSize) {
        this.currentPoolSize = currentPoolSize;
    }

    public int getLargestPoolSize() {
        return largestPoolSize;
    }

    public void setLargestPoolSize(int largestPoolSize) {
        this.largestPoolSize = largestPoolSize;
    }

    public long getCompletedTaskCount() {
        return completedTaskCount;
    }

    public void setCompletedTaskCount(long completedTaskCount) {
        this.completedTaskCount = completedTaskCount;
    }

    public int getWaitingTaskCount() {
        return waitingTaskCount;
    }

    public void setWaitingTaskCount(int waitingTaskCount) {
        this.waitingTaskCount = waitingTaskCount;
    }

    public long getAvgExecuteTime() {
        return avgExecuteTime;
    }

    public void setAvgExecuteTime(long avgExecuteTime) {
        this.avgExecuteTime = avgExecuteTime;
    }

    public RejectedExecutionHandler getRejectedExecutionHandler() {
        return rejectedExecutionHandler;
    }

    public void setRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler) {
        this.rejectedExecutionHandler = rejectedExecutionHandler;
    }

    @Override
    public String toString() {
        return "ThreadPoolMetadata{" +
                "corePoolSize=" + corePoolSize +
                ", maxPoolSize=" + maxPoolSize +
                ", keepAliveTime=" + keepAliveTime +
                ", allowCoreThreadTimeout=" + allowCoreThreadTimeout +
                ", activeCount=" + activeCount +
                ", currentPoolSize=" + currentPoolSize +
                ", largestPoolSize=" + largestPoolSize +
                ", completedTaskCount=" + completedTaskCount +
                ", waitingTaskCount=" + waitingTaskCount +
                ", avgExecuteTime=" + avgExecuteTime +
                ", rejectedExecutionHandler=" + rejectedExecutionHandler +
                '}';
    }
}
