package org.example.jdbc.task3.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateParser {

    private static final Logger logger = LogManager.getLogger();

    public static Date parseDateFromString(String stringDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = dateFormat.parse(stringDate);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        return date;
    }
}
