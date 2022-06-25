package com.idefav.theadpool;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * the DelegateExecutorService description.
 *
 * @author wuzishu
 */
abstract class DelegateExecutorService implements ExecutorService {
    private final ExecutorService delegate;

    /**
     * Instantiates a new Delegate executor service.
     *
     * @param delegate the delegate
     */
    public DelegateExecutorService(ExecutorService delegate) {
        this.delegate = delegate;
    }

    /**
     * Delegate executor service.
     *
     * @return the executor service
     */
    ExecutorService delegate() {
        return delegate;
    }

    @Override
    public void shutdown() {
        delegate.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return delegate.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return delegate.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return delegate.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return delegate.awaitTermination(timeout, unit);
    }

    @Override
    public abstract <T> Future<T> submit(Callable<T> task);

    @Override
    public abstract <T> Future<T> submit(Runnable task, T result);

    @Override
    public abstract Future<?> submit(Runnable task);

    @Override
    public abstract <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException;

    @Override
    public abstract <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException;

    @Override
    public abstract <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException;

    @Override
    public abstract <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException;

    @Override
    public abstract void execute(Runnable command);
}
