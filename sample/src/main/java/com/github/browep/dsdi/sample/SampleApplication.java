package com.github.browep.dsdi.sample;

import com.github.browep.dsdi.DependencySupplier;

/**
 * SampleApplication object, this injects the normal NetworkAdapter
 */

public class SampleApplication extends android.app.Application {

    private DependencySupplier dependencySupplier;

    @Override
    public void onCreate() {
        super.onCreate();

        // setup dependency injector
        dependencySupplier = setupDependencySupplier();
    }

    protected DependencySupplier setupDependencySupplier() {
        return new ProductionDependencySupplier(true);
    }

    public DependencySupplier getDependencySupplier() {
        return dependencySupplier;
    }
}
