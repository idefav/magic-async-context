package com.idefav.theadpool;

import java.util.concurrent.ExecutorService;

/**
 * the DefaultWrappedExecutorService description.
 *
 * @author wuzishu
 */
class DefaultWrappedExecutorService extends WrappedExecutorService {
    /**
     * Instantiates a new Default wrapped executor service.
     *
     * @param delegate the delegate
     */
    DefaultWrappedExecutorService(ExecutorService delegate) {
        super(delegate, Wrapper.get());
    }
}
