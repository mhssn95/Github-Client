package com.mhssn.githubclient.repository.userrepository;

import com.mhssn.githubclient.model.Repository;
import com.mhssn.githubclient.model.User;
import com.mhssn.githubclient.model.resource.Response;

import java.util.List;

public interface UserRepository {
    List<User> getUsers();
    boolean addUser(String username);
    Response<List<Repository>> getRepositories(String username);
}
