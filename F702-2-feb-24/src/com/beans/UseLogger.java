package com.beans;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UseLogger {
	// use the classname for the logger, this way you can refactor
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    public void doSomeThingAndLog(String msg) {
        // ... more code

        // now we demo the logging

        // set the LogLevel to Severe, only severe Messages will be written
        /*LOGGER.setLevel(Level.SEVERE);
        LOGGER.severe("Info Log");
        LOGGER.warning("Info Log");
        LOGGER.info("Info Log");
        LOGGER.finest("Really not important");*/

        // set the LogLevel to Info, severe, warning and info will be written
        // finest is still not written
        LOGGER.setLevel(Level.INFO);
        //LOGGER.severe("");
       // LOGGER.warning("");
        LOGGER.info(msg);
        //LOGGER.finest("Really not important");
    }
}
