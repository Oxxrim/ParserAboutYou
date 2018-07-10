package com.scalors.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckForRedirect {

    private static final String BASE_URL = "https://www.aboutyou.de";
    private static final String SEARCH_URL = "https://www.aboutyou.de/suche?term=";

    private String url = "";

    public String checker(String keyword){

        url = SEARCH_URL + keyword;
        HttpURLConnection connection = null;
        int statusCode = 0;

        try {

            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setInstanceFollowRedirects(false);
            connection.connect();

            statusCode = connection.getResponseCode();

            if (statusCode == 302){
                url = BASE_URL + connection.getHeaderField("location");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return url;
    }
}
