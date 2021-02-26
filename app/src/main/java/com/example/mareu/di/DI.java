package com.example.mareu.di;

import com.example.mareu.model.Meeting;
import com.example.mareu.service.FakeMeetingApiService;
import com.example.mareu.service.MeetingApiService;
import com.example.mareu.service.MeetingApiServiceException;

import java.util.List;

public class DI {
    private static MeetingApiService sService = new FakeMeetingApiService();


    public static MeetingApiService getMeetingApiService() {
        return sService;
    }

    //EXPLIQUEZ CELA
    public static void initMeetingApiService(List<String> rooms, List<Meeting> meetings) throws MeetingApiServiceException {

        sService = new FakeMeetingApiService();
        sService.delAllRooms();

        for (String room : rooms)
            sService.addRoom(room);

        for (Meeting meeting: meetings)
            sService.addMeeting(meeting);

    }
}
