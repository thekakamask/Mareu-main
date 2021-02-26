package com.example.mareu.utils.dummies;

import java.util.Arrays;
import java.util.List;

public abstract class DummyEmailGen {

    public static final String INVALID_EMAIL = "erreurMail";
    public static final String VALID_EMAIL_1 = "adressecool@hotmail.fr";
    public static final String VALID_EMAIL_2 = "houssemtoum@gmail.com";
    public static final String VALID_EMAIL_3 = "mognettout@gmail.com";


    private static final List<String> DUMMY_EMAILS = Arrays.asList(
            "adressecool@hotmail.fr",
            "houssemtoum@gmail.com",
            "leomarti@hotmail.com",
            "angelmognet@outlook.com");

    static List<String> generateEmails() {return DUMMY_EMAILS;}

    static List<String> generateEmails(int nbEmail) {
        nbEmail--;

        if (nbEmail < 0 || nbEmail >= DUMMY_EMAILS.size())
            return DUMMY_EMAILS;
        else
            return DUMMY_EMAILS.subList(0,nbEmail);


    }


}
