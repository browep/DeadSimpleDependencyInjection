package com.github.browep.dsdi.sample;

import android.app.Application;

import com.github.browep.dsdi.DependencySupplier;

/**
 *  Application object for test cases, overrides the injector provider and supplies test
 *  dependencies
 */

public class TestApplication extends SampleApplication {

    @Override
    protected DependencySupplier setupDependencySupplier() {
        return new TestDependencySupplier();
    }
}
