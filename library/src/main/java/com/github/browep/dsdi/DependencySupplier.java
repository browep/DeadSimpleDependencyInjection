package com.github.browep.dsdi;

import android.util.Log;

import java.lang.reflect.Field;

import javax.inject.Inject;

/**
 *
 */

public abstract class DependencySupplier {

    private boolean log = false;

    public DependencySupplier() {

    }

    /**
     *
     * @param log whether the injector should log what its doing, useful for debugging
     */
    public DependencySupplier(boolean log) {
        this.log = log;
    }

    public abstract Object supply(Class aClass) throws IllegalArgumentException;

    /**
     * call this on all classes that have @Inject annotations
     *
     * @param obj
     */
    public void inject(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("obj cannot be null");
        } else {
            Field[] fields = obj.getClass().getFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Inject.class)) {
                    Class fieldClass = field.getType();
                    Object supply = supply(fieldClass);
                    log("injection: (" + obj +") " + field.getName() +" -> " + supply);
                    try {
                        field.set(obj, supply);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private void log(String logMsg) {
        if(log) {
            Log.d(getClass().getCanonicalName(), logMsg);
        }
    }
}
