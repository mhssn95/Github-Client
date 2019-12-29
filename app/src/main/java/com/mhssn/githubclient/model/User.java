package com.mhssn.githubclient.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Parcelable {
    public static final String TABLE_NAME = "users";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_BIO = "bio";
    public static final String COLUMN_COMPANY = "company";
    public static final String COLUMN_AVATAR_URL = "avatar_url";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_USERNAME + " TEXT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_BIO + " TEXT,"
                    + COLUMN_COMPANY + " TEXT,"
                    + COLUMN_AVATAR_URL + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    private long userId;
    private String name;
    private String username;
    private String bio;
    private String company;
    private String avatarUrl;

    public User(long userId, String username, String name, String bio, String company, String avatarUrl) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.bio = bio;
        this.company = company;
        this.avatarUrl = avatarUrl;
    }

    public long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public String getCompany() {
        return company;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public static User fromJson(String json) throws JSONException {
        JSONObject jsonUser = new JSONObject(json);
        int id = jsonUser.getInt("id");
        String username = jsonUser.getString("login");
        String name = jsonUser.getString("name");
        String bio = jsonUser.isNull("bio") ? null : jsonUser.getString("bio");
        String company = jsonUser.isNull("company") ? null : jsonUser.getString("company");
        String avatarUrl = jsonUser.getString("avatar_url");
        return new User(id, username, name, bio, company, avatarUrl);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.userId);
        dest.writeString(this.username);
        dest.writeString(this.name);
        dest.writeString(this.bio);
        dest.writeString(this.company);
        dest.writeString(this.avatarUrl);
    }

    protected User(Parcel in) {
        this.userId = in.readLong();
        this.username = in.readString();
        this.name = in.readString();
        this.bio = in.readString();
        this.company = in.readString();
        this.avatarUrl = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
