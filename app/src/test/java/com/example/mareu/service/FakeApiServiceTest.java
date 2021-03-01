package com.example.mareu.service;

import androidx.annotation.VisibleForTesting;

import com.example.mareu.model.Meeting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Calendar;

import static com.example.mareu.utils.UtilTest.fromTime;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class FakeApiServiceTest {

    private FakeMeetingApiService mApi;
    private Integer mInitialCount;
    private Meeting mMeeting;

    @BeforeEach
    private void setUp() throws MeetingApiServiceException {
        mApi = new FakeMeetingApiService();

        mMeeting = new Meeting(
                "Salle 1",
                fromTime("14:00"),
                fromTime("15:00"),
                "sujet",
                Arrays.asList("houssemtoumi@hotmail.fr", "adressecool@hotmail.fr"));

        mApi.addMeeting(mMeeting);

        mInitialCount = mApi.getMeetings(null,"").size();

    }


    @Test
    void addMeeting() {

        Meeting meeting = new Meeting ("salle 2", Calendar.getInstance(),Calendar.getInstance(),"sujet",Arrays.asList("hubertdel@hotmail.fr", "alisherkoa@hotmail.com"));

        try {
            mApi.addMeeting(meeting);
        } catch (MeetingApiServiceException e) {

        }

    }


    @ParameterizedTest
    @CsvSource({"14:00,15:00", "13:30,15:00", "14:00,15:30", "14:15,14:45", "14:30,15:30"})
    void addMeetingFail(String start, String end) {
        final Meeting meeting = new Meeting(
                "Salle 1",
                fromTime(start),
                fromTime(end),
                "sujet",
                Arrays.asList("houssemtoumi@hotmail.fr", "adressecool@hotmail.fr"));

        assertThrows(MeetingApiServiceException.class, () -> mApi.addMeeting(meeting));

    }


    @Test
    void getMeeting() {
        assertEquals((int) mInitialCount, mApi.getMeetings(null, "").size()); }


    @Test
    void delMeeting() {
        mApi.delMeeting(mMeeting.getId());

        assertEquals((int) --mInitialCount, mApi.getMeetings(null, "").size());

    }





}
