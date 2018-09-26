package com.example.android.codelabs.javapaging.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.android.codelabs.javapaging.data.GithubRepository;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private GithubRepository repository;

    public ViewModelFactory(GithubRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(SearchRepositoriesViewModel.class)) {
            return (T) new SearchRepositoriesViewModel(repository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
