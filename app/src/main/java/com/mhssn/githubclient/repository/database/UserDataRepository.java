package com.mhssn.githubclient.repository.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mhssn.githubclient.model.GithubUser;

import java.util.ArrayList;
import java.util.List;

public class UserDataRepository extends DatabaseHelper {

    public UserDataRepository(Context context) {
        super(context);
    }

    public List<GithubUser> getAllUsers() {
        List<GithubUser> githubUsers = new ArrayList<>();

        String getAllUsersQuery = "SELECT * FROM " + GithubUser.TABLE_NAME + " ORDER BY " +
                GithubUser.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(getAllUsersQuery, null);
        if (cursor.moveToFirst()) {
            do {
                GithubUser githubUser = new GithubUser(
                        cursor.getInt(cursor.getColumnIndex(GithubUser.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(GithubUser.COLUMN_USERNAME)),
                        cursor.getString(cursor.getColumnIndex(GithubUser.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(GithubUser.COLUMN_BIO)),
                        cursor.getString(cursor.getColumnIndex(GithubUser.COLUMN_COMPANY)),
                        cursor.getString(cursor.getColumnIndex(GithubUser.COLUMN_AVATAR_URL))
                );
                githubUsers.add(githubUser);
            } while (cursor.moveToNext());
        }

        db.close();

        return githubUsers;
    }

    public boolean insertUser(GithubUser githubUser) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GithubUser.COLUMN_ID, githubUser.getUserId());
        values.put(GithubUser.COLUMN_USERNAME, githubUser.getUsername());
        values.put(GithubUser.COLUMN_NAME, githubUser.getName());
        values.put(GithubUser.COLUMN_BIO, githubUser.getBio());
        values.put(GithubUser.COLUMN_COMPANY, githubUser.getCompany());
        values.put(GithubUser.COLUMN_AVATAR_URL, githubUser.getAvatarUrl());

        boolean inserted = db.insert(GithubUser.TABLE_NAME, null, values) != -1;
        db.close();
        return inserted;
    }
}
