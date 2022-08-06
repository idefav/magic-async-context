package com.idefav.context;

import com.idefav.wrap.spi.WrapSupport;

import java.util.concurrent.Callable;

/**
 * the ContextWrapSupport description.
 *
 * @author wuzishu
 */
public class ContextWrapSupport implements WrapSupport {
    @Override
    public Runnable wrap(Runnable runnable) {
        return Context.current().wrap(runnable);
    }

    @Override
    public <T> Callable<T> wrap(Callable<T> callable) {
        return Context.current().wrap(callable);
    }
}
