package com.example.android.codelabs.javapaging.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.codelabs.javapaging.model.Repo;

@Database(entities = {Repo.class}, version = 1, exportSchema = false)
public abstract class RepoDatabase extends RoomDatabase {

    abstract public RepoDao reposDao();

    /**
     * Get {@link RepoDatabase} instance
     *
     * @param iContext Application context
     * @return RepoDatabase instance
     */
    public static RepoDatabase getInstance(Context iContext) {
        return Room.databaseBuilder(iContext,
                RepoDatabase.class, "Github.db")
                .allowMainThreadQueries()
                .build();
    }

}
