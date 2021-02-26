package com.example.mareu.utils;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class UtilTest {

    @Test
    public static Calendar fromTime(String time) {


        //CONVERTIR UN STRING EN HEURE
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        //RECUPERER L'INSTANCE DU CALENDRIER
        Calendar datetime = Calendar.getInstance();

        //CONFIGURER L'HEURE
        try {
            datetime.setTime(Objects.requireNonNull(sdf.parse(time)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return datetime;
    }
}
