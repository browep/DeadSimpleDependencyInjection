package com.github.browep.dsdi.sample;

import com.github.browep.dsdi.sample.model.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Example class for communicating with the network
 */

public class NetworkAdapter {

    Server server;

    public NetworkAdapter(Server server) {
        this.server = server;
    }

    public void listRepos(String user, Callback<List<Repo>> callback) {
        server.listRepos(user).enqueue(callback);
    }
}
