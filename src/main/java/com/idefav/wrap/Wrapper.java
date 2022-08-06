package com.idefav.wrap;

import com.idefav.wrap.spi.SpiHelper;
import com.idefav.wrap.spi.WrapSupport;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * the Wrapper description.
 *
 * @author wuzishu
 */
interface Wrapper {
    /**
     * The Wrap supports.
     */
    List<WrapSupport> WRAP_SUPPORTS = SpiHelper.loadInstanceList(WrapSupport.class);

    /**
     * Get wrapper.
     *
     * @return the wrapper
     */
    static Wrapper get() {
        return DefaultWrapper.INSTANCE;
    }

    /**
     * Wrap runnable.
     *
     * @param runnable the runnable
     * @return the runnable
     */
    default Runnable wrap(Runnable runnable) {
        Runnable tmp = runnable;
        for (WrapSupport wrapSupport : WRAP_SUPPORTS) {
            tmp = wrapSupport.wrap(tmp);
        }
        return tmp;
    }

    /**
     * Wrap callable.
     *
     * @param <T>      the type parameter
     * @param callable the callable
     * @return the callable
     */
    default <T> Callable<T> wrap(Callable<T> callable) {
        Callable<T> tmp = callable;
        for (WrapSupport wrapSupport : WRAP_SUPPORTS) {
            tmp = wrapSupport.wrap(tmp);
        }
        return tmp;
    }
}
