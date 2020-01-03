package com.mhssn.githubclient.repository.remote;

import android.util.Log;

import com.mhssn.githubclient.model.GithubRepository;
import com.mhssn.githubclient.model.GithubUser;
import com.mhssn.githubclient.model.response.Response;
import com.mhssn.githubclient.model.response.ResponseState;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class UserRemoteRepository extends GithubHttpsClient {

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

    public Response<GithubUser> getUser(String userName) {
        try {
            String response = getResponse("users/" + userName);
            Log.d(TAG, "received user: " + response);
            return new Response<>(ResponseState.SUCCESS, GithubUser.fromJson(response), null);
        } catch (IOException | JSONException e) {
            return new Response<>(ResponseState.FAILED, null, e.getLocalizedMessage());
        }
    }

    public Response<List<GithubRepository>> getRepositories(String username) {
        try {
            String response = getResponse("users/" +username + "/repos?per_page=10&sort=star");
            Log.d(TAG, "received repositories: " + response);
            return new Response<>(ResponseState.SUCCESS, GithubRepository.getListFromJson(response), null);
        } catch (IOException | JSONException e) {
            return new Response<>(ResponseState.FAILED, null, e.getLocalizedMessage());
        }
    }
}
