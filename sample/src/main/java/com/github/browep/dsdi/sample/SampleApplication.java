package com.github.browep.dsdi.sample;

import android.text.TextUtils;

import com.github.browep.dsdi.DependencySupplier;

/**
 * SampleApplication object, this injects the normal NetworkAdapter
 */

public class SampleApplication extends android.app.Application {

    public static final String DSDI_INJECTOR_CLASS = "dsdi.injector_class";
    private DependencySupplier dependencySupplier;

    private static SampleApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;

        // setup dependency injector
        dependencySupplier = setupDependencySupplier();
    }

    public static SampleApplication getInstance() {
        return INSTANCE;
    }

    protected DependencySupplier setupDependencySupplier() {

        // get the name from the system props
        String className = System.getProperty(DSDI_INJECTOR_CLASS);

        // default to production supplier if none other specified
        if (TextUtils.isEmpty(className)) {
            className = ProductionDependencySupplier.class.getCanonicalName();
        }

        return DependencySupplier.initializeSupplier(className);
    }

    public DependencySupplier getDependencySupplier() {
        return dependencySupplier;
    }
}
