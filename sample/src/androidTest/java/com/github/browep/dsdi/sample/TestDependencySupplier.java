package com.github.browep.dsdi.sample;

import com.github.browep.dsdi.sample.model.Repo;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * create test dependencies for testing.  No calls should go out over the network, nothing persisted
 * to disk
 */

public class TestDependencySupplier extends ProductionDependencySupplier {

    private final Dao dao;

    public TestDependencySupplier() {
        super();

        dao = new TestDao();
    }

    @Override
    public Object supply(Object o, Class aClass) throws IllegalArgumentException {
        if (aClass.equals(NetworkAdapter.class)) {
            return new MockNetworkAdapter();
        } else if (aClass.equals(Dao.class)) {
            return dao;
        } else {
            return super.supply(o, aClass);
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
