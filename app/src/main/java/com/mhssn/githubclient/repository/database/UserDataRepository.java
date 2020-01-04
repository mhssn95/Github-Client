package com.mhssn.githubclient.repository.database;

import android.content.Context;

import com.mhssn.githubclient.model.GithubUser;

import java.util.List;

public class UserDataRepository {

    private GithubUserDao githubUserDao;

    public UserDataRepository(Context context) {
        GithubClientDatabase db = GithubClientDatabase.getDatabase(context);
        githubUserDao = db.githubUserDao();
    }

    public List<GithubUser> getAllUsers(String sort) {
        return githubUserDao.getAllUsers(sort);
    }

    public void insertUser(GithubUser githubUser) {
        githubUserDao.insertUser(githubUser);
    }
}
