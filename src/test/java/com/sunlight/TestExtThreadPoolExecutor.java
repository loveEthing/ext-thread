package com.sunlight;

import org.junit.Test;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class TestExtThreadPoolExecutor {


    @Test
    public void testExtThreadPoolExecutor() throws InterruptedException {
        ExtThreadPoolExecutor extThreadPoolExecutor = new ExtThreadPoolExecutor(2, 5, 0, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(2));
        extThreadPoolExecutor.execute(()->{
            System.out.println("1任务执行了。。。");
            try {
                TimeUnit.SECONDS.sleep(600);
            } catch (InterruptedException e) {
            }
        });

        extThreadPoolExecutor.execute(()->{
            System.out.println("2任务执行了。。。");
            try {
                TimeUnit.SECONDS.sleep(600);
            } catch (InterruptedException e) {
            }
        });

        TimeUnit.SECONDS.sleep(5);
        System.out.println(extThreadPoolExecutor.getThreadPoolMetaData());

        extThreadPoolExecutor.execute(()->{
            System.out.println("3任务执行了。。。");
            try {
                TimeUnit.SECONDS.sleep(600);
            } catch (InterruptedException e) {
            }
        });

        TimeUnit.SECONDS.sleep(5);
        System.out.println(extThreadPoolExecutor.getThreadPoolMetaData());

        extThreadPoolExecutor.execute(()->{
            System.out.println("4任务执行了。。。");
            try {
                TimeUnit.SECONDS.sleep(600);
            } catch (InterruptedException e) {
            }
        });
        TimeUnit.SECONDS.sleep(5);
        System.out.println(extThreadPoolExecutor.getThreadPoolMetaData());

        extThreadPoolExecutor.execute(()->{
            System.out.println("5任务执行了。。。");
            try {
                TimeUnit.SECONDS.sleep(600);
            } catch (InterruptedException e) {
            }
        });
        TimeUnit.SECONDS.sleep(5);
        System.out.println(extThreadPoolExecutor.getThreadPoolMetaData());

    }

}
