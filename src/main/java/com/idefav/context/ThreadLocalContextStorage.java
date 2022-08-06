package com.idefav.context;

/**
 * the ThreadLocalContextStorage description.
 *
 * @author wuzishu
 */
public enum ThreadLocalContextStorage implements ContextStorage {
    INSTANCE;

    private static final ThreadLocal<Context> THREAD_LOCAL_STORAGE = ThreadLocal.withInitial(DefaultContext::new);


    @Override
    public Scope attach(Context toAttach) {
        if (toAttach == null) {
            return Scope.noop();
        }
        Context beforeAttach = current();
        if (toAttach == beforeAttach) {
            return Scope.noop();
        }
        THREAD_LOCAL_STORAGE.set(beforeAttach.merge(toAttach));
        return () -> THREAD_LOCAL_STORAGE.set(beforeAttach);
    }

    @Override
    public Context current() {
        return THREAD_LOCAL_STORAGE.get();
    }

    @Override
    public void reset() {
        THREAD_LOCAL_STORAGE.remove();
    }
}


enum NoopScope implements Scope {
    INSTANCE;

    @Override
    public void close() {

    }
}