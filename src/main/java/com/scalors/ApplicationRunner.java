package com.scalors;

import com.scalors.dao.WriterToXML;
import com.scalors.service.CheckForRedirect;
import com.scalors.service.StartParser;

import java.io.IOException;

/**
 *
 * @author Okhrymenko Dmytro
 *
 */

public class ApplicationRunner {

    public static void main(String[] args) throws IOException {

        /*if(args.length == 0){
            System.out.print("By");
            System.exit(0);
        }*/
        //CheckForRedirect redirect = new CheckForRedirect();
        //System.out.print(redirect.checker(args[0]));
        StartParser parser = new StartParser();
        parser.paganation(/*args[0]*/"ab");
        //WriterToXML writerToXML = new WriterToXML();
        //writerToXML.writeOffersToXML();

    }
}
