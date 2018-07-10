package com.scalors.service;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.scalors.model.Offer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class GoodParser {

    private final JsonParser jsonParser = new JsonParser();

    private List<Offer> offers = new ArrayList<Offer>();

    public List<Offer> goodParsing(List<String> links){

        Offer offer = new Offer();

        for(String offerPage : links){
            Document document = null;

            try {
                document = Jsoup.connect(offerPage).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String name = document.getElementsByClass("styles__title--3Jos_").first().text();
            offer.setName(name);

            Elements colors = document.getElementsByAttributeValueMatching("class", "styles__title--UFKYd");
            String clr = "";
            for (Element color : colors) {
                clr += color.text() + ",";
            }

            offer.setColor(clr);

            String article = document.getElementsByClass("styles__articleNumber--1UszN").text().substring(12);
            offer.setArticle(article);



            String json = document.html();
            json = json.split("\"window.__INITIAL_STATE__=\"")[1].substring(0,json.indexOf(";</script>"));

            JsonObject jsonObject = (JsonObject) jsonParser.parse(json);
            JsonObject offerJson = jsonObject.get("products").getAsJsonObject();

        }



        return offers;
    }
}
