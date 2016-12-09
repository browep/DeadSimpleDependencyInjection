package com.github.browep.dsdi.sample;

import com.github.browep.dsdi.DependencySupplier;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * supply production or development ready dependencies
 */

public class ProductionDependencySupplier extends DependencySupplier {

    private NetworkAdapter networkAdapter;

    public ProductionDependencySupplier() {
        this(false);
    }

    public ProductionDependencySupplier(boolean log) {
        super(log);

        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        networkAdapter = retrofit.create(NetworkAdapter.class);
    }

    @Override
    public Object supply(Class aClass) {
        if (aClass.equals(NetworkAdapter.class)) {
           return networkAdapter;
        } else {
            throw new IllegalArgumentException("could not supply: " + aClass);
        }
    }
}
