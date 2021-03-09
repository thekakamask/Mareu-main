package com.example.mareu.service;

import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

//import static com.example.mareu.service.MeetingGenerator.DUMMY_MEETINGS;
import static com.example.mareu.utils.MeetingUtil.getMeetingsMatchRoom;
import static com.example.mareu.utils.MeetingUtil.getMeetingsMatchDate;



public class FakeMeetingApiService implements MeetingApiService {
    private List<Meeting> mMeetings;
    private final List<String> mRooms;



    public FakeMeetingApiService() {
        mMeetings = new ArrayList<>();
        mRooms = new ArrayList<>(Arrays.asList(
                "Room 1", "Room 2", "Room 3", "Room 4", "Room 5",
                "Room 6", "Room 7", "Room 8", "Room 9", "Room 10"));
    }

    //RECUPERER LES SALLES
    @Override
    public List<String> getRooms() {
        return mRooms;
    }

    //AJOUTER UNE SALLE
    @Override
    public void addRoom(String room) {
        mRooms.add(room);
    }

    //SUPPRIMER UNE SALLE
    @Override
    public void delRoom(String room) {
        mRooms.remove(room);
    }

    //SUPPRIMER TTES LES SALLES POUR L'INITIALISATION
    @Override
    public void delAllRooms() {
        mRooms.clear();
    }

    // RECUPERE LES REUNIONS
    @Override
    public List<Meeting> getMeetings(Calendar date, String roomName) {
        if (date != null && roomName != null && ! roomName.isEmpty())
            return getMeetingsMatchDate(date, getMeetingsMatchRoom(roomName, mMeetings));
        else if (date != null)
            return getMeetingsMatchDate(date, mMeetings);
        else if (roomName != null && ! roomName.isEmpty())
            return getMeetingsMatchRoom(roomName, mMeetings);

        Collections.sort(mMeetings);
        return mMeetings;

    }

    // AJOUTER UNE REUNION
    @Override
    public void addMeeting(Meeting meeting) throws MeetingApiServiceException {
        for (Meeting m : mMeetings) {
            if (meeting.getRoomName().equals(m.getRoomName())) {
                if (meeting.getStartTime().equals(m.getStartTime()) || meeting.getEndTime().equals(m.getEndTime()))
                    throw new MeetingApiServiceException();
                if (meeting.getStartTime().after(m.getStartTime()) && meeting.getStartTime().before(m.getEndTime()))
                    if (meeting.getStartTime().after(m.getStartTime()) && meeting.getStartTime().before(m.getEndTime()))
                        throw new MeetingApiServiceException();

            }
        }
        mMeetings.add(meeting);
    }

    // SUPPRIMER UNE REUNION
    @Override
    public void delMeeting(Integer idMeeting) {
        for (Meeting m: mMeetings) {
            if (m.getId().equals(idMeeting)) {
                mMeetings.remove(m);
                break;
            }
        }

    }


}