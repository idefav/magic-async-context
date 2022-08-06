package com.idefav.wrap.spi;

import java.util.concurrent.Callable;

/**
 * the WrapSupport description.
 *
 * @author wuzishu
 */
public interface WrapSupport {

    /**
     * Wrap runnable.
     *
     * @param runnable the runnable
     * @return the runnable
     */
    Runnable wrap(Runnable runnable);

    /**
     * Wrap callable.
     *
     * @param <T>      the type parameter
     * @param callable the callable
     * @return the callable
     */
    <T> Callable<T> wrap(Callable<T> callable);
}
