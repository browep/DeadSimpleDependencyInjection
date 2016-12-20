package com.github.browep.dsdi;

import android.util.Log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

/**
 *  Abstract class that should be overridden for each type of supplier you want ( Prod, Test, Dev
 *  etc. )
 */

public abstract class DependencySupplier {

    public static final String TAG = DependencySupplier.class.getCanonicalName();
    private boolean log = false;

    public DependencySupplier() {

    }

    /**
     *
     * @param log whether the injector should log what its doing, useful for debugging
     */
    public DependencySupplier(Boolean log) {
        this.log = log;
    }

    /**
     * Helper/Util method.
     * dyanmically load the {@link DependencySupplier} class and initialize it
     * @param log
     * @param className
     * @return
     */
    public static DependencySupplier initializeSupplier(Boolean log, String className) {
        try {
            if(log) {
                Log.d(TAG, "initializing: " + className + " as dependency supplier");
            }
            Class<DependencySupplier> dependencySupplierClass = (Class<DependencySupplier>) Class.forName(className);
            Constructor<DependencySupplier> supplierConstructor = dependencySupplierClass.getConstructor(Boolean.class);
            return supplierConstructor.newInstance(log);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * override this method to return an instance of the injectionClass
     * @param injectee this is the object that contains the fields to be injected, should not be
     *                 modified
     * @param injectionClass the is the Class of the object that needs to be supplied
     * @return
     * @throws IllegalArgumentException
     */
    public abstract Object supply(Object injectee, Class injectionClass) throws IllegalArgumentException;

    /**
     * call this on all classes that have @Inject annotations
     *
     * @param obj Object of a Class that contains fields with @Inject annotations.
     */
    public void inject(Object obj) {

        if (obj == null) {
            throw new IllegalArgumentException("obj cannot be null");
        } else {
            Class<?> objClass = obj.getClass();
            innerInject(obj, objClass);
        }
    }

    /**
     * do the actual injection.  If no injection candidates are found, look in the super class
     * @param obj
     * @param objClass
     * @return
     */
    private void innerInject(Object obj, Class<?> objClass) {

        // get all the fields that this class ( but not it's super class ) explicitly declares
        Field[] fields = objClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                field.setAccessible(true);
                Class fieldClass = field.getType();
                Object supply = supply(obj, fieldClass);
                log("injection: (" + obj +") " + field.getName() +" -> " + supply);
                try {
                    field.set(obj, supply);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        // if the superclass isnt Object and it has the @HasInjectees annotation, inject it as well
        Class<?> superclass = objClass.getSuperclass();
        if (superclass != Object.class && superclass.isAnnotationPresent(HasInjectees.class)) {
            innerInject(obj, obj.getClass().getSuperclass());
        }
    }

    private void log(String logMsg) {
        if(log) {
            Log.d(getClass().getCanonicalName(), logMsg);
        }
    }

    /**
     *
     * Explicit declaration that a class has @Inject fields.  Use this for a superclass that wants
     * to be injected.  Not needed if the class will not be extended
     *
     * Created by paulbrower on 12/20/16.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public static @interface HasInjectees {
    }
}
