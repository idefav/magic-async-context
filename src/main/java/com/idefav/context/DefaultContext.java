package com.idefav.context;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * the DefaultContext description.
 *
 * @author wuzishu
 */
public class DefaultContext implements Context {

    private final ConcurrentHashMap<String, Object> CONTEXT_STORAGE = new ConcurrentHashMap<>();

    public ConcurrentHashMap<String, Object> storage() {
        return CONTEXT_STORAGE;
    }

    @Override
    public int size() {
        return storage().size();
    }

    @Override
    public boolean isEmpty() {
        return storage().isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return storage().containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return storage().containsKey(value);
    }

    @Override
    public Object get(Object key) {
        return storage().get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return storage().put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return storage().remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        storage().putAll(m);
    }

    @Override
    public void clear() {
        storage().clear();
    }

    @Override
    public Set<String> keySet() {
        return storage().keySet();
    }

    @Override
    public Collection<Object> values() {
        return storage().values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return storage().entrySet();
    }

    @Override
    public Context clone() {
        DefaultContext ctx = new DefaultContext();
        ctx.putAll(this);
        return ctx;
    }

    @Override
    public Context merge(Context target) {
        Context merged = this.clone();
        for (Map.Entry<String, Object> entry : target.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) continue;
            merged.put(entry.getKey(), entry.getValue());
        }
        return merged;
    }
}
