package com.scalors.service;


import com.scalors.dao.WriterToXML;
import com.scalors.model.Offer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartParser {

    private  static final Logger log = Logger.getLogger(StartParser.class.getName());
    private List<String> pageUrls = new ArrayList<String>();
    private List<String> goodUrls = new ArrayList<String>();
    private List<Offer> offersList = new ArrayList<Offer>();

    public void pagination(String keyword, int amountOfHttpRequest) throws IOException {

        int amountOfPages = 0;
        CheckForRedirect redirect = new CheckForRedirect();
        String verifiedUrl = redirect.checker(keyword);

        pageUrls.add(verifiedUrl);

        Document document = null;

        try {
            log.log(Level.INFO,"Connect to : " + verifiedUrl);
            document = Jsoup.connect(verifiedUrl).get();
            amountOfHttpRequest++;
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            amountOfPages = Integer.parseInt(document.getElementsByClass("styles__pageNumbers--1Lsj_").last().getElementsByTag("a").text());
        } catch (Exception e){
            e.printStackTrace();
        }


        for (int i = 2; i <= amountOfPages; i++){

            pageUrls.add(verifiedUrl + "&page=" + i);
        }



        LinksOnGoodParser  links = new LinksOnGoodParser();


        goodUrls = links.parseLinks(pageUrls, amountOfHttpRequest);

        //Trying to do multi-threading but had a exception java.lang.OutOfMemoryError: Java heap space
        /*for (String offerUrl : goodUrls) {
            GoodParser goodParser = new GoodParser(offersList,offerUrl);
            goodParser.start();
        }*/


        GoodParser goodParser = new GoodParser();
        offersList = goodParser.goodParsing(goodUrls, amountOfHttpRequest);

        WriterToXML writerToXML = new WriterToXML();
        writerToXML.writeOffersToXML(offersList);

        log.log(Level.INFO,"Amount of triggered HTTP requests : " + amountOfHttpRequest);
        log.log(Level.INFO,"Amount of extracted products : " + offersList.size());
    }
}
