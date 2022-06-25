package com.idefav.theadpool.spi;

import com.idefav.utils.AssertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * the SpiHelper description.
 *
 * @param <T> the type parameter
 * @author wuzishu
 */
public class SpiHelper<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpiHelper.class);


    /**
     * Load first instance t.
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @return the t
     */
    public static <T> T LoadFirstInstance(Class<T> clazz) {
        AssertUtil.notNull(clazz, "SPI class cannot be null");
        try {
            ServiceLoader<T> serviceLoader = ServiceLoader.load(clazz);
            Iterator<T> iterator = serviceLoader.iterator();
            if (iterator.hasNext()) {
                return iterator.next();
            }
            else {
                return null;
            }
        }
        catch (Throwable t) {
            LOGGER.error(" [SpiLoader] ERROR: LoadFirstInstance failed", t);
            return null;
        }
    }

    /**
     * Load first instance or default t.
     *
     * @param <T>          the type parameter
     * @param clazz        the clazz
     * @param defaultClass the default class
     * @return the t
     */
    public static <T> T LoadFirstInstanceOrDefault(Class<T> clazz, Class<? extends T> defaultClass) {
        AssertUtil.notNull(clazz, "SPI class cannot be null");
        AssertUtil.notNull(defaultClass, "default SPI class cannot be null");
        try {
            ServiceLoader<T> serviceLoader = ServiceLoader.load(clazz);
            for (T instance : serviceLoader) {
                if (instance.getClass() != defaultClass) {
                    return instance;
                }
            }
            return defaultClass.newInstance();
        }
        catch (Throwable t) {
            LOGGER.error("[SpiLoader] ERROR: LoadFirstInstance0rDefault failed", t);
            return null;
        }
    }

    /**
     * Load instance list list.
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @return the list
     */
    public static <T> List<T> loadInstanceList(Class<T> clazz) {
        try {
            ServiceLoader<T> serviceLoader = ServiceLoader.load(clazz);
            List<T> list = new ArrayList<>();
            for (T spi : serviceLoader) {
                LOGGER.info("[SpiLoader] Found {} SPI: {}", clazz.getSimpleName(), spi.getClass().getCanonicalName());
                list.add(spi);
            }
            return list;
        }
        catch (Throwable t) {
            LOGGER.error("[SpiLoader] ERROR: loadInstanceList failed", t);
            return new ArrayList<>();
        }
    }
}
