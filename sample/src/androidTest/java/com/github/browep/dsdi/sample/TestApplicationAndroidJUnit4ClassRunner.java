package com.github.browep.dsdi.sample;

import android.app.Application;
import android.content.Context;
import android.support.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import android.support.test.internal.util.AndroidRunnerParams;
import android.support.test.runner.AndroidJUnitRunner;

import org.junit.runners.model.InitializationError;

/**
 * Created by paulbrower on 12/8/16.
 */

public class TestApplicationAndroidJUnit4ClassRunner extends AndroidJUnitRunner {
    @Override
    public Application newApplication(ClassLoader cl, String className, Context context) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        return super.newApplication(cl, TestApplication.class.getName(), context);
    }
}
