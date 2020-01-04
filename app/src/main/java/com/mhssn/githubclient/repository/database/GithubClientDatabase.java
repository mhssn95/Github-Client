package com.mhssn.githubclient.repository.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mhssn.githubclient.model.GithubUser;

@Database(entities = {GithubUser.class}, version = 1, exportSchema = false)
abstract public class GithubClientDatabase extends RoomDatabase {

    public abstract GithubUserDao githubUserDao();

    private static GithubClientDatabase INSTANCE;

    public static synchronized GithubClientDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    GithubClientDatabase.class, "github_client_database")
                    .build();
        }
        return INSTANCE;
    }

}
