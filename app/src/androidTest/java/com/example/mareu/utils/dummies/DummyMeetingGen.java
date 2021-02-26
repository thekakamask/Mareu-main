package com.example.mareu.utils.dummies;

import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.mareu.utils.dummies.DummyCalendarGen.generateTomorrowDateTime;
import static com.example.mareu.utils.dummies.DummyEmailGen.generateEmails;

public abstract class DummyMeetingGen {

    public static int ITEMS_COUNT= 6;
    public static int TOMO_MEETING_COUNT=6;
    public static final String ROOM_NAME_1 = "Room 1";
    public static int MEETINGS_ROOM_NAME_1_COUNT = 1;
    public static int TOM_MEETING_ROOM_NAME_1= 1;
    public static final String ROOM_NAME="Room 3";
    public static final String TOPIC="meeting topic";
    public static final String EXPECTED_DESCRIPTION_24h="Room 1 - 07:00 - Chimie organique";
    public static final String EXPECTED_DESCRIPTION_12H="Room 1 - 7:00 AM - Chimie organique";
    public static final int EXPECTED_ITEM_POSITION = 0;
    public static final int DELETE_ITEM_POSITION= 1;
    public static final String EXPECTED_PARTICIPANTS =  String.join(", ", generateEmails());

    private static final List<String> DUMMY_MEETING_ROOMS = Arrays.asList(
            "Room 1", "Room 2","Room 3","Room 4","Room 5","Room 6","Room 7",
            "Room 8","Room 9","Room 10");

    private static final List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting ("Room 1",
                    generateTomorrowDateTime(7,0),
                    generateTomorrowDateTime(7,45),
                    "Chimie organique",
                    generateEmails()),
            new Meeting ("Room 2",
                    generateTomorrowDateTime(7,0),
                    generateTomorrowDateTime(7,45),
                    "Physique quantique",
                    generateEmails(1)),
            new Meeting ("Room 3",
                    generateTomorrowDateTime(7,0),
                    generateTomorrowDateTime(7,45),
                    "Biologie organique",
                    generateEmails(3)),
            new Meeting ("Room 4",
                    generateTomorrowDateTime(7,0),
                    generateTomorrowDateTime(7,45),
                    "Physique des particules",
                    generateEmails(2)),
            new Meeting ("Room 5",
                    generateTomorrowDateTime(7,0),
                    generateTomorrowDateTime(7,45),
                    "Gestion des données",
                    generateEmails(1)),
            new Meeting ("Room 6",
                    generateTomorrowDateTime(7,0),
                    generateTomorrowDateTime(7,45),
                    "Mecanique des fluides",
                    generateEmails()),
            new Meeting ("Room 1",
                    generateTomorrowDateTime(7,0),
                    generateTomorrowDateTime(7,45),
                    "Budget",
                    generateEmails(2)),
            new Meeting ("Room 1",
                    generateTomorrowDateTime(7,0),
                    generateTomorrowDateTime(7,45),
                    "Organisation",
                    generateEmails(2)),
            new Meeting ("Room 1",
                    generateTomorrowDateTime(7,0),
                    generateTomorrowDateTime(7,45),
                    "Analyse quantitative",
                    generateEmails(3))

    );

    //GENERE UN LISTE DE SALLE

    public static List<String> generateRooms() {
        return new ArrayList<>(DUMMY_MEETING_ROOMS);
    }

    //GENERE UN LIST DE MEETING

    public static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}
