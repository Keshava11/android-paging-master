package com.example.android.codelabs.javapaging.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.android.codelabs.javapaging.BlockExecutor;
import com.example.android.codelabs.javapaging.api.GithubService;
import com.example.android.codelabs.javapaging.db.GithubLocalCache;
import com.example.android.codelabs.javapaging.model.Repo;
import com.example.android.codelabs.javapaging.model.RepoSearchResult;

import java.util.List;

public class GithubRepository {

    private GithubService.GithubServiceEp service;
    private GithubLocalCache cache;

    // keep the last requested page. When the request is successful, increment the page number.
    private int lastRequestedPage = 1;

    // LiveData of network errors.
    private MutableLiveData<String> networkErrors = new MutableLiveData<>();

    // avoid triggering multiple requests in the same time
    private boolean isRequestInProgress = false;

    private static final int NETWORK_PAGE_SIZE = 50;

    public GithubRepository(GithubService.GithubServiceEp service, GithubLocalCache cache) {
        this.service = service;
        this.cache = cache;
    }

    private void requestAndSaveData(String query){
        if(isRequestInProgress)
            return;

        isRequestInProgress = true;

        GithubService.searchRepos(service, query, lastRequestedPage, NETWORK_PAGE_SIZE, new GithubService.SearchListener() {
            @Override
            public void onSuccess(List<Repo> repos) {
                cache.insert(repos, new BlockExecutor() {
                    @Override
                    public void executeThis() {
                        lastRequestedPage++;
                        isRequestInProgress = false;
                    }
                });
            }

            @Override
            public void onError(String error) {
                networkErrors.postValue(error);
                isRequestInProgress = false;
            }
        });
    }


    public void requestMore(String query) {
        requestAndSaveData(query);
    }


    /**
     * Search repositories whose names match the query.
     */
    public RepoSearchResult search(String query) {
        Log.d("GithubRepository", "New query: "+query);
        lastRequestedPage = 1;
        requestAndSaveData(query);

        // Get data from the local cache
        LiveData<List<Repo>> data = cache.reposByName(query);

        return new RepoSearchResult(data, networkErrors);
    }


}
