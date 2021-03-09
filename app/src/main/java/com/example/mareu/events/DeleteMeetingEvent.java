package com.example.mareu.events;

public class DeleteMeetingEvent {

    private int meetingId;


    // CONSTRUCTEUR

    public DeleteMeetingEvent(int meetingId) {
        this.meetingId = meetingId;
    }

    // RETOURNER L'ID DU MEETING A SUPPRIMER

    public int getMeetingId() {
        return meetingId;
    }

}
