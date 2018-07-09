package com.scalors;

import com.scalors.dao.WriterToXML;

import java.io.IOException;

public class ApplicationRunner {

    public static void main(String[] args) throws IOException {

        if(args.length == 0){
            System.out.print("By");
            System.exit(0);
        }

        WriterToXML writerToXML = new WriterToXML();
        writerToXML.writeOffersToXML();

    }
}
