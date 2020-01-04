package com.mhssn.githubclient.model;

import com.google.gson.annotations.SerializedName;

public class GithubRepository {

    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("html_url")
    private String link;

    public GithubRepository(String name, String description, String link) {
        this.name = name;
        this.description = description;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

}
