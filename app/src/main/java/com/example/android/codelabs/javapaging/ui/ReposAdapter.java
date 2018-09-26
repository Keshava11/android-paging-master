package com.example.android.codelabs.javapaging.ui;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.android.codelabs.javapaging.model.Repo;

public class ReposAdapter extends ListAdapter<Repo, RecyclerView.ViewHolder> {

    /**
     * This can be changed as per requirement
     */
    private static final DiffUtil.ItemCallback<Repo> REPO_COMPARATOR = new DiffUtil.ItemCallback<Repo>() {
        @Override
        public boolean areItemsTheSame(Repo oldItem, Repo newItem) {
            String oldFullName = oldItem.getFullName() != null ? oldItem.getFullName() : "";
            String newFullName = newItem.getFullName() != null ? newItem.getFullName() : "";

            return oldFullName.equals(newFullName);
        }

        @Override
        public boolean areContentsTheSame(Repo oldItem, Repo newItem) {
            return oldItem.equals(newItem);
        }
    };

    public ReposAdapter() {
        super(REPO_COMPARATOR);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return RepoViewHolder.create(parent) ;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Repo repoItem = getItem(position);
        if (repoItem != null) {
            ((RepoViewHolder)holder).bind(repoItem);
        }
    }
}
