package com.idefav.wrap;

import java.util.concurrent.ExecutorService;

/**
 * the DefaultWrappedScheduledExecutorService description.
 *
 * @author wuzishu
 */
class DefaultWrappedScheduledExecutorService extends WrappedScheduledExecutorService {
    /**
     * Instantiates a new Default wrapped scheduled executor service.
     *
     * @param delegate the delegate
     */
    DefaultWrappedScheduledExecutorService(ExecutorService delegate) {
        super(delegate, Wrapper.get());
    }
}
