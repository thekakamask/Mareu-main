package com.example.mareu.service;

import com.example.mareu.model.Meeting;

import java.util.Calendar;
import java.util.List;

public interface MeetingApiService {


    public List<String> getRooms();

    public void addRoom(String room);

    public void delRoom(String room);

    public void delAllRooms();

    public List<Meeting> getMeetings(Calendar date, String roomName);

    public void addMeeting(Meeting meeting) throws MeetingApiServiceException;
    public void delMeeting(Integer idMeeting);

}
