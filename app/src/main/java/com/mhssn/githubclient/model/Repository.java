package com.mhssn.githubclient.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    private String name;
    private String description;
    private String link;

    public Repository(String name, String description, String link) {
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

    public static List<Repository> getListFromJson(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        List<Repository> repositories = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String name = jsonObject.getString("name");
            String description = jsonObject.isNull("description") ? null : jsonObject.getString("description");
            String url = jsonObject.getString("html_url");
            repositories.add(new Repository(name, description, url));
        }
        return repositories;
    }

}
