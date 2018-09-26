package com.example.android.codelabs.javapaging.db;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.android.codelabs.javapaging.BlockExecutor;
import com.example.android.codelabs.javapaging.model.Repo;

import java.util.List;
import java.util.concurrent.Executor;

public class GithubLocalCache {
    private RepoDao repoDao;
    private Executor ioExecutor;

    public GithubLocalCache(RepoDao repoDao, Executor executor) {
        this.repoDao = repoDao;
        this.ioExecutor = executor;
    }

    /**
     * Insert a list of repos in the database, on a background thread.
     */
    public void insert(final List<Repo> repos, final BlockExecutor iBlockExecutor) {
        ioExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("GithubLocalCache", "inserting " + repos.size() + " repos");
                repoDao.insert(repos);
                iBlockExecutor.executeThis();
            }
        });
    }

    /**
     * Request a LiveData<List<Repo>> from the Dao, based on a repo name. If the name contains
     * multiple words separated by spaces, then we're emulating the GitHub API behavior and allow
     * any characters between the words.
     *
     * @param name repository name
     */
    public LiveData<List<Repo>> reposByName(String name) {
        name = name.replaceAll(" ", "%");
        String query = "%" + name + "%"; // "%${name.replace(' ', '%')}%";
        return repoDao.reposByName(query);
    }
}
