package com.idefav.theadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * the WrappedScheduledExecutorService description.
 *
 * @author wuzishu
 */
public class WrappedScheduledExecutorService extends WrappedExecutorService implements ScheduledExecutorService {
    /**
     * Instantiates a new Wrapped scheduled executor service.
     *
     * @param delegate the delegate
     * @param wrapper  the wrapper
     */
    public WrappedScheduledExecutorService(ExecutorService delegate, Wrapper wrapper) {
        super(delegate, wrapper);
    }

    @Override
    ScheduledExecutorService delegate() {
        return (ScheduledExecutorService) super.delegate();
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return delegate().schedule(wrapper().wrap(command), delay, unit);
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        return delegate().schedule(wrapper().wrap(callable), delay, unit);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return delegate().scheduleAtFixedRate(wrapper().wrap(command), initialDelay, period, unit);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return delegate().scheduleWithFixedDelay(wrapper().wrap(command), initialDelay, delay, unit);
    }
}
