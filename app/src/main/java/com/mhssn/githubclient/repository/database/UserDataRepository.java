package com.mhssn.githubclient.repository.database;


import com.mhssn.githubclient.model.User;

import java.util.List;

public interface UserDataRepository {

    List<User> getAllUsers();
    boolean insertUser(User user);

}
