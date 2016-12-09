package com.github.browep.dsdi.sample;

import android.app.Application;
import android.os.Bundle;
import android.support.test.runner.AndroidJUnitRunner;

/**
 * Created by paulbrower on 12/9/16.
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
