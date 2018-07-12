package com.scalors.service;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.scalors.model.Offer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.IOException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GoodParser extends Thread{

    private  static final Logger log = Logger.getLogger(GoodParser.class.getName());
    private final JsonParser jsonParser = new JsonParser();
    private List<Offer> offers = new ArrayList<Offer>();
    private String offerPage;
    private final Semaphore semaphore = new Semaphore(20,true);




    /*public GoodParser(List<Offer> offers, String offerPage) {

        this.offers = offers;
        this.offerPage = offerPage;
    }*/

    public List<Offer> goodParsing(List<String> links, int amountOfHttpRequest) {



        for (String offerPage : links) {

            String[] jOfferId = offerPage.split("-");
            String jId = jOfferId[jOfferId.length - 1];
            Document document = null;


            int available = 0;

            try {
                log.log(Level.INFO,"Start parsing : " + offerPage);
                document = Jsoup.connect(offerPage).get();
                amountOfHttpRequest++;
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



                String article = document.getElementsByClass("styles__articleNumber--1UszN").text().substring(12);


                String doc = document.html();
                String json = doc.split("window.__INITIAL_STATE__=")[1];
                json = json.substring(0, json.indexOf(";</script>"));

                JsonObject jsonObject = (JsonObject) jsonParser.parse(json);
                JsonObject offerJson = jsonObject.getAsJsonObject("entities").getAsJsonObject("products").getAsJsonObject(jId);

                String brand = offerJson.get("brandName").getAsString();


                String description = offerJson.getAsJsonObject("info").get("description").getAsString();

                String styleKey = offerJson.get("styleKey").getAsString();

                JsonArray array = jsonObject.getAsJsonObject("entities").getAsJsonObject("productStyleToIds").getAsJsonArray(styleKey);

                for (JsonElement element : array) {
                    offerJson = jsonObject.getAsJsonObject("entities").getAsJsonObject("products").getAsJsonObject(element.getAsString());

                    String clr = offerJson.getAsJsonObject("detailColors").toString();

                    clr = clr.replaceAll(":","").replaceAll(" ","").replaceAll("[0-9]","").replaceAll("\\{","").replaceAll("}","").replaceAll("\"","");

                    BigDecimal price = offerJson.getAsJsonObject("prices").get("beforeCampaignPrice").getAsBigDecimal();

                    BigDecimal initialPrice;
                    try {
                        initialPrice = offerJson.getAsJsonObject("prices").get("originalPrice").getAsBigDecimal();
                    } catch (Exception e){
                        initialPrice = BigDecimal.valueOf(0);
                    }

                    JsonArray jsonElements = offerJson.getAsJsonArray("streamSizes");

                    for (JsonElement jsonElement : jsonElements) {

                        available = jsonElement.getAsJsonObject().get("quantity").getAsInt();
                        if(available != 0){

                            Offer offer = new Offer();
                            offer.setName(name);
                            offer.setColor(clr);
                            offer.setArticle(article);
                            offer.setBrand(brand);
                            offer.setDescription(description);
                            offer.setPrice(price);
                            offer.setInitialPrice(initialPrice);

                            String size = jsonElement.getAsJsonObject().get("name").getAsString();
                            offer.setSize(size);

                            offers.add(offer);
                        }
                    }

                }



            }
        }
        return offers;

    }
    //Trying to do multi-threading but had a exception java.lang.OutOfMemoryError: Java heap space
    //I don`t know how to fix it
    /*@Override
    public void run() {

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



                String article = document.getElementsByClass("styles__articleNumber--1UszN").text().substring(12);


                String doc = document.html();
                String json = doc.split("window.__INITIAL_STATE__=")[1];
                json = json.substring(0, json.indexOf(";</script>"));

                JsonObject jsonObject = (JsonObject) jsonParser.parse(json);
                JsonObject offerJson = jsonObject.getAsJsonObject("entities").getAsJsonObject("products").getAsJsonObject(jId);

                String brand = offerJson.get("brandName").getAsString();


                String description = offerJson.getAsJsonObject("info").get("description").getAsString();

                String styleKey = offerJson.get("styleKey").getAsString();

                JsonArray array = jsonObject.getAsJsonObject("entities").getAsJsonObject("productStyleToIds").getAsJsonArray(styleKey);

                for (JsonElement element : array) {
                    offerJson = jsonObject.getAsJsonObject("entities").getAsJsonObject("products").getAsJsonObject(element.getAsString());

                    String clr = offerJson.getAsJsonObject("detailColors").toString();

                    clr = clr.replaceAll(":","").replaceAll(" ","").replaceAll("[0-9]","").replaceAll("\\{","").replaceAll("}","").replaceAll("\"","");

                    BigDecimal price = offerJson.getAsJsonObject("prices").get("beforeCampaignPrice").getAsBigDecimal();

                    BigDecimal initialPrice;
                    try {
                        initialPrice = offerJson.getAsJsonObject("prices").get("originalPrice").getAsBigDecimal();
                    } catch (Exception e){
                        initialPrice = BigDecimal.valueOf(0);
                    }

                    JsonArray jsonElements = offerJson.getAsJsonArray("streamSizes");

                    for (JsonElement jsonElement : jsonElements) {

                        available = jsonElement.getAsJsonObject().get("quantity").getAsInt();
                        if(available != 0){

                            Offer offer = new Offer();
                            offer.setName(name);
                            offer.setColor(clr);
                            offer.setArticle(article);
                            offer.setBrand(brand);
                            offer.setDescription(description);
                            offer.setPrice(price);
                            offer.setInitialPrice(initialPrice);

                            String size = jsonElement.getAsJsonObject().get("name").getAsString();
                            offer.setSize(size);

                            offers.add(offer);
                        }
                    }

                }



            }
        semaphore.release();

    }*/
}
