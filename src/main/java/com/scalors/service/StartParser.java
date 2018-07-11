package com.scalors.service;

import com.scalors.dao.WriterToXML;
import com.scalors.model.Offer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StartParser {

    private List<String> pageUrls = new ArrayList<String>();
    private List<String> goodUrls = new ArrayList<String>();
    private List<Offer> offersList = new ArrayList<Offer>();

    public void paganation(String keyword) throws IOException {

        int amountOfPages = 0;
        CheckForRedirect redirect = new CheckForRedirect();
        String verifiedUrl = redirect.checker(keyword);

        pageUrls.add(verifiedUrl);

        Document document = null;

        try {
            document = Jsoup.connect(verifiedUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        amountOfPages = Integer.parseInt(document.getElementsByClass("styles__pageNumbers--1Lsj_").last().getElementsByTag("a").text());


        for (int i = 2; i <= amountOfPages; i++){

            pageUrls.add(verifiedUrl + "&page=" + i);
        }


        //System.out.print(amountOfPages);
        LinksOnGoodParser  links = new LinksOnGoodParser();


        goodUrls = links.parseLinks(pageUrls);

        for (String offerUrl : goodUrls) {
            GoodParser goodParser = new GoodParser(offersList,offerUrl);
            goodParser.start();
        }


        /*GoodParser goodParser = new GoodParser();
        offersList = goodParser.goodParsing(goodUrls);*/

        WriterToXML writerToXML = new WriterToXML();
        writerToXML.writeOffersToXML(offersList);

    }
}
