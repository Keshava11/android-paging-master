package com.example.android.codelabs.javapaging.api;

import android.util.Log;

import com.example.android.codelabs.javapaging.BlockExecutor;
import com.example.android.codelabs.javapaging.model.Repo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class GithubService {

    private static final String TAG = "GithubService";
    private static final String IN_QUALIFIER = "in:name,description";
    private static final String BASE_URL = "https://api.github.com/";

    public interface GithubServiceEp {
        /**
         * Get repos ordered by stars.
         */
        @GET("search/repositories?sort=stars")
        Call<RepoSearchResponse> searchRepos(@Query("q") String query,
                                             @Query("page") int page,
                                             @Query("per_page") int itemsPerPage);
    }

    public static GithubServiceEp create() {
        final HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logger)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubServiceEp.class);
    }


    public static void searchRepos(GithubServiceEp service, String query, int page, int itemsPerPage, final SearchListener iSearchListener) {
        Log.d(TAG, "query: " + query + ", page: " + page + ", itemsPerPage: " + itemsPerPage);

        String apiQuery = query + IN_QUALIFIER;

        service.searchRepos(apiQuery, page, itemsPerPage).enqueue(new Callback<RepoSearchResponse>() {
            @Override
            public void onResponse(Call<RepoSearchResponse> call, Response<RepoSearchResponse> response) {
                Log.d(TAG, "got a response " + response);

                if (response.isSuccessful()) {
                    List<Repo> repos = new ArrayList<>();
                    RepoSearchResponse repoSearchResponse = response.body();

                    if (repoSearchResponse != null)
                        repos = repoSearchResponse.getItems();

                    iSearchListener.onSuccess(repos);
                } else {
                    ResponseBody errorBody = response.errorBody();

                    try {
                        iSearchListener.onError(errorBody != null ? errorBody.string() : "Unknown error");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RepoSearchResponse> call, Throwable t) {
                Log.d(TAG, "fail to get data");
                iSearchListener.onError(t.getMessage() != null ? t.getMessage() : "unknown error");
            }
        });

    }

    /**
     * Api Success listener
     */
    public interface SearchListener {
        void onSuccess(List<Repo> repos);

        void onError(String error);
    }

}
