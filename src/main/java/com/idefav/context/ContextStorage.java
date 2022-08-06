package com.idefav.context;

/**
 * the ContextStorage description.
 *
 * @author wuzishu
 */
public interface ContextStorage {
    static ContextStorage defaultStorage(){
        return ThreadLocalContextStorage.INSTANCE;
    }

    Scope attach(Context context);

    Context current();

    void reset();
}
