package com.example.android.codelabs.javapaging.api;

import com.example.android.codelabs.javapaging.model.Repo;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RepoSearchResponse {

    @SerializedName("total_count")
    private int total = 0;

    @SerializedName("items")
    private List<Repo> items = new ArrayList<>();

    @SerializedName("nextPage")
    private int nextPage = 0;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Repo> getItems() {
        return items;
    }

    public void setItems(List<Repo> items) {
        this.items = items;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }
}
