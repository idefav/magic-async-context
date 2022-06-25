package com.idefav.theadpool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * the WrappedExecutorService description.
 *
 * @author wuzishu
 */
class WrappedExecutorService extends DelegateExecutorService {

    private final Wrapper wrapper;

    /**
     * Instantiates a new Wrapped executor service.
     *
     * @param delegate the delegate
     * @param wrapper  the wrapper
     */
    public WrappedExecutorService(ExecutorService delegate, Wrapper wrapper) {
        super(delegate);
        this.wrapper = wrapper;
    }

    /**
     * Wrapper wrapper.
     *
     * @return the wrapper
     */
    final Wrapper wrapper() {
        return wrapper;
    }

    private <T> Collection<? extends Callable<T>> wrap(Collection<? extends Callable<T>> tasks) {
        List<Callable<T>> wrapped = new ArrayList<>();
        for (Callable<T> task : tasks) {
            wrapped.add(wrapper.wrap(task));
        }
        return wrapped;
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return delegate().submit(wrapper().wrap(task));
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return delegate().submit(wrapper().wrap(task), result);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return delegate().submit(wrapper().wrap(task));
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return delegate().invokeAll(wrap(tasks));
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return delegate().invokeAll(wrap(tasks), timeout, unit);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return delegate().invokeAny(wrap(tasks));
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return delegate().invokeAny(wrap(tasks), timeout, unit);
    }

    @Override
    public void execute(Runnable command) {
        delegate().execute(wrapper().wrap(command));
    }
}
