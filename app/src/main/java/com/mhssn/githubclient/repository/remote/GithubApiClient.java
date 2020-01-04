package com.mhssn.githubclient.repository.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class GithubApiClient {
    private static final String BASE_URL = "https://api.github.com/";

    private static Retrofit RETROFIT;

    public static synchronized GithubService getService() {
        if (RETROFIT == null) {
            RETROFIT = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return RETROFIT.create(GithubService.class);
    }

}
