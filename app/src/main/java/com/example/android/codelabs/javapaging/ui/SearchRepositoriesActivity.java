package com.example.android.codelabs.javapaging.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.codelabs.javapaging.Injection;
import com.example.android.codelabs.javapaging.model.Repo;
import com.example.android.codelabs.paging.R;

import java.util.List;

public class SearchRepositoriesActivity extends AppCompatActivity {

    private SearchRepositoriesViewModel viewModel;
    private ReposAdapter adapter = new ReposAdapter();
    private EditText search_repo;

    private static final String LAST_SEARCH_QUERY = "last_search_query";
    private static final String DEFAULT_QUERY = "Android";
    private RecyclerView list;
    private TextView emptyList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_repositories);

        // get the view model
        viewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(this))
                .get(SearchRepositoriesViewModel.class);

        list = findViewById(R.id.list);
        emptyList = findViewById(R.id.emptyList);
        search_repo = findViewById(R.id.search_repo);

        // add dividers between RecyclerView's row items
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        list.addItemDecoration(decoration);
        setupScrollListener();

        initAdapter();

        String query = savedInstanceState != null ? (savedInstanceState.getString(LAST_SEARCH_QUERY) != null
                ? savedInstanceState.getString(LAST_SEARCH_QUERY) : DEFAULT_QUERY) : DEFAULT_QUERY;
        viewModel.searchRepo(query);

        initSearch(query);
    }

    private void initSearch(String query) {
        search_repo.setText(query);

        search_repo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_GO) {
                    updateRepoListFromInput();
                    return true;
                } else {
                    return false;
                }
            }
        });

        search_repo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                    updateRepoListFromInput();
                    return true;
                } else {
                    return false;
                }
            }
        });

    }

    private void updateRepoListFromInput() {
        String text = search_repo.getText().toString().trim();

        if (!TextUtils.isEmpty(text)) {
            list.scrollToPosition(0);
            viewModel.searchRepo(text);
            adapter.submitList(null);
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(LAST_SEARCH_QUERY, viewModel.lastQueryValue());
    }

    private void initAdapter() {
        list.setAdapter(adapter);

        viewModel.repos.observe(this, new Observer<List<Repo>>() {
            @Override
            public void onChanged(@Nullable List<Repo> repos) {
                int size = repos.size();
                Log.d("Activity", "list: " + size);

                showEmptyList(size == 0);
                adapter.submitList(repos);
            }
        });

        viewModel.networkErrors.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Toast.makeText(SearchRepositoriesActivity.this, "\uD83D\uDE28 Wooops ${it}", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupScrollListener() {
        final LinearLayoutManager layoutManager = (LinearLayoutManager) list.getLayoutManager();

        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = layoutManager.getItemCount();
                int visibleItemCount = layoutManager.getChildCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount);
            }
        });

    }

    private void showEmptyList(boolean show) {
        if (show) {
            emptyList.setVisibility(View.VISIBLE);
            list.setVisibility(View.GONE);
        } else {
            emptyList.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
        }
    }
}
