package com.github.loveething.thread;

public class ThreadPoolMetadata {

    private String poolName;   //线程池名称
    private int corePoolSize;  //核心线程池大小
    private int maxPoolSize;   //最大线程池大小
    private long keepAliveTime; //线程最大空闲时间,时间单位为毫秒
    private boolean allowCoreThreadTimeout; //是否给 核心线程 安排 最大空闲时间

    private int activeCount; //当前活动的线程数(执行中的任务数)
    private int currentPoolSize; //当前线程池的大小
    private int largestPoolSize; //曾达到过的最大线程池大小
    private long completedTaskCount; // 完成过的任务数量
    private int waitingTaskCount; //等待执行的任务数
    private int rejectedTaskCount; //被拒绝的任务数量

    private long avgExecuteTime;  //线程池任务执行的平均时间

    private int alarmThreshold;   //任务执行时间 告警阈值, 时间单位为 毫秒
    private int overAlarmThresholdTaskCount; // 任务执行时间 超过 告警阈值的数量

    private int exceptionCount; // 任务执行 异常数量

    public String getPoolName() {
        return poolName;
    }

    void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public long getKeepAliveTime() {
        return keepAliveTime;
    }

    void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public boolean isAllowCoreThreadTimeout() {
        return allowCoreThreadTimeout;
    }

    void setAllowCoreThreadTimeout(boolean allowCoreThreadTimeout) {
        this.allowCoreThreadTimeout = allowCoreThreadTimeout;
    }

    public int getActiveCount() {
        return activeCount;
    }

    void setActiveCount(int activeCount) {
        this.activeCount = activeCount;
    }

    public int getCurrentPoolSize() {
        return currentPoolSize;
    }

    void setCurrentPoolSize(int currentPoolSize) {
        this.currentPoolSize = currentPoolSize;
    }

    public int getLargestPoolSize() {
        return largestPoolSize;
    }

    void setLargestPoolSize(int largestPoolSize) {
        this.largestPoolSize = largestPoolSize;
    }

    public long getCompletedTaskCount() {
        return completedTaskCount;
    }

    void setCompletedTaskCount(long completedTaskCount) {
        this.completedTaskCount = completedTaskCount;
    }

    public int getWaitingTaskCount() {
        return waitingTaskCount;
    }

    void setWaitingTaskCount(int waitingTaskCount) {
        this.waitingTaskCount = waitingTaskCount;
    }

    public int getRejectedTaskCount() {
        return rejectedTaskCount;
    }

    void setRejectedTaskCount(int rejectedTaskCount) {
        this.rejectedTaskCount = rejectedTaskCount;
    }

    public long getAvgExecuteTime() {
        return avgExecuteTime;
    }

    void setAvgExecuteTime(long avgExecuteTime) {
        this.avgExecuteTime = avgExecuteTime;
    }

    public int getAlarmThreshold() {
        return alarmThreshold;
    }

    void setAlarmThreshold(int alarmThreshold) {
        this.alarmThreshold = alarmThreshold;
    }

    public int getOverAlarmThresholdTaskCount() {
        return overAlarmThresholdTaskCount;
    }

    void setOverAlarmThresholdTaskCount(int overAlarmThresholdTaskCount) {
        this.overAlarmThresholdTaskCount = overAlarmThresholdTaskCount;
    }

    public int getExceptionCount() {
        return exceptionCount;
    }

    void setExceptionCount(int exceptionCount) {
        this.exceptionCount = exceptionCount;
    }

    @Override
    public String toString() {
        return "ThreadPoolMetadata{" +
                "poolName='" + poolName + '\'' +
                ", corePoolSize=" + corePoolSize +
                ", maxPoolSize=" + maxPoolSize +
                ", keepAliveTime=" + keepAliveTime +
                ", allowCoreThreadTimeout=" + allowCoreThreadTimeout +
                ", activeCount=" + activeCount +
                ", currentPoolSize=" + currentPoolSize +
                ", largestPoolSize=" + largestPoolSize +
                ", completedTaskCount=" + completedTaskCount +
                ", waitingTaskCount=" + waitingTaskCount +
                ", rejectedTaskCount=" + rejectedTaskCount +
                ", avgExecuteTime=" + avgExecuteTime +
                ", alarmThreshold=" + alarmThreshold +
                ", overAlarmThresholdTaskCount=" + overAlarmThresholdTaskCount +
                ", exceptionCount=" + exceptionCount +
                '}';
    }
}
