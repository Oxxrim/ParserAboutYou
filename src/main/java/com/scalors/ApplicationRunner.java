package com.scalors;


import com.scalors.service.StartParser;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Okhrymenko Dmytro
 *
 */

public class ApplicationRunner {
    private  static final Logger log = Logger.getLogger(ApplicationRunner.class.getName());

    public static void main(String[] args) throws IOException {

        long start = System.currentTimeMillis();

        if(args.length == 0){
            log.log(Level.INFO,"You don`t write arguments, the program is shutting down!");
            System.exit(0);
        }
        int amountOfHttpRequest = 0;

        StartParser parser = new StartParser();
        parser.pagination(args[0],amountOfHttpRequest);

        log.log(Level.INFO,"Run-time : " + (System.currentTimeMillis()- start)/1000 + " sec");
        log.log(Level.INFO,"Memory Footprint : " + ((double)Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024*1024) + " mb");

    }
}
