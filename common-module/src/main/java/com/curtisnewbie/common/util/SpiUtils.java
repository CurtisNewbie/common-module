package com.curtisnewbie.common.util;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Utils for SPI
 *
 * @author yongjie.zhuang
 */
public final class SpiUtils {

    private SpiUtils() {
    }

    /**
     * Load first instance through SPI
     *
     * @return null if not found
     */
    public static <T> T loadFirstNullable(Class<T> tclz) {
        Iterator<T> iterator = getServiceIterator(tclz);
        if (iterator.hasNext())
            return iterator.next();
        else
            return null;
    }

    /**
     * Load first instance through SPI
     *
     * @throws if none instance is found
     * @see #loadFirstNullable(Class)
     */
    public static <T> T loadFirst(Class<T> tclz) {
        Iterator<T> iterator = getServiceIterator(tclz);
        if (iterator.hasNext())
            return iterator.next();
        else
            throw new IllegalStateException("Unable to load " + tclz.getName());
    }

    /**
     * Get iterator of ServiceLoader
     */
    public static <T> Iterator<T> getServiceIterator(Class<T> tclz) {
        ServiceLoader<T> serviceLoader = ServiceLoader.load(tclz);
        return serviceLoader.iterator();
    }

}
