package com.github.browep.dsdi.sample;

import android.app.Application;

import com.github.browep.dsdi.DependencySupplier;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * supply production or development ready dependencies
 */

public class ProductionDependencySupplier extends DependencySupplier {

    private NetworkAdapter networkAdapter;
    private Dao dao;

    public ProductionDependencySupplier() {
        this(false);
    }

    public ProductionDependencySupplier(Boolean log) {
        super(log);

        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        networkAdapter = new NetworkAdapter(retrofit.create(Server.class));

        dao = new SharedPrefsDao(SampleApplication.getInstance());
    }

    @Override
    public Object supply(Object o, Class aClass) {
        if (aClass.equals(NetworkAdapter.class)) {
           return networkAdapter;
        } else if (aClass.equals(Dao.class)) {
            return dao;
        } else {
            throw new IllegalArgumentException("could not supply: " + aClass);
        }
    }
}
