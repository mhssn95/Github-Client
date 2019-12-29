package com.mhssn.githubclient.repository.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mhssn.githubclient.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDataRepositoryImpl extends DatabaseHelper implements UserDataRepository {

    public UserDataRepositoryImpl(Context context) {
        super(context);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        String getAllUsersQuery = "SELECT * FROM " + User.TABLE_NAME + " ORDER BY " +
                User.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(getAllUsersQuery, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User(
                        cursor.getInt(cursor.getColumnIndex(User.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(User.COLUMN_USERNAME)),
                        cursor.getString(cursor.getColumnIndex(User.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(User.COLUMN_BIO)),
                        cursor.getString(cursor.getColumnIndex(User.COLUMN_COMPANY)),
                        cursor.getString(cursor.getColumnIndex(User.COLUMN_AVATAR_URL))
                );
                users.add(user);
            } while (cursor.moveToNext());
        }

        db.close();

        return users;
    }

    @Override
    public boolean insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(User.COLUMN_ID, user.getUserId());
        values.put(User.COLUMN_USERNAME, user.getUsername());
        values.put(User.COLUMN_NAME, user.getName());
        values.put(User.COLUMN_BIO, user.getBio());
        values.put(User.COLUMN_COMPANY, user.getCompany());
        values.put(User.COLUMN_AVATAR_URL, user.getAvatarUrl());

        boolean inserted = db.insert(User.TABLE_NAME, null, values) != -1;
        db.close();
        return inserted;
    }
}
