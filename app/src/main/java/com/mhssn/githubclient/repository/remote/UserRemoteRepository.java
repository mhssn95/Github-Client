package com.mhssn.githubclient.repository.remote;


import com.mhssn.githubclient.model.GithubRepository;
import com.mhssn.githubclient.model.GithubUser;

import java.util.List;

import retrofit2.Call;

public class UserRemoteRepository extends GithubApiClient {

    private static final String TAG = "UserRemoteRepository";

    private static UserRemoteRepository instance;

    private UserRemoteRepository() {
    }

    public static synchronized UserRemoteRepository getInstance() {
        if (instance == null) {
            instance = new UserRemoteRepository();
        }
        return instance;
    }

    public Call<GithubUser> getUser(String userName) {
        return GithubApiClient.getService().getUser(userName);
    }

    public Call<List<GithubRepository>> getRepositories(String username) {
        return GithubApiClient.getService().getRepositories(username);
    }
}
