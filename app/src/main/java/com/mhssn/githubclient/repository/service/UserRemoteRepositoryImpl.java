package com.mhssn.githubclient.repository.service;

import android.util.Log;

import com.mhssn.githubclient.model.Repository;
import com.mhssn.githubclient.model.User;
import com.mhssn.githubclient.model.resource.Response;
import com.mhssn.githubclient.model.resource.ResponseState;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class UserRemoteRepositoryImpl extends GithubHttpsClient implements UserRemoteRepository {

    private static final String TAG = "UserRemoteRepository";

    private static UserRemoteRepositoryImpl instance;

    private UserRemoteRepositoryImpl() {
    }

    public static synchronized UserRemoteRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new UserRemoteRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Response<User> getUser(String userName) {
        try {
            String response = getResponse("users/" + userName);
            Log.d(TAG, "received user: " + response);
            return new Response<>(ResponseState.SUCCESS, User.fromJson(response), null);
        } catch (IOException | JSONException e) {
            return new Response<>(ResponseState.FAILED, null, e.getLocalizedMessage());
        }
    }

    @Override
    public Response<List<Repository>> getRepositories(String username) {
        try {
            String response = getResponse("users/" +username + "/repos?per_page=10&sort=star");
            Log.d(TAG, "received repositories: " + response);
            return new Response<>(ResponseState.SUCCESS, Repository.getListFromJson(response), null);
        } catch (IOException | JSONException e) {
            return new Response<>(ResponseState.FAILED, null, e.getLocalizedMessage());
        }
    }
}
