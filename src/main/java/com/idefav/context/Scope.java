package com.idefav.context;

/**
 * the Scope description.
 *
 * @author wuzishu
 */
@FunctionalInterface
public interface Scope extends AutoCloseable {
    static Scope noop() {
        return NoopScope.INSTANCE;
    }

    @Override
    void close();
}
