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


    // GENERER UN CALENDRIER POUR UNE DATE D'AUJOURDHUI AVEC UN TEMPS DECOUPE EN DEMI HEURE
    //RETOURNER CETTE DATE
    private static Calendar initNow() {
        Calendar tmp = Calendar.getInstance();

        int roundedMinutes = tmp.get(Calendar.MINUTE) % 15;
        roundedMinutes = roundedMinutes > 0 ? (15 -roundedMinutes) : 0;
        roundedMinutes+= 15;

        tmp.add(Calendar.MINUTE, roundedMinutes);

        return tmp;

    }

    //GENERER UN CALENDRIER POUR UN DATE DU PASSE OU FUTUR
    // DIFFDAYS ETANT LA DIFFERENCE DE JOUR ENTRE LA DATE ET AUJOURDHUI
    // RETOURNER CETTE DATE
    private static Calendar generateDateFromNow(int diffDays) {
        Calendar tmp = (Calendar) NOW.clone();
        tmp.add(Calendar.DATE, diffDays);

        return tmp;

    }

    //GENERATE UN CALENDRIER POUR UN HEURE DU PASSE OU FUTUR
    //DIFFHOURS ETANT LA DIFFENRECE DHEURE ENTRE LHEURE CIBLE ET LHEURE DE MAINTENANT
    //RETOURNER CETTE HEURE
    private static Calendar generateTimeFromNow(int diffHours) {
        Calendar tmp = (Calendar) NOW.clone();
        tmp.add(Calendar.HOUR_OF_DAY, diffHours);

        return tmp;

    }

    //GENERER UN CALENDRIER POUR UNE DATE ET UNE HEURE DEPUIS LE PASSE OU FUTUR
    //DIFFHOUYRS ETANT LE NOMBRE D'HEURE A ENLEVER OU AJOUTER
    // RETOURNER CETTE DATE ET CETTE HEURE
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
