package com.mhssn.githubclient.repository;

import android.content.Context;

import com.mhssn.githubclient.model.GithubRepository;
import com.mhssn.githubclient.model.GithubUser;
import com.mhssn.githubclient.model.response.Response;
import com.mhssn.githubclient.repository.database.UserDataRepository;
import com.mhssn.githubclient.repository.remote.UserRemoteRepository;

import java.util.List;

public class UserRepository {

    private static UserRepository instance;
    private UserRemoteRepository remote;
    private UserDataRepository data;

    private UserRepository(Context context) {
        remote = UserRemoteRepository.getInstance();
        data = new UserDataRepository(context);
    }

    public synchronized static UserRepository getInstance(Context context) {
        if (instance == null) {
            instance = new UserRepository(context);
        }
        return instance;
    }

    public List<GithubUser> getUsers() {
        return data.getAllUsers();
    }

    public boolean addUser(String username) {
        Response<GithubUser> response = remote.getUser(username);
        switch (response.getState()) {
            case SUCCESS:
                data.insertUser(response.getData());
                return true;
            case FAILED:
                return false;
        }
        return false;
    }

    public Response<List<GithubRepository>> getRepositories(String username) {
        return remote.getRepositories(username);
    }
}
