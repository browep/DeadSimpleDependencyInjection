package com.github.browep.dsdi.sample;

import com.github.browep.dsdi.sample.model.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Example class for communicating with the network
 */

public interface NetworkAdapter {

    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);
}
