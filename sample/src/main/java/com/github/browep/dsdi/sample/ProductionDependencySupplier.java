package com.github.browep.dsdi.sample;

import com.github.browep.dsdi.DependencySupplier;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * supply production or development ready dependencies
 */

public class ProductionDependencySupplier extends DependencySupplier {

    public ProductionDependencySupplier() {
    }

    public ProductionDependencySupplier(boolean log) {
        super(log);
    }

    @Override
    public Object supply(Class aClass) {
        if (aClass.equals(NetworkAdapter.class)) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            return retrofit.create(NetworkAdapter.class);
        } else {
            throw new IllegalArgumentException("could not supply: " + aClass);
        }
    }
}
