package com.example.mareu.model;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.example.mareu.utils.UtilTest.fromTime;
import static org.junit.Assert.assertThat;

class MeetingTest {
    private final String invalid_email = "test" ;

    @Test
    void initiateMeetingWithValidEmail() {
        List<String> participants = Arrays.asList(
                "houssem.toumi@hotmail.fr", "adressecool@hotmail.fr");

        Meeting meeting = new Meeting("salle 1", fromTime("11:00"),
                fromTime("12:00"), "chimie orga", participants );

        assertThat(participants, CoreMatchers.is(meeting.getParticipants()));
    }

    @Test
    void initiateMeetingWithInvalidEmail() {
        List<String> participants = new LinkedList<>(Arrays.asList(
                "houssem.toumi@hotmail.fr", "adressecool@hotmail.fr", invalid_email));

        Meeting meeting = new Meeting("salle 1", fromTime("13:00"),
                fromTime("14:00"), "physique quantique", participants );

        participants.remove(invalid_email);

        assertThat(participants, CoreMatchers.is(meeting.getParticipants()));
    }


}
