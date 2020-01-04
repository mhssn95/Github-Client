package com.mhssn.githubclient.repository.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mhssn.githubclient.model.GithubUser;

import java.util.List;

@Dao
public interface GithubUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUser(GithubUser user);

    @Query("SELECT * FROM users ORDER BY " +
            "CASE WHEN :sort = 'ASC' THEN username END ASC, " +
            "CASE WHEN :sort = 'DESC' THEN username END DESC")
    List<GithubUser> getAllUsers(String sort);

}
