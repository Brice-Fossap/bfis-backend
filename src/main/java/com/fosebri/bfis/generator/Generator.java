package com.fosebri.bfis.generator;

public interface Generator<T> {
    /**
     * Generates a new value of type T.
     *
     * @return a new instance of T
     */
    T next();
}
