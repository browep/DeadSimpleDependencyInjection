package com.github.browep.dsdi.sample;

import android.app.Application;
import android.os.Bundle;
import android.support.test.runner.AndroidJUnitRunner;

/**
 * overriden instrumentation runner.  This will allow us to set the {@link TestDependencySupplier}
 * as the Injector class and will be picked up in the SampleApplication object
 */

public class CustomInstrumentationRunner extends AndroidJUnitRunner {
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void callApplicationOnCreate(Application app) {
        System.setProperty(SampleApplication.DSDI_INJECTOR_CLASS, TestDependencySupplier.class.getCanonicalName());
        super.callApplicationOnCreate(app);
    }
}
