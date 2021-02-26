package com.example.mareu.utils.dummies;

import java.util.Calendar;

import static android.icu.text.RelativeDateTimeFormatter.AbsoluteUnit.NOW;

public abstract class DummyCalendarGen {

    private static final Calendar NOW = initNow();
    public static final Calendar TOMORROW = generateDateFromNow(1);
    public static final Calendar YESTERDAY = generateDateFromNow(-1);

    public static final Calendar TOMORROW_VALID_START_TIME = (Calendar) TOMORROW.clone();
    public static final Calendar TOMORROW_VALID_END_TIME = generateTimeFromTomo(1);

    public static final Calendar TOMOROW_INVALID_START_TIME = generateTimeFromNow(-1);
    public static final Calendar TOMORROW_INVALID_END_TIME = generateTimeFromTomo(-1);

    public static final Calendar TODAY_INVALID_START_TIME = generateTimeFromNow(-1);


    private static Calendar initNow() {
        Calendar tmp = Calendar.getInstance();

        int roundedMinutes = tmp.get(Calendar.MINUTE) % 15;
        roundedMinutes = roundedMinutes > 0 ? (15 -roundedMinutes) : 0;
        roundedMinutes+= 15;

        tmp.add(Calendar.MINUTE, roundedMinutes);

        return tmp;

    }

    private static Calendar generateDateFromNow(int diffDays) {
        Calendar tmp = (Calendar) NOW.clone();
        tmp.add(Calendar.DATE, diffDays);

        return tmp;

    }

    private static Calendar generateTimeFromNow(int diffHours) {
        Calendar tmp = (Calendar) NOW.clone();
        tmp.add(Calendar.HOUR_OF_DAY, diffHours);

        return tmp;

    }

    private static Calendar generateTimeFromTomo(int diffHours)  {
        Calendar tmp = (Calendar) TOMORROW.clone();
        tmp.add(Calendar.HOUR_OF_DAY, diffHours);

        return tmp;

    }

    public static Calendar generateDateTimeFromTomo(int diffDays, int hours, int minutes) {
        Calendar tmp=(Calendar) TOMORROW.clone();

        if (diffDays>1) {
            tmp.add(Calendar.DATE, diffDays);
        }

        tmp.set(Calendar.HOUR_OF_DAY, hours);
        tmp.set(Calendar.MINUTE, minutes);

        return tmp;

    }

    public static Calendar generateTomorrowDateTime(int hours, int minutes) {
        return generateDateTimeFromTomo(0, hours, minutes);

    }


}
