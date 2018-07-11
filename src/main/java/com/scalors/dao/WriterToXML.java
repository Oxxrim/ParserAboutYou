package com.scalors.dao;

import com.scalors.model.Offer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriterToXML {

    private static final String FILE_PATH = System.getProperty("user.dir") + System.getProperty("file.separator") + "ExtractedOffers.xml";
    private static final String firstRow = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<offers>";
    private static final String lastRow = "\n</offers>";

    public void writeOffersToXML(List<Offer> offers) throws IOException {

        FileWriter writer = new FileWriter(FILE_PATH);

        writer.write(firstRow);
        for (Offer offer: offers) {

            writer.write("\n\t<offer>\n\t\t<name>" + offer.getName() + "</name>" +
            "\n\t\t<brand>" + offer.getBrand() + "</brand>" +
            "\n\t\t<color>" + offer.getColor() + "</color>" +
            "\n\t\t<price>" + offer.getPrice() + "</price>" +
            "\n\t\t<initial price>" + offer.getInitialPrice() + "</initial price>" +
            "\n\t\t<description>" + offer.getDescription() + "</description>" +
            "\n\t\t<article id>" + offer.getArticle() + "</article id>" +
            "\n\t\t<size>" + offer.getSize() + "</size>" + "\n\t</offer>");
        }
        writer.write(lastRow);
        writer.flush();
        writer.close();
    }
}
