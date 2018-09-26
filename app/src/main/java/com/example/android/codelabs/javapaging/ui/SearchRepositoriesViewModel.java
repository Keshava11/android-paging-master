package com.example.android.codelabs.javapaging.ui;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.android.codelabs.javapaging.data.GithubRepository;
import com.example.android.codelabs.javapaging.model.Repo;
import com.example.android.codelabs.javapaging.model.RepoSearchResult;

import java.util.List;

public class SearchRepositoriesViewModel extends ViewModel {

    private static final int VISIBLE_THRESHOLD = 5;
    private GithubRepository repository;
    private MutableLiveData<String> queryLiveData = new MutableLiveData<String>();

    public SearchRepositoriesViewModel(GithubRepository repository) {
        this.repository = repository;
    }

    public LiveData<RepoSearchResult> repoResult = Transformations.map(queryLiveData, new Function<String, RepoSearchResult>() {
        @Override
        public RepoSearchResult apply(String input) {
            return repository.search(input);
        }
    });

    public LiveData<List<Repo>> repos = Transformations.switchMap(repoResult, new Function<RepoSearchResult, LiveData<List<Repo>>>() {
        @Override
        public LiveData<List<Repo>> apply(RepoSearchResult input) {
            return input.getData();
        }
    });

    public LiveData<String> networkErrors = Transformations.switchMap(repoResult, new Function<RepoSearchResult, LiveData<String>>() {
        @Override
        public LiveData<String> apply(RepoSearchResult input) {
            return input.getNetworkErrors();
        }
    });

    void searchRepo(String queryString) {
        queryLiveData.postValue(queryString);
    }

    void listScrolled(int visibleItemCount, int lastVisibleItemPosition, int totalItemCount) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            String immutableQuery = lastQueryValue();
            if (immutableQuery != null) {
                repository.requestMore(immutableQuery);
            }
        }
    }

    /**
     * Get the last query value.
     */
    String lastQueryValue() {
        return queryLiveData.getValue();
    }

}
