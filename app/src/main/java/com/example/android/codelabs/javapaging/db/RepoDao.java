package com.example.android.codelabs.javapaging.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.codelabs.javapaging.model.Repo;

import java.util.List;

@Dao
public interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Repo> posts);

    @Query("SELECT * FROM repos WHERE (name LIKE :query) OR (description LIKE :query) ORDER BY stargazers_count DESC, name ASC")
    LiveData<List<Repo>> reposByName(String query);
}
