package com.mhssn.githubclient.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "users")
public class GithubUser implements Parcelable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private long userId;
    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String name;
    @ColumnInfo(name = "username")
    @SerializedName("login")
    private String username;
    @ColumnInfo(name = "bio")
    @SerializedName("bio")
    private String bio;
    @ColumnInfo(name = "Company")
    @SerializedName("company")
    private String company;
    @ColumnInfo(name = "avatar_url")
    @SerializedName("avatar_url")
    private String avatarUrl;

    public GithubUser(long userId, String username, String name, String bio, String company, String avatarUrl) {
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

    private GithubUser(Parcel in) {
        this.userId = in.readLong();
        this.username = in.readString();
        this.name = in.readString();
        this.bio = in.readString();
        this.company = in.readString();
        this.avatarUrl = in.readString();
    }

    public static final Parcelable.Creator<GithubUser> CREATOR = new Parcelable.Creator<GithubUser>() {
        @Override
        public GithubUser createFromParcel(Parcel source) {
            return new GithubUser(source);
        }

        @Override
        public GithubUser[] newArray(int size) {
            return new GithubUser[size];
        }
    };
}
