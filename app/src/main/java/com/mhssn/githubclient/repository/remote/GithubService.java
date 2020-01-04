package com.mhssn.githubclient.repository.remote;

import com.mhssn.githubclient.model.GithubRepository;
import com.mhssn.githubclient.model.GithubUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {
    @GET("users/{username}")
    Call<GithubUser> getUser(@Path("username") String username);

    @GET("users/{username}/repos?per_page=10&sort=star")
    Call<List<GithubRepository>> getRepositories(@Path("username") String username);
}
