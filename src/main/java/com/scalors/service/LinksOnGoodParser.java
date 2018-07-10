package com.scalors.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LinksOnGoodParser {

    private static final String BASE_URL = "https://www.aboutyou.de";

    private List<String> goodUrls = new ArrayList<String>();

    public List<String> parseLinks(List<String> urls){

        String link = "";
        for(String url : urls) {
            Document document = null;

            try {
                document = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Elements classesWithLinks = document.getElementsByClass("styles__tile--2s8XN col-sm-6 col-md-4 col-lg-4");

            for (Element links : classesWithLinks) {
                link = links.getElementsByTag("a").first().attr("href");
                goodUrls.add(BASE_URL + link);
            }
        }

        return goodUrls;
    }
}
