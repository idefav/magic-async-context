package com.idefav.utils;

import java.util.Objects;

/**
 * the AssertUtil description.
 *
 * @author wuzishu
 */
public class AssertUtil {
    /**
     * Not null.
     *
     * @param value   the value
     * @param message the message
     */
    public static void notNull(Object value, String message) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException(message);
        }
    }
}
