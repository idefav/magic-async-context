package com.idefav.context;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * the Context description.
 *
 * @author wuzishu
 */
public interface Context extends Map<String, Object> {
    static Context current() {
        return ContextStorage.defaultStorage().current();
    }

    static void reset() {
        ContextStorage.defaultStorage().reset();
    }

    default Scope makeCurrent() {
        return ContextStorage.defaultStorage().attach(this);
    }

    Context clone();

    Context merge(Context target);

    default Runnable wrap(Runnable runnable) {
        return () -> {
            try (Scope ignored = makeCurrent()) {
                runnable.run();
            }

        };
    }

    default <V> Callable<V> wrap(Callable<V> callable) {
        return () -> {
            try (Scope ignored = makeCurrent()) {
                return callable.call();
            }
        };
    }
}