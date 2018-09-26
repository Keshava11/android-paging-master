package com.example.android.codelabs.javapaging.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Immutable model class for a Github repo that holds all the information about a repository.
 * Objects of this type are received from the Github API, therefore all the fields are annotated
 * with the serialized name.
 * This class also defines the Room repos table, where the repo [id] is the primary key.
 */
@Entity(tableName = "repos")
public class Repo {

    @PrimaryKey
    @ColumnInfo
    int id;

    @NonNull
    @ColumnInfo
    String name;

    @ColumnInfo
    String fullName;

    @ColumnInfo
    String description;

    @NonNull
    @ColumnInfo
    String html_url;

    @SerializedName("stargazers_count")
    @ColumnInfo(name = "stargazers_count")
    int stars;

    @SerializedName("forks_count")
    @ColumnInfo(name = "forks_count")
    int forks;

    @ColumnInfo
    String language;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getFullName() {
        return fullName;
    }

    public void setFullName(@NonNull String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(@NonNull String html_url) {
        this.html_url = html_url;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getForks() {
        return forks;
    }

    public void setForks(int forks) {
        this.forks = forks;
    }


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

