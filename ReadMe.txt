1. 目标
提供通用方便的可监控的线程池
2. 设计
通过在原有ThreadPoolFactory基础上进行扩展。
线程池包含元数据信息;
线程池监控者通过处理元数据信息,生成可监控的web图表;
有问题的线程进行可以通过外部关闭;
线程池部分元数据信息支持动态修改;
3. 实现
CommonExecutorService接口 继承 ExecutorService接口，并提供更多通用的查询接口，并包装了 执行任务的三个方法；
CommonThreadPoolExecutor继承 ThreadPoolExecutor类 并实现CommonExecutorService接口，并增加一些数据统计功能；
CommonThreadPoolFactory类 担当 java.util.concurrent.Executors 的角色，负责创建CommonExecutorService线程池，相对于Executors, CommonThreadPoolFactory类提供了更加简洁通过的创建线程池方法。
ThreadPoolMetadata 充当线程池的 元数据信息，使监控更加便捷。



