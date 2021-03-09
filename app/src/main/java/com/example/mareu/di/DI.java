package com.example.mareu.di;

import com.example.mareu.model.Meeting;
import com.example.mareu.service.FakeMeetingApiService;
import com.example.mareu.service.MeetingApiService;
import com.example.mareu.service.MeetingApiServiceException;

import java.util.List;

public class DI {

    // RECUPERER L'INSTANCE
    private static MeetingApiService sService = new FakeMeetingApiService();


    // RETOURNER L'INSTANCE
    public static MeetingApiService getMeetingApiService() {
        return sService;
    }

    //INITIALISATION DE L'API POUR LES TESTS
    public static void initMeetingApiService(List<String> rooms, List<Meeting> meetings) throws MeetingApiServiceException {

        sService = new FakeMeetingApiService();
        sService.delAllRooms();

        for (String room : rooms)
            sService.addRoom(room);

        for (Meeting meeting: meetings)
            sService.addMeeting(meeting);

    }
}
