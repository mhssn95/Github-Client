package com.mhssn.githubclient.repository.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

class GithubHttpsClient {
    private static final String BASE_URL = "https://api.github.com/";

    String getResponse(String segments) throws IOException {
        BufferedReader br = null;
        StringBuilder response = new StringBuilder();

        HttpsURLConnection connection = (HttpsURLConnection) new URL(BASE_URL + segments).openConnection();
        connection.setRequestMethod("GET");
        try {
            InputStream is = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = br.readLine()) != null) {
                response.append(line).append('\n');
            }

        } catch (IOException e) {
            if (br != null)
                br.close();
            throw e;
        }

        return response.toString();
    }

}
