package com.example.android.codelabs.javapaging.ui;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.codelabs.javapaging.model.Repo;
import com.example.android.codelabs.paging.R;

import org.jetbrains.annotations.Nullable;

public class RepoViewHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView description;
    private TextView stars;
    private TextView language;
    private TextView forks;
    private Repo repo = null;

    public RepoViewHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.repo_name);
        description = itemView.findViewById(R.id.repo_description);
        stars = itemView.findViewById(R.id.repo_stars);
        language = itemView.findViewById(R.id.repo_language);
        forks = itemView.findViewById(R.id.repo_forks);
    }

    public static RepoViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_view_item, parent, false);
        return new RepoViewHolder(view);
    }

    void bind(@Nullable Repo repo) {
        if (repo == null) {
            Resources resources = itemView.getResources();

            name.setText(resources.getString(R.string.loading));
            description.setVisibility(View.GONE);
            language.setVisibility(View.GONE);
            stars.setText(resources.getString(R.string.unknown));
            forks.setText(resources.getString(R.string.unknown));
        } else {
            showRepoData(repo);
        }
    }

    private void showRepoData(Repo repo) {
        this.repo = repo;
        name.setText(repo.getFullName());

        // if the description is missing, hide the TextView
        int descriptionVisibility = View.GONE;
        if (repo.getDescription() != null) {
            description.setText(repo.getDescription());
            descriptionVisibility = View.VISIBLE;
        }
        description.setVisibility(descriptionVisibility);

        stars.setText(String.valueOf(repo.getStars()));
        forks.setText(String.valueOf(repo.getForks()));

        // if the language is missing, hide the label and the value
        int languageVisibility = View.GONE;
        if (!TextUtils.isEmpty(repo.getLanguage())) {
            Resources resources = this.itemView.getResources();
            language.setText(resources.getString(R.string.language, repo.getLanguage()));
            languageVisibility = View.VISIBLE;
        }
        language.setVisibility(languageVisibility);
    }

}
