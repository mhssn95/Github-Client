package com.mhssn.githubclient.repository.service;

import com.mhssn.githubclient.model.Repository;
import com.mhssn.githubclient.model.response.Response;
import com.mhssn.githubclient.model.User;

import java.util.List;

public interface UserRemoteRepository {

    Response<User> getUser(String userName);
    Response<List<Repository>> getRepositories(String userName);
}
