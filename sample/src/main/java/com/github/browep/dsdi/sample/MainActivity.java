package com.github.browep.dsdi.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.browep.dsdi.sample.model.Repo;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Inject
    NetworkAdapter networkAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((SampleApplication) getApplicationContext()).getDependencySupplier().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        networkAdapter.listRepos("browep", new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                StringBuilder repoNames = new StringBuilder();
                for(Repo repo : response.body()) {
                    repoNames.append(repo.name).append("\n");
                }

                ((TextView) findViewById(R.id.text)).setText(repoNames);
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                ((TextView) findViewById(R.id.text)).setText("Call failed.");
            }
        });
    }
}
