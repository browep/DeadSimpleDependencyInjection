package com.github.browep.dsdi.sample;

import android.text.TextUtils;

import com.github.browep.dsdi.DependencySupplier;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * SampleApplication object, this injects the normal NetworkAdapter
 */

public class SampleApplication extends android.app.Application {

    public static final String DSDI_INJECTOR_CLASS = "dsdi.injector_class";
    private DependencySupplier dependencySupplier;

    @Override
    public void onCreate() {
        super.onCreate();

        // setup dependency injector
        dependencySupplier = setupDependencySupplier();
    }

    protected DependencySupplier setupDependencySupplier() {
        Boolean log = true;
        String className = System.getProperty(DSDI_INJECTOR_CLASS);
        if (TextUtils.isEmpty(className)) {
            return new ProductionDependencySupplier(log);
        } else {
            try {
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
    }

    public DependencySupplier getDependencySupplier() {
        return dependencySupplier;
    }
}
