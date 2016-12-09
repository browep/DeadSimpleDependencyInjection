package com.github.browep.dsdi.sample;

import com.github.browep.dsdi.DependencySupplier;
import com.github.browep.dsdi.sample.model.Repo;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;

/**
 * create test dependencies for testing.  No calls should go out over the network
 */

public class TestDependencySupplier extends ProductionDependencySupplier {

    public TestDependencySupplier(Boolean log) {
        super(log);
    }

    @Override
    public Object supply(Class aClass) throws IllegalArgumentException {
        if (aClass.equals(NetworkAdapter.class)) {
            return new MockNetworkAdapter();
        } else {
            return super.supply(aClass);
        }
    }

    public static class MockNetworkAdapter extends NetworkAdapter {


        public MockNetworkAdapter() {
            super(null);
        }

        @Override
        public void listRepos(String user, Callback<List<Repo>> callback) {
            List<Repo> repos = new LinkedList<>();
            Repo repo = new Repo();
            repo.name = "Test Repo1";
            repos.add(repo);
            repo = new Repo();
            repo.name = "Test Repo2";
            repos.add(repo);
            repo = new Repo();
            repo.name = "Test Repo3";
            repos.add(repo);
            callback.onResponse(null, Response.success(repos));
        }
    }

}
