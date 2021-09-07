package org.example.jvm.task4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Cat implements Animal {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void play() {
        logger.info("Cat play!");
    }

    @Override
    public void voice() {
        logger.info("Meow Meow");
    }
}
