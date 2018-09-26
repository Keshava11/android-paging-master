package com.example.android.codelabs.javapaging.model;

import android.arch.lifecycle.LiveData;

import java.util.List;

public class RepoSearchResult {

    private LiveData<List<Repo>> data;
    private LiveData<String> networkErrors;

    public RepoSearchResult(LiveData<List<Repo>> data, LiveData<String> networkErrors) {
        this.data = data;
        this.networkErrors = networkErrors;
    }

    public LiveData<List<Repo>> getData() {
        return data;
    }

    public void setData(LiveData<List<Repo>> data) {
        this.data = data;
    }

    public LiveData<String> getNetworkErrors() {
        return networkErrors;
    }

    public void setNetworkErrors(LiveData<String> networkErrors) {
        this.networkErrors = networkErrors;
    }
}
