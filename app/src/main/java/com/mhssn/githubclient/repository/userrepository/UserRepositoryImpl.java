package com.mhssn.githubclient.repository.userrepository;

import android.content.Context;

import com.mhssn.githubclient.model.Repository;
import com.mhssn.githubclient.model.User;
import com.mhssn.githubclient.model.response.Response;
import com.mhssn.githubclient.repository.database.UserDataRepositoryImpl;
import com.mhssn.githubclient.repository.service.UserRemoteRepository;
import com.mhssn.githubclient.repository.service.UserRemoteRepositoryImpl;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private static UserRepositoryImpl instance;
    private UserRemoteRepository remote;
    private UserDataRepositoryImpl data;

    private UserRepositoryImpl(Context context) {
        remote = UserRemoteRepositoryImpl.getInstance();
        data = new UserDataRepositoryImpl(context);
    }

    public synchronized static UserRepositoryImpl getInstance(Context context) {
        if (instance == null) {
            instance = new UserRepositoryImpl(context);
        }
        return instance;
    }

    @Override
    public List<User> getUsers() {
        return data.getAllUsers();
    }

    @Override
    public boolean addUser(String username) {
        Response<User> response = remote.getUser(username);
        switch (response.getState()) {
            case SUCCESS:
                data.insertUser(response.getData());
                return true;
            case FAILED:
                return false;
        }
        return false;
    }

    @Override
    public Response<List<Repository>> getRepositories(String username) {
        return remote.getRepositories(username);
    }
}
