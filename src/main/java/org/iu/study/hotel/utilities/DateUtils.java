package org.iu.study.hotel.utilities;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static final String HTML_DATE_PATTER = "yyyy-MM-dd";
    private static final DateTimeFormatter GERMAN_DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static String formatDateGerman(LocalDate date){
        if(date == null)
            return null;

        return GERMAN_DATE_FORMAT.format(date);
    }
}
