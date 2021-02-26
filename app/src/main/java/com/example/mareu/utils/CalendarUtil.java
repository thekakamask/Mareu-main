package com.example.mareu.utils;

import java.util.Calendar;

public class CalendarUtil {

    public static boolean sameDate(Calendar firstDate, Calendar secondDate) {
        if (firstDate.get(Calendar.YEAR) == secondDate.get(Calendar.YEAR))
            if (firstDate.get(Calendar.MONTH) == secondDate.get(Calendar.MONTH))
                return firstDate.get(Calendar.DAY_OF_MONTH) == secondDate.get(Calendar.DAY_OF_MONTH);

        return false;
    }
}
