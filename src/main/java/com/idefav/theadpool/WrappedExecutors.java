package com.idefav.theadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * the WrappedExecutors description.
 *
 * @author wuzishu
 */
public class WrappedExecutors {
    /**
     * Wrap runnable.
     *
     * @param runnable the runnable
     * @return the runnable
     */
    public static Runnable wrap(Runnable runnable) {
        return Wrapper.get().wrap(runnable);
    }

    /**
     * Wrap callable.
     *
     * @param <V>      the type parameter
     * @param callable the callable
     * @return the callable
     */
    public static <V> Callable<V> wrap(Callable<V> callable) {
        return Wrapper.get().wrap(callable);
    }

    /**
     * Wrap executor.
     *
     * @param executor the executor
     * @return the executor
     */
    public static Executor wrap(Executor executor) {
        return command -> executor.execute(wrap(command));
    }

    /**
     * Wrap executor service.
     *
     * @param executor the executor
     * @return the executor service
     */
    public static ExecutorService wrap(ExecutorService executor) {
        return new DefaultWrappedExecutorService(executor);
    }

    /**
     * Wrap scheduled executor service.
     *
     * @param executor the executor
     * @return the scheduled executor service
     */
    public static ScheduledExecutorService wrap(ScheduledExecutorService executor) {
        return new DefaultWrappedScheduledExecutorService(executor);
    }

    /**
     * New custom thread pool executor executor service.
     *
     * @param coreSize        the core size
     * @param maximumPoolSize the maximum pool size
     * @param keepAliveTime   the keep alive time
     * @param unit            the unit
     * @param workQueue       the work queue
     * @return the executor service
     */
    public static ExecutorService newCustomThreadPoolExecutor(int coreSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        return wrap(new ThreadPoolExecutor(coreSize, maximumPoolSize, keepAliveTime, unit, workQueue));
    }

    /**
     * New custom thread pool executor executor service.
     *
     * @param threadPoolExecutor the thread pool executor
     * @return the executor service
     */
    public static ExecutorService newCustomThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
        return wrap(threadPoolExecutor);
    }

    /**
     * New single thread executor executor service.
     *
     * @return the executor service
     */
    public static ExecutorService newSingleThreadExecutor() {
        return wrap(Executors.newSingleThreadExecutor());
    }

    /**
     * New single thread executor executor service.
     *
     * @param threadFactory the thread factory
     * @return the executor service
     */
    public static ExecutorService newSingleThreadExecutor(ThreadFactory threadFactory) {
        return wrap(Executors.newSingleThreadExecutor(threadFactory));
    }

    /**
     * New cached thread pool executor service.
     *
     * @return the executor service
     */
    public static ExecutorService newCachedThreadPool() {
        return wrap(Executors.newCachedThreadPool());
    }

    /**
     * New cached thread pool executor service.
     *
     * @param threadFactory the thread factory
     * @return the executor service
     */
    public static ExecutorService newCachedThreadPool(ThreadFactory threadFactory) {
        return wrap(Executors.newCachedThreadPool(threadFactory));
    }

    /**
     * New fixed thread pool executor service.
     *
     * @param nThreads      the n threads
     * @param threadFactory the thread factory
     * @return the executor service
     */
    public static ExecutorService newFixedThreadPool(int nThreads, ThreadFactory threadFactory) {
        return wrap(Executors.newFixedThreadPool(nThreads, threadFactory));
    }

    /**
     * New fixed thread pool executor service.
     *
     * @param nThreads the n threads
     * @return the executor service
     */
    public static ExecutorService newFixedThreadPool(int nThreads) {
        return wrap(Executors.newFixedThreadPool(nThreads));
    }

    /**
     * New work stealing pool executor service.
     *
     * @param parallelism the parallelism
     * @return the executor service
     */
    public static ExecutorService newWorkStealingPool(int parallelism) {
        return wrap(Executors.newWorkStealingPool(parallelism));
    }

    /**
     * New work stealing pool executor service.
     *
     * @return the executor service
     */
    public static ExecutorService newWorkStealingPool() {
        return wrap(Executors.newWorkStealingPool());
    }

    /**
     * New scheduled thread pool scheduled executor service.
     *
     * @param corePoolSize the core pool size
     * @return the scheduled executor service
     */
    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
        return wrap(Executors.newScheduledThreadPool(corePoolSize));
    }

    /**
     * New scheduled thread pool scheduled executor service.
     *
     * @param corePoolSize  the core pool size
     * @param threadFactory the thread factory
     * @return the scheduled executor service
     */
    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize, ThreadFactory threadFactory) {
        return wrap(Executors.newScheduledThreadPool(corePoolSize, threadFactory));
    }

    /**
     * Unconfigurable executor service executor service.
     *
     * @param executorService the executor service
     * @return the executor service
     */
    public static ExecutorService unconfigurableExecutorService(ExecutorService executorService) {
        return wrap(Executors.unconfigurableExecutorService(executorService));
    }

    /**
     * Unconfigurable scheduled executor service scheduled executor service.
     *
     * @param scheduledExecutorService the scheduled executor service
     * @return the scheduled executor service
     */
    public static ScheduledExecutorService unconfigurableScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
        return wrap(Executors.unconfigurableScheduledExecutorService(scheduledExecutorService));
    }
}
