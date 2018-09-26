package com.example.android.codelabs.javapaging;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import com.example.android.codelabs.javapaging.api.GithubService;
import com.example.android.codelabs.javapaging.data.GithubRepository;
import com.example.android.codelabs.javapaging.db.GithubLocalCache;
import com.example.android.codelabs.javapaging.db.RepoDatabase;
import com.example.android.codelabs.javapaging.ui.ViewModelFactory;

import java.util.concurrent.Executors;

public class Injection {

    /**
     * Creates an instance of [GithubLocalCache] based on the database DAO.
     */
    private static GithubLocalCache provideCache( Context context) {
        RepoDatabase database = RepoDatabase.getInstance(context);
        return new GithubLocalCache(database.reposDao(), Executors.newSingleThreadExecutor());
    }

    /**
     * Creates an instance of [GithubRepository] based on the [GithubService] and a
     * [GithubLocalCache]
     */
    private static GithubRepository provideGithubRepository(Context context) {
        return new GithubRepository(GithubService.create(), provideCache(context));
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    public static ViewModelProvider.Factory provideViewModelFactory(Context context) {
        return new ViewModelFactory(provideGithubRepository(context));
    }
}
