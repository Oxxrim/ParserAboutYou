package com.scalors.dao;

import java.io.FileWriter;
import java.io.IOException;

public class WriterToXML {

    private static final String FILE_PATH = System.getProperty("user.dir") + System.getProperty("file.separator") + "ExtractedOffers.xml";
    private static final String firstRow = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<offers>";
    private static final String lastRow = "\n</offers>";

    public void writeOffersToXML() throws IOException {

        FileWriter writer = new FileWriter(FILE_PATH);

        writer.write(firstRow);
        writer.write(lastRow);
        writer.flush();
        writer.close();
    }
}
