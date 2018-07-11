package com.scalors.service;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.scalors.model.Offer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class GoodParser extends Thread{

    private final JsonParser jsonParser = new JsonParser();
    private List<Offer> offers = new ArrayList<Offer>();
    private String offerPage;
    private final Semaphore semaphore = new Semaphore(20);




    public GoodParser(List<Offer> offers, String offerPage) {

        this.offers = offers;
        this.offerPage = offerPage;
    }

    /*public List<Offer> goodParsing(List<String> links) {



        for (String offerPage : links) {

            String[] jOfferId = offerPage.split("-");
            String jId = jOfferId[jOfferId.length - 1];
            Document document = null;


            int available = 0;

            try {
                document = Jsoup.connect(offerPage).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (document.html().contains("styles__title--3Jos_")) {

                String name = "";
                try {
                    name = document.getElementsByClass("styles__title--3Jos_").first().text();
                }catch (Exception e){
                    System.out.println("HERE");
                }



                Elements colors = document.getElementsByAttributeValueMatching("class", "styles__title--UFKYd");
                String clr = "";
                for (Element color : colors) {
                    clr += color.text() + ",";
                }


                String article = document.getElementsByClass("styles__articleNumber--1UszN").text().substring(12);


                String json = document.html();
                json = json.split("window.__INITIAL_STATE__=")[1];
                json = json.substring(0, json.indexOf(";</script>"));

                JsonObject jsonObject = (JsonObject) jsonParser.parse(json);
                JsonObject offerJson = jsonObject.getAsJsonObject("entities").getAsJsonObject("products").getAsJsonObject(jId);

                String brand = offerJson.get("brandName").getAsString();


                String description = offerJson.getAsJsonObject("info").get("description").getAsString();


                JsonArray jArr = offerJson.getAsJsonArray("variants");

                for (JsonElement element : jArr) {

                    available = element.getAsJsonObject().get("quantity").getAsInt();
                    if (available == 0) {

                    } else {
                        Offer offer = new Offer();
                        offer.setName(name);
                        offer.setColor(clr);
                        offer.setArticle(article);
                        offer.setBrand(brand);
                        offer.setDescription(description);

                        BigDecimal price = element.getAsJsonObject().getAsJsonObject("price").get("current").getAsBigDecimal();
                        offer.setPrice(price);

                        BigDecimal initialPrice = element.getAsJsonObject().getAsJsonObject("price").get("old").getAsBigDecimal();
                        offer.setInitialPrice(initialPrice);

                        String size = element.getAsJsonObject().getAsJsonObject("sizes").get("shop").getAsString();
                        offer.setSize(size);

                        System.out.println("Sucsesfull");
                        offers.add(offer);
                    }


                }


            }
        }
        return offers;

    }*/

    @Override
    public void run() {
        synchronized (this) {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String[] jOfferId = offerPage.split("-");
            String jId = jOfferId[jOfferId.length - 1];
            Document document = null;


            int available = 0;

            try {
                document = Jsoup.connect(offerPage).timeout(0).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (document.html().contains("styles__title--3Jos_")) {
                String name = document.getElementsByClass("styles__title--3Jos_").first().text();


                Elements colors = document.getElementsByAttributeValueMatching("class", "styles__title--UFKYd");
                String clr = "";
                for (Element color : colors) {
                    clr += color.text() + ",";
                }


                String article = document.getElementsByClass("styles__articleNumber--1UszN").text().substring(12);


                String json = document.html();
                json = json.split("window.__INITIAL_STATE__=")[1];
                json = json.substring(0, json.indexOf(";</script>"));

                JsonObject jsonObject = (JsonObject) jsonParser.parse(json);
                JsonObject offerJson = jsonObject.getAsJsonObject("entities").getAsJsonObject("products").getAsJsonObject(jId);

                String brand = offerJson.get("brandName").getAsString();


                String description = offerJson.getAsJsonObject("info").get("description").getAsString();


                JsonArray jArr = offerJson.getAsJsonArray("variants");

                for (JsonElement element : jArr) {

                    available = element.getAsJsonObject().get("quantity").getAsInt();
                    if (available == 0) {

                    } else {
                        Offer offer = new Offer();
                        offer.setName(name);
                        offer.setColor(clr);
                        offer.setArticle(article);
                        offer.setBrand(brand);
                        offer.setDescription(description);

                        BigDecimal price = element.getAsJsonObject().getAsJsonObject("price").get("current").getAsBigDecimal();
                        offer.setPrice(price);

                        BigDecimal initialPrice = element.getAsJsonObject().getAsJsonObject("price").get("old").getAsBigDecimal();
                        offer.setInitialPrice(initialPrice);

                        String size = element.getAsJsonObject().getAsJsonObject("sizes").get("shop").getAsString();
                        offer.setSize(size);


                        offers.add(offer);

                    }


                }
            }
            semaphore.release();
        }
    }
}
